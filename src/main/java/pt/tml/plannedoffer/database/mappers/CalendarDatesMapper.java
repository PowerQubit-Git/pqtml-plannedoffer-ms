package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsAgency;
import pt.powerqubit.validator.core.table.GtfsCalendarDate;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.tml.plannedoffer.entities.Agency;
import pt.tml.plannedoffer.entities.CalendarDate;

import java.util.ArrayList;
import java.util.List;

@Flogger
public class CalendarDatesMapper
{

    public static List<CalendarDate> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

        List<GtfsCalendarDate> gtfsEntities = container.getEntities();
        List<CalendarDate> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new CalendarDate();


            out.setFeedId(feedId);

            out.setCsvRowNumber(gtfsEntity.csvRowNumber());
            out.setServiceId(gtfsEntity.serviceId());
            out.setCalendarName(gtfsEntity.calendarName());
            out.setHoliday(gtfsEntity.holiday());
            out.setPeriod(gtfsEntity.period());
            //out.setDate(gtfsEntity.date());
            out.setExceptionType(gtfsEntity.exceptionType());

            entities.add(out);

        });

        return  entities;
    }
}
