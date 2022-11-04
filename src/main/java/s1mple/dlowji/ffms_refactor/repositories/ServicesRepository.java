package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import s1mple.dlowji.ffms_refactor.entities.Item;
import s1mple.dlowji.ffms_refactor.entities.Service;

@RepositoryRestResource
@CrossOrigin("*")
public interface ServicesRepository extends JpaRepository<Service, Long> {

}
