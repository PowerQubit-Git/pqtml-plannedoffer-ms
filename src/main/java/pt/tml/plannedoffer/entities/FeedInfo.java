package pt.tml.plannedoffer.entities;

import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;

import javax.persistence.*;
import java.util.Locale;

@Entity
@Data
@Table(name = "feed_info")
@IdClass(CsvRowFeedIdCompositeKey.class)
@CsvBindByNameOrder({"FeedInfoId", "FeedPublisherName", "FeedPublisherUrl", "Feedlang", "FeedStartDate", "FeedEndDate", "FeedVersion", "FeedDesc", "FeedRemarks"})

public class FeedInfo implements FeedIdEntity {

    @Column(name = "FeedInfoId")
    private Long feedInfoId;

    @Column(name = "FeedPublisherName")
    private String feedPublisherName;

    @Column(name = "FeedPublisherUrl")
    private String feedPublisherUrl;

    @Column(name = "FeedLang")
    private Locale feedLang;

    @Column(name = "FeedStartDate")
    private String feedStartDate;

    @Column(name = "FeedEndDate")
    private String feedEndDate;

    @Column(name = "FeedVersion")
    private String feedVersion;

    @Column(name = "FeedDesc")
    private String feedDesc;

    @Column(name = "FeedRemarks")
    private String feedRemarks;

    @Column(name = "FeedContactEmail")
    private String feedContactEmail;

    @Column(name = "FeedContactUrl")
    private String feedContactUrl;

    @Id
    @CsvIgnore
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;

}
