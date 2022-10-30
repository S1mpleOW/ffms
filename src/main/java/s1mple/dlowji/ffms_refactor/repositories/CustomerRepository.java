package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import s1mple.dlowji.ffms_refactor.entities.Customer;
import s1mple.dlowji.ffms_refactor.entities.projections.PartialCustomerProjection;

import java.util.List;

@RepositoryRestResource(excerptProjection = PartialCustomerProjection.class)
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
