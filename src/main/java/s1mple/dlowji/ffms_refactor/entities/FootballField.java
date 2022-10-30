package s1mple.dlowji.ffms_refactor.entities;

import lombok.*;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;

@Entity
@Table(name = "football_field")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FootballField extends AbstractEntity {
	@Column(name = "NAME")
	private String name;

	@Column(name = "TYPE")
	private int type;

	@Column(name = "IMAGE")
	@Lob
	private String image;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FIELD_GROUP_ID", referencedColumnName = "ID")
	private FieldGroup fieldGroup;
}
