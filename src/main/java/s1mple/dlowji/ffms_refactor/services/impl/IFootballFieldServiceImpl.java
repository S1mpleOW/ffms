package s1mple.dlowji.ffms_refactor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s1mple.dlowji.ffms_refactor.entities.BookedTicket;
import s1mple.dlowji.ffms_refactor.entities.BookedTicketDetail;
import s1mple.dlowji.ffms_refactor.entities.FootballField;
import s1mple.dlowji.ffms_refactor.repositories.BookedTicketDetailRepository;
import s1mple.dlowji.ffms_refactor.repositories.FootballFieldRepository;
import s1mple.dlowji.ffms_refactor.services.IBookedTicketService;
import s1mple.dlowji.ffms_refactor.services.IFootballFieldService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IFootballFieldServiceImpl implements IFootballFieldService {
	@Autowired
	private FootballFieldRepository footballFieldRepository;

	@Autowired
	private BookedTicketDetailRepository bookedTicketDetailRepository;

	@Autowired
	private IBookedTicketService bookedTicketService;

	@Override
	public boolean existsById(Long id) {
		return footballFieldRepository.existsById(id);
	}

	@Override
	public boolean check_available_space(LocalDateTime startTime, LocalDateTime endTime) {
		Optional<BookedTicketDetail> isAvailable =
		bookedTicketDetailRepository.findByStartTimeGreaterThanEqualAndEndTimeLessThanEqual(startTime, endTime);
		System.out.println("is available " + isAvailable);
		if (isAvailable.isPresent()) {
			return true;
		}
		return false;
	}

	@Override
	public Optional<FootballField> findById(Long id) {
		return footballFieldRepository.findById(id);
	}

	@Override
	public List<FootballField> getFieldByBookedTicketDetails(List<BookedTicketDetail> bookedTicketDetailList) {
		List<FootballField> footballFieldList = new ArrayList<>();

		for (BookedTicketDetail btd:bookedTicketDetailList) {
			footballFieldList.add(btd.getFootballField());
		}

		return footballFieldList;
	}

	@Override
	public List<FootballField> getBookedFieldByWeek(LocalDateTime firstDay, LocalDateTime lastDay) {
		List<FootballField> footballFieldList = new ArrayList<>();

		return footballFieldList;
	}
}
