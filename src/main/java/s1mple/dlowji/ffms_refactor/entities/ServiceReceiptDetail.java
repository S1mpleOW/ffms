package s1mple.dlowji.ffms_refactor.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "service_receipt_detail")
public class ServiceReceiptDetail {
    @EmbeddedId
    private ServiceReceiptDetailKey id;

    @JoinColumn(name = "SERVICE_RECEIPT_ID")
    @MapsId("serviceReceiptId")
    @ManyToOne
    private ServiceReceipt serviceReceipt;

    @JoinColumn(name = "SERVICE_ID")
    @MapsId("serviceId")
    @ManyToOne
    private Service service;

    // ENUM
    @Column(name = "UNIT_PRICE")
    private int unitPrice;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "ORDER_DATE")
    private ZonedDateTime orderDate;
}
