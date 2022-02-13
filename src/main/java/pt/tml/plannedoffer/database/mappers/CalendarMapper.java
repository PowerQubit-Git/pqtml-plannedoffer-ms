package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsCalendar;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.tml.plannedoffer.entities.Calendar;

import java.util.ArrayList;
import java.util.List;

@Flogger
public class CalendarMapper
{

    public static List<Calendar> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

        List<GtfsCalendar> gtfsEntities = container.getEntities();
        List<Calendar> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new Calendar();


            out.setFeedId(feedId);

            out.setCsvRowNumber(gtfsEntity.csvRowNumber());
            out.setServiceId(gtfsEntity.serviceId());
            out.setCalendarName(gtfsEntity.calendarName());
            out.setPeriod(gtfsEntity.period());
            out.setMonday(gtfsEntity.monday());
            out.setTuesday(gtfsEntity.tuesday());
            out.setWednesday(gtfsEntity.wednesday());
            out.setThursday(gtfsEntity.thursday());
            out.setFriday(gtfsEntity.friday());
            out.setSaturday(gtfsEntity.saturday());
            out.setSunday(gtfsEntity.sunday());
//          out.setStartDate(gtfsEntity.startDate());
//          out.setEndDate(gtfsEntity.endDate());

            entities.add(out);

        });

        return  entities;
    }
}
