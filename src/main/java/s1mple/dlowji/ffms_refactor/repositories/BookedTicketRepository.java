package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import s1mple.dlowji.ffms_refactor.entities.BookedTicket;

import java.util.List;

@Repository
public interface BookedTicketRepository extends CrudRepository<BookedTicket, Long> {
    List<BookedTicket> findAll();
}
