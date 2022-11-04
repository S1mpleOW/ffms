package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import s1mple.dlowji.ffms_refactor.entities.Customer;
import s1mple.dlowji.ffms_refactor.entities.projections.PartialCustomerProjection;

import java.util.Optional;

@RepositoryRestResource(excerptProjection = PartialCustomerProjection.class, exported = false)
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findCustomerByAccount_Id(Long aLong);

	Page<Customer> findAll(Pageable pageable);
}
