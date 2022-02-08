package pt.tml.plannedoffer.entities;

import com.opencsv.bean.CsvIgnore;
import lombok.Getter;
import lombok.Setter;
import pt.powerqubit.validator.core.table.GtfsFareAttributePaymentMethod;
import pt.powerqubit.validator.core.table.GtfsFareAttributeTransfers;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Currency;

@Entity
@Getter
@Setter
@Table(name = "fare_rules")
@CsvFileName("fare_rules.txt")
@CsvBindByNameOrder({"FareId", "RouteId", "OriginId", "DestinationId", "ContainsId",})
@IdClass(CsvRowFeedIdCompositeKey.class)
public class FareRule implements FeedIdEntity
{
    @Column(name = "FareId")
    private String fareId;

    @Column(name = "RouteId")
    private String routeId;

    @Column(name = "OriginId")
    private String originId;

    @Column(name = "DestinationId")
    private String destinationId;

    @Column(name = "ContainsId")
    private String containsId;

    @Id
    @CsvIgnore()
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore()
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;

}