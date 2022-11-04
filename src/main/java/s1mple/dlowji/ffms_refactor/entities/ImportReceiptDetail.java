package s1mple.dlowji.ffms_refactor.entities;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "import_receipt_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportReceiptDetail {
	@EmbeddedId
	private ImportReceiptDetailKey id;

	@JoinColumn(name = "IMPORT_RECEIPT_ID")
	@MapsId("importReceiptId")
	@ManyToOne
	private ImportReceipt importReceipt;

	@JoinColumn(name = "ITEM_ID")
	@MapsId("itemId")
	@ManyToOne
	private Item item;

	@Column(name = "DELIVERY_DATE")
	private ZonedDateTime deliveryDate;

	@Column(name = "ORDER_DATE")
	private ZonedDateTime orderDate;

	@Column(name = "QUANTITY")
	private int quantity;
}
