package pt.tml.plannedoffer.entities;

import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;
import pt.tml.plannedoffer.gtfs.tables.GtfsCalendarDateExceptionTypeEnum;
import pt.tml.plannedoffer.gtfs.tables.GtfsHolidayEnum;
import pt.tml.plannedoffer.gtfs.tables.GtfsPeriodEnum;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "calendar_dates")
@CsvBindByNameOrder({"ServiceId", "Calendar_Name", "Holiday", "Period", "Date", "ExceptionType"})

@IdClass(CsvRowFeedIdCompositeKey.class)
public class CalendarDate implements FeedIdEntity
{

    @Column(name = "ServiceId")
    private String serviceId;

    @Column(name = "CalendarName")
    private String calendarName;

    @Column(name = "Holiday")
    private GtfsHolidayEnum holiday;

    @Column(name = "Period")
    private GtfsPeriodEnum period;

    @Column(name = "Date")
    private LocalDate date;

    @Id
    @CsvIgnore
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;

    private GtfsCalendarDateExceptionTypeEnum exceptionType;

}
