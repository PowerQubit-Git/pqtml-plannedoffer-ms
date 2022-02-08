package pt.tml.plannedoffer.entities;

import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;

import javax.persistence.*;
import java.time.ZoneId;
import java.util.Locale;

@Entity
@Data
@Table(name = "agency")
@CsvFileName("agency.txt")
@CsvBindByNameOrder({"AgencyId", "AgencyName", "AgencyUrl", "AgencyTimezone", "AgencyLang",})
@IdClass(CsvRowFeedIdCompositeKey.class)
public class Agency implements FeedIdEntity
{
    @Column(name = "AgencyId")
    private String agencyId;

    @Column(name = "AgencyName")
    private String agencyName;

    @Column(name = "AgencyUrl")
    private String agencyUrl;

    @Column(name = "AgencyTimezone")
    private ZoneId agencyTimezone;

    @Column(name = "AgencyLang")
    private Locale agencyLang;

    @Id
    @CsvIgnore()
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore()
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;

}