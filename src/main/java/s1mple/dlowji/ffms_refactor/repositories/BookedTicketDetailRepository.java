package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import s1mple.dlowji.ffms_refactor.entities.BookedTicketDetail;

import java.time.LocalDateTime;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface BookedTicketDetailRepository extends JpaRepository<BookedTicketDetail, Long> {
	Optional<BookedTicketDetail> findByStartTimeGreaterThanEqualAndEndTimeLessThanEqual(LocalDateTime startTime, LocalDateTime endTime);
}
