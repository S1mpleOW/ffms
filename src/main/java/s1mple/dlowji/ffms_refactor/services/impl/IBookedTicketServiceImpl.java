package s1mple.dlowji.ffms_refactor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s1mple.dlowji.ffms_refactor.entities.BookedTicket;
import s1mple.dlowji.ffms_refactor.entities.BookedTicketDetail;
import s1mple.dlowji.ffms_refactor.repositories.BookedTicketDetailRepository;
import s1mple.dlowji.ffms_refactor.repositories.BookedTicketRepository;
import s1mple.dlowji.ffms_refactor.services.IBookedTicketService;

@Service
public class IBookedTicketServiceImpl implements IBookedTicketService {
	@Autowired
	private BookedTicketRepository bookedTicketRepository;

	@Autowired
	private BookedTicketDetailRepository bookedTicketDetailRepository;

	@Override
	public BookedTicket save(BookedTicket bookedTicket) {
		return bookedTicketRepository.save(bookedTicket);
	}

	@Override
	public BookedTicketDetail save(BookedTicketDetail bookedTicketDetail) {
		return bookedTicketDetailRepository.save(bookedTicketDetail);
	}
}
