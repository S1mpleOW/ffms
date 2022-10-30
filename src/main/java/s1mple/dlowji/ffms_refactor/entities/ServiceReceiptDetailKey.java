package s1mple.dlowji.ffms_refactor.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Setter
@Getter
public class ServiceReceiptDetailKey implements Serializable {
    @Column(name = "SERVICE_ID")
    private Long serviceId;

    @Column(name = "SERVICE_RECEIPT_ID")
    private Long serviceReceiptId;
}
