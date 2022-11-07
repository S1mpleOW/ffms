package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import s1mple.dlowji.ffms_refactor.entities.FootballField;

@RepositoryRestResource(path = "football_fields")
@CrossOrigin("*")
public interface FootballFieldRepository extends PagingAndSortingRepository<FootballField, Long> {
	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	<S extends FootballField> S save(S entity);

	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	<S extends FootballField> Iterable<S> saveAll(Iterable<S> entities);

	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	void deleteById(Long aLong);

	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	void delete(FootballField entity);

	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	void deleteAllById(Iterable<? extends Long> longs);

	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	void deleteAll(Iterable<? extends FootballField> entities);

	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	void deleteAll();
}
