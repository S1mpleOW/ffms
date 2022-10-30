package s1mple.dlowji.ffms_refactor.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "booked_ticket_detail")
public class BookedTicketDetail {
    @EmbeddedId
    private BookedTicketDetailKey id;

    @JoinColumn(name = "BOOKED_TICKET_ID")
    @MapsId("bookedTicketId")
    @ManyToOne
    private BookedTicket bookedTicket;

    @JoinColumn(name = "FOOTBALL_FIELD_ID")
    @MapsId("footballFieldId")
    @ManyToOne
    private FootballField footballField;

    @Column(name = "START_TIME")
    private LocalTime startTime;

    @Column(name = "END_TIME")
    private LocalTime endTime;

    @Column(name = "DEPOSIT")
    private int deposit;

    @Column(name = "ORDER_DATE")
    private ZonedDateTime orderDate;
}
