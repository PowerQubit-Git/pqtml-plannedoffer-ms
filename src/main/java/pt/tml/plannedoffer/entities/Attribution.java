package pt.tml.plannedoffer.entities;

import com.opencsv.bean.CsvIgnore;
import lombok.Getter;
import lombok.Setter;
import pt.powerqubit.validator.core.table.GtfsAttributionRole;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "attribution")
@CsvFileName("attribution.txt")
@CsvBindByNameOrder({"AttributionId", "AgencyId", "RouteId", "TripId", "OrganizationName","IsProducer","IsOperator","IsAuthority","AttributionUrl","AttributionEmail","AttributionPhone",})
@IdClass(CsvRowFeedIdCompositeKey.class)
public class Attribution implements FeedIdEntity
{
    @Column(name = "AttributionId")
    private String attributionId;

    @Column(name = "AgencyId")
    private String agencyId;

    @Column(name = "RouteId")
    private String routeId;

    @Column(name = "TripId")
    private String tripId;

    @Column(name = "OrganizationName")
    private String organizationName;

    @Column(name = "IsProducer")
    private GtfsAttributionRole isProducer;

    @Column(name = "IsOperator")
    private GtfsAttributionRole isOperator;

    @Column(name = "IsAuthority")
    private GtfsAttributionRole isAuthority;

    @Column(name = "AttributionUrl")
    private String attributionUrl;

    @Column(name = "AttributionEmail")
    private String attributionEmail;

    @Column(name = "AttributionPhone")
    private String attributionPhone;

    @Id
    @CsvIgnore()
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore()
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;

}