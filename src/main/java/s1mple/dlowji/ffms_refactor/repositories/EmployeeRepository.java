package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import s1mple.dlowji.ffms_refactor.entities.Customer;
import s1mple.dlowji.ffms_refactor.entities.Employee;

import java.util.Optional;

@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin("*")
@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
	boolean existsEmployeeByIdentityCard(String identityCard);

	Optional<Employee> findEmployeeByAccount_Id(Long aLong);
}
