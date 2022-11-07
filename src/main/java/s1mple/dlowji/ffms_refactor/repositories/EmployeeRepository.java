package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import s1mple.dlowji.ffms_refactor.entities.Customer;
import s1mple.dlowji.ffms_refactor.entities.Employee;

import java.util.Optional;

@PreAuthorize("hasAuthority('ADMIN')")
@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
	boolean existsEmployeeByIdentityCard(String identityCard);

	Optional<Employee> findEmployeeByAccount_Id(Long aLong);

	@Override
	<S extends Employee> S save(S entity);

	@RestResource(path="groups", rel = "employees")
	Page<Employee> findEmployeesByFieldGroup_Name (String fieldGroup_name, Pageable pageable);
}
