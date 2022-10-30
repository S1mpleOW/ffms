package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import s1mple.dlowji.ffms_refactor.entities.FootballField;

@RepositoryRestResource(path = "football_fields")
public interface FootballFieldRepository extends PagingAndSortingRepository<FootballField, Long> {
	@Override
	@PreAuthorize("hasRole('ADMIN')")
	<S extends FootballField> S save(S entity);

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	<S extends FootballField> Iterable<S> saveAll(Iterable<S> entities);

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	void deleteById(Long aLong);

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	void delete(FootballField entity);

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	void deleteAllById(Iterable<? extends Long> longs);

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	void deleteAll(Iterable<? extends FootballField> entities);

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	void deleteAll();
}
