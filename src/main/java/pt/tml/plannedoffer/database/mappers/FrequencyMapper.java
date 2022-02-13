package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsFrequencie;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.tml.plannedoffer.entities.Frequency;

import java.util.ArrayList;
import java.util.List;

@Flogger
public class FrequencyMapper
{

    public static List<Frequency> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

        List<GtfsFrequencie> gtfsEntities = container.getEntities();
        List<Frequency> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new Frequency();


            out.setFeedId(feedId);

            out.setCsvRowNumber(gtfsEntity.csvRowNumber());
            out.setFrequency(gtfsEntity.frequency());
//          out.setStartTime(gtfsEntity.startTime());
//          out.setEndTime(gtfsEntity.endTime());
//          out.setExactTimes(gtfsEntity.csvRowNumber());
            out.setHeadwaySecs(gtfsEntity.headwaySecs());
            out.setPassengerCounting(gtfsEntity.passengerCounting());
            out.setPropulsion(gtfsEntity.propulsion());
            out.setTypology(gtfsEntity.typology());
            out.setVideoSurveillance(gtfsEntity.videoSurveillance());
            out.setTripId(gtfsEntity.tripId());

            entities.add(out);

        });

        return  entities;
    }
}
