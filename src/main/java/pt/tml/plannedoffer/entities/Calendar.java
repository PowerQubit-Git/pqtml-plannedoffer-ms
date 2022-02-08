package pt.tml.plannedoffer.entities;

import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;
import pt.tml.plannedoffer.gtfs.tables.GtfsCalendarServiceEnum;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "calendar")
@CsvFileName("calendar.txt")
@CsvBindByNameOrder({"ServiceId", "CalendarName", "Period", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "StartDate", "EndDate"})

@IdClass(CsvRowFeedIdCompositeKey.class)
public class Calendar implements FeedIdEntity
{
    @Column(name = "ServiceId")
    private String serviceId;

    @Column(name = "CalendarName")
    private String calendarName;

    @Column(name = "Period")
    private String period;

    @Column(name = "Monday")
    private GtfsCalendarServiceEnum monday;

    @Column(name = "Tuesday")
    private GtfsCalendarServiceEnum tuesday;

    @Column(name = "Wednesday")
    private GtfsCalendarServiceEnum wednesday;

    @Column(name = "Thursday")
    private GtfsCalendarServiceEnum thursday;

    @Column(name = "Friday")
    private GtfsCalendarServiceEnum friday;

    @Column(name = "Saturday")
    private GtfsCalendarServiceEnum saturday;

    @Column(name = "Sunday")
    private GtfsCalendarServiceEnum sunday;

    @Column(name = "StartDate")
    private LocalDate startDate;

    @Column(name = "EndDate")
    private LocalDate endDate;

    @Id
    @CsvIgnore
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;
}
