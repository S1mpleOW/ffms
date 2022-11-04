package s1mple.dlowji.ffms_refactor.services;

import s1mple.dlowji.ffms_refactor.entities.BookedTicket;
import s1mple.dlowji.ffms_refactor.entities.BookedTicketDetail;

public interface IBookedTicketService {
	BookedTicket save(BookedTicket bookedTicket);

	BookedTicketDetail save(BookedTicketDetail bookedTicketDetail);
}
