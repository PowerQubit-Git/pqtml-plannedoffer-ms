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
import java.time.ZoneId;
import java.util.Currency;
import java.util.Locale;

@Entity
@Getter
@Setter
@Table(name = "fare_attributes")
@CsvFileName("fare_attributes.txt")
@CsvBindByNameOrder({"FareId", "Price", "CurrencyType", "PaymentMethod", "Transfers","AgencyId","TransferDuration",})
@IdClass(CsvRowFeedIdCompositeKey.class)
public class FareAttributes implements FeedIdEntity
{
    @Column(name = "FareId")
    private String fareId;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "CurrencyType")
    private Currency currencyType;

    @Column(name = "PaymentMethod")
    private GtfsFareAttributePaymentMethod paymentMethod;

    @Column(name = "Transfers")
    private GtfsFareAttributeTransfers transfers;

    @Column(name = "AgencyId")
    private String agencyId;

    @Column(name = "TransferDuration")
    private int transferDuration;

    @Id
    @CsvIgnore()
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore()
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;

}