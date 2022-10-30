package s1mple.dlowji.ffms_refactor.entities;

import lombok.*;
import s1mple.dlowji.ffms_refactor.entities.enums.PaymentStatus;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "import_receipt")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ImportReceipt extends AbstractEntity {
    @Column(name = "TOTAL_PRICE")
    private int totalPrice;

    @Column(name = "PAYMENT_STATUS")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "NOTE")
    private String note;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @OneToMany(mappedBy = "importReceipt", fetch = FetchType.LAZY)
    private List<ImportReceiptDetail> importReceiptDetails;
}
