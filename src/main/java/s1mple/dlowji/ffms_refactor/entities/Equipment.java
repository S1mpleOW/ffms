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
@Table(name = "equipment")
public class Equipment extends AbstractEntity {
	@Column(name = "NAME")
	private String name;

	@Column(name = "STATUS")
	private int status;

	@Column(name = "UNIT_PRICE")
	private int unitPrice;

	@Column(name = "UNIT")
	private String unit;

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "SUPPLIER_ID", referencedColumnName = "ID")
	private Supplier supplier;

	@OneToMany(mappedBy = "equipment", fetch = FetchType.LAZY)
	private List<ImportReceiptDetail> importReceiptDetails;
}
