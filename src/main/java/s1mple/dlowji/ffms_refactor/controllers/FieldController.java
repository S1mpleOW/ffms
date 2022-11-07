package s1mple.dlowji.ffms_refactor.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s1mple.dlowji.ffms_refactor.dto.request.BookedTicketForm;
import s1mple.dlowji.ffms_refactor.dto.request.FieldOrderForm;
import s1mple.dlowji.ffms_refactor.dto.response.ResponseMessage;
import s1mple.dlowji.ffms_refactor.entities.*;
import s1mple.dlowji.ffms_refactor.entities.enums.PaymentStatus;
import s1mple.dlowji.ffms_refactor.services.IBookedTicketService;
import s1mple.dlowji.ffms_refactor.services.impl.ICustomerServiceImpl;
import s1mple.dlowji.ffms_refactor.services.impl.IFootballFieldServiceImpl;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.time.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class FieldController {
	@Autowired
	private IFootballFieldServiceImpl footballFieldService;

	@Autowired
	private ICustomerServiceImpl customerService;

	@Autowired
	private IBookedTicketService bookedTicketService;

	/*
		Input {
			id football field
			id customer
			start time
			end time
		}
	 */
	@PostMapping("/fields/order")
	public ResponseEntity<?> orderField(@Valid @RequestBody FieldOrderForm fieldOrderForm) {
		System.out.println(fieldOrderForm);
		if (!footballFieldService.existsById(fieldOrderForm.getField_id())) {
			return new ResponseEntity<>(new ResponseMessage("The field is not " +
			"existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
		}

		if (!customerService.existsById(fieldOrderForm.getCustomer_id())) {
			return new ResponseEntity<>(new ResponseMessage("The customer is not " +
			"existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
		}

		if (footballFieldService.check_available_space(fieldOrderForm.getStart_time(), fieldOrderForm.getEnd_time())) {
			return new ResponseEntity<>(new ResponseMessage("The field is ordered",
			HttpStatus.OK.value()), HttpStatus.OK);
		}

		Long footballFieldId = fieldOrderForm.getField_id();

		FootballField footballField =
		footballFieldService.findById(footballFieldId).get();
		int hours =
		fieldOrderForm.getEnd_time().getHour() - fieldOrderForm.getStart_time().getHour();
		int minutes =
		fieldOrderForm.getEnd_time().getMinute() - fieldOrderForm.getStart_time().getMinute();
		int totalPrice = (int) Math.floor(footballField.getPrice() * (hours + minutes / 60));

		Customer customer =
		customerService.findCustomerById(fieldOrderForm.getCustomer_id()).get();


		BookedTicket receipt =
		BookedTicket.builder().customer(customer).paymentStatus(PaymentStatus.PROCESSING).totalPrice(totalPrice).build();

		BookedTicketDetail receiptDetail =
		BookedTicketDetail.builder()
		.id(BookedTicketDetailKey.builder().bookedTicketId(receipt.getId()).footballFieldId(footballFieldId).build())
		.bookedTicket(receipt)
		.footballField(footballField)
		.deposit(0)
		.startTime(fieldOrderForm.getStart_time())
		.endTime(fieldOrderForm.getEnd_time())
		.build();

		BookedTicket receiptBody = bookedTicketService.save(receipt);
		BookedTicketDetail receiptDetailBody = bookedTicketService.save(receiptDetail);
		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("receipt_id", receiptBody.getId());
		response.put("total_price", totalPrice);
		response.put("payment_status", PaymentStatus.PROCESSING.toString());
		response.put("created_at", receiptBody.getCreatedAt());
		response.put("deposit", receiptDetailBody.getDeposit());
		return ResponseEntity.ok(response);
	}

	@PostMapping("/booked/played")
	public ResponseEntity<?> getPlayedBookedFields(@Valid @RequestBody BookedTicketForm bookedTicketForm) {
//		LocalDateTime bookedDate = bookedTicketForm.getBooked_date();

		LocalDateTime bookedDate = LocalDateTime.now();
		List<BookedTicketDetail> bookedTicketDetailList =
		bookedTicketService.getPlayedBookedTicketDetailByDate(bookedDate);

		List<FootballField> footballFieldList =
		footballFieldService.getFieldByBookedTicketDetails(bookedTicketDetailList);

		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("fieldList", footballFieldList);
		response.put("football_status", "played");
		return ResponseEntity.ok(response);
	}

	@PostMapping("/booked/playing")
	public ResponseEntity<?> getPlayingBookedFields(@Valid @RequestBody BookedTicketForm bookedTicketForm) {
//		LocalDateTime bookedDate = bookedTicketForm.getBooked_date();
		LocalDateTime bookedDate = LocalDateTime.now();
		List<BookedTicketDetail> bookedTicketDetailList =
		bookedTicketService.getPlayingBookedTicketDetailByDate(bookedDate);

		List<FootballField> footballFieldList =
		footballFieldService.getFieldByBookedTicketDetails(bookedTicketDetailList);

		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("fieldList", footballFieldList);
		response.put("football_status", "playing");
		return ResponseEntity.ok(response);
	}

	@PostMapping("/booked/will-play")
	public ResponseEntity<?> getWillPlayBookedFields(@Valid @RequestBody BookedTicketForm bookedTicketForm) {
//		LocalDateTime bookedDate = bookedTicketForm.getBooked_date();
		LocalDateTime bookedDate = LocalDateTime.now();
		List<BookedTicketDetail> bookedTicketDetailList =
		bookedTicketService.getWillPlayBookedTicketDetailByDate(bookedDate);

		List<FootballField> footballFieldList =
		footballFieldService.getFieldByBookedTicketDetails(bookedTicketDetailList);

		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("fieldList", footballFieldList);
		response.put("football_status", "will-play");
		return ResponseEntity.ok(response);
	}

	@PostMapping("/booked/week")
	public ResponseEntity<?> getBookedFieldByWeek(@Valid @RequestBody BookedTicketForm bookedTicketForm) {
		Long field_id = bookedTicketForm.getField_id();

		LocalDate currentDay = LocalDate.now();

		int dayOfWeek = currentDay.getDayOfWeek().getValue();

		Map<Integer, Integer> days = getDaysOfWeek();

		int neededDay = days.get(DayOfWeek.SUNDAY.getValue()) - days.get(dayOfWeek);

		LocalDateTime firstDayOfWeek = currentDay.minusDays(days.get(dayOfWeek)).atStartOfDay();
		LocalDateTime lastDayOfWeek = currentDay.plusDays(neededDay).atStartOfDay();

		System.out.println(firstDayOfWeek);
		System.out.println(lastDayOfWeek);

		List<BookedTicketDetail> bookedTicketDetailList = bookedTicketService.getBookedTicketDetailByWeek(firstDayOfWeek, lastDayOfWeek, field_id);
		List<Map<String, Object>> datas = new ArrayList<>();

		for (BookedTicketDetail btd:bookedTicketDetailList) {
			Map<String, Object> data = new HashMap<>();
			data.put("id", btd.getFootballField().getId());
			data.put("start", btd.getStartTime());
			data.put("end", btd.getEndTime());

			datas.add(data);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.OK.value());
		response.put("data", datas);
		return ResponseEntity.ok(response);
	}

	public Map<Integer, Integer> getDaysOfWeek() {

		Map<Integer, Integer> days = new HashMap<>();
		days.put(DayOfWeek.MONDAY.getValue(), 0);
		days.put(DayOfWeek.TUESDAY.getValue(), 1);
		days.put(DayOfWeek.WEDNESDAY.getValue(), 2);
		days.put(DayOfWeek.THURSDAY.getValue(), 3);
		days.put(DayOfWeek.FRIDAY.getValue(), 4);
		days.put(DayOfWeek.SATURDAY.getValue(), 5);
		days.put(DayOfWeek.SUNDAY.getValue(), 6);

		return days;
	}
}
