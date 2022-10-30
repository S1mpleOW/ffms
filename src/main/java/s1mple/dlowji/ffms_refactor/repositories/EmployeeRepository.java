package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import s1mple.dlowji.ffms_refactor.entities.Employee;

@PreAuthorize("hasAuthority('ADMIN')")
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
}
