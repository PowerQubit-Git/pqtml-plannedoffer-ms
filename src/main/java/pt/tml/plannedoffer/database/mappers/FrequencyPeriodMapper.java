package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsAgency;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.tml.plannedoffer.entities.Agency;
import pt.tml.plannedoffer.entities.FrequencyPeriod;

import java.util.ArrayList;
import java.util.List;

@Flogger
public class FrequencyPeriodMapper
{

    public static List<FrequencyPeriod> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

//        List<GtfsFrequencyPeriod> gtfsEntities = container.getEntities();
//        List<FrequencyPeriod> entities = new ArrayList<>();
//
//        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));
//
//        gtfsEntities.forEach(gtfsEntity -> {
//            var out = new FrequencyPeriod();
//
//
//            out.setFeedId(feedId);
//
//            out.setFrequencyPeriodId();
//            out.setEndTime();
//            out.setStartTime();
//
//            entities.add(out);
//
//        });

//        return  entities;

        return null;
    }
}
