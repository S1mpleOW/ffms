package s1mple.dlowji.ffms_refactor.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import s1mple.dlowji.ffms_refactor.entities.FieldGroup;

@RepositoryRestResource(path = "groups")
public interface FieldGroupRepository extends PagingAndSortingRepository<FieldGroup, Long> {
}
