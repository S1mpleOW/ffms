package s1mple.dlowji.ffms_refactor.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	@Column(name = "SELL_PRICE")
	private int priceSell;

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinTable(name = "service_item", joinColumns = @JoinColumn(name =
	"SERVICE_ID",
	referencedColumnName = "ID"),
	inverseJoinColumns = @JoinColumn(name = "ITEM_ID", referencedColumnName =
	"ID"))
	private Set<Item> items = new HashSet<>();

	@OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
	private List<ServiceReceiptDetail> serviceReceiptDetails;
}