package s1mple.dlowji.ffms_refactor.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s1mple.dlowji.ffms_refactor.dto.request.FieldOrderForm;
import s1mple.dlowji.ffms_refactor.dto.response.ResponseMessage;
import s1mple.dlowji.ffms_refactor.entities.*;
import s1mple.dlowji.ffms_refactor.entities.enums.PaymentStatus;
import s1mple.dlowji.ffms_refactor.services.IBookedTicketService;
import s1mple.dlowji.ffms_refactor.services.impl.ICustomerServiceImpl;
import s1mple.dlowji.ffms_refactor.services.impl.IFootballFieldServiceImpl;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

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

			if (!customerService.existsByPhoneNumber(fieldOrderForm.getPhone())) {
				return new ResponseEntity<>(new ResponseMessage("The customer is not " +
				"existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
			}

		if (footballFieldService.check_available_space(fieldOrderForm.getStart_time(), fieldOrderForm.getEnd_time())) {
			return new ResponseEntity<>(new ResponseMessage("The field is ordered",
			HttpStatus.OK.value()), HttpStatus.OK);
		}
		int hours =
		fieldOrderForm.getEnd_time().getHour() - fieldOrderForm.getStart_time().getHour();
		int minutes =
		fieldOrderForm.getEnd_time().getMinute() - fieldOrderForm.getStart_time().getMinute();
		if(hours * 60 + minutes < 60) {
			return new ResponseEntity<>(new ResponseMessage("The field must be " +
			"ordered at least 60 minutes",
			HttpStatus.OK.value()), HttpStatus.OK);
		}
		LocalDateTime now = LocalDateTime.now();
		boolean isAfterStartTime = now.isAfter(fieldOrderForm.getStart_time());
		boolean isAfterEndTime = now.isAfter(fieldOrderForm.getEnd_time());
		if(isAfterStartTime || isAfterEndTime) {
			return new ResponseEntity<>(new ResponseMessage("Please choose the next" +
			" time from now",
			HttpStatus.OK.value()), HttpStatus.OK);
		}

		Long footballFieldId = fieldOrderForm.getField_id();

		FootballField footballField =
		footballFieldService.findById(footballFieldId).get();


		int totalPrice = (int) Math.floor(footballField.getPrice() * (hours + minutes / 60));
		Customer customer =
		customerService.findCustomerByPhone(fieldOrderForm.getPhone()).get();

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
}
