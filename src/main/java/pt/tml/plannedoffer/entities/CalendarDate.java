package pt.tml.plannedoffer.entities;

import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import pt.powerqubit.validator.core.table.GtfsCalendarDateExceptionType;
import pt.powerqubit.validator.core.table.GtfsHoliday;
import pt.powerqubit.validator.core.table.GtfsPeriod;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "calendar_dates")
@CsvBindByNameOrder({"ServiceId", "Calendar_Name", "Holiday", "Period", "Date", "ExceptionType"})

@IdClass(CsvRowFeedIdCompositeKey.class)
public class CalendarDate
{

    @Column(name = "ServiceId")
    private String serviceId;

    @Column(name = "CalendarName")
    private String calendarName;

    @Column(name = "Holiday")
    private GtfsHoliday holiday;

    @Column(name = "Period")
    private GtfsPeriod period;

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

    private GtfsCalendarDateExceptionType exceptionType;

}
