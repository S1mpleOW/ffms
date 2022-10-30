package s1mple.dlowji.ffms_refactor.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "service")
public class Service extends AbstractEntity {

	@Column(name = "NAME")
	private String name;

	@Column(name = "STATUS")
	private int status;

	@Column(name = "PRICE", columnDefinition = "DOUBLE DEFAULT 0")
	private double price;

	@Column(name = "NOTE")
	private String note;

	@OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
	private List<ServiceReceiptDetail> serviceReceiptDetails;
}