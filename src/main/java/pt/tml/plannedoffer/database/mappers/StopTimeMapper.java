package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsStopTime;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.tml.plannedoffer.entities.StopTime;

import java.util.ArrayList;
import java.util.List;

@Flogger
public class StopTimeMapper
{

    public static List<StopTime> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

        List<GtfsStopTime> gtfsEntities = container.getEntities();
        List<StopTime> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new StopTime();


            out.setFeedId(feedId);

            out.setCsvRowNumber(gtfsEntity.csvRowNumber());
            out.setTripId(gtfsEntity.tripId());
            out.setArrivalTime(gtfsEntity.arrivalTime().toHHMMSS());
            out.setDepartureTime(gtfsEntity.departureTime().toHHMMSS());
            out.setStopId(gtfsEntity.stopId());
            out.setStopSequence(gtfsEntity.stopSequence());
            out.setStopHeadsign(gtfsEntity.stopHeadsign());
            out.setContinuousPickup(gtfsEntity.continuousPickup());
            out.setContinuousDropOff(gtfsEntity.continuousDropOff());
            out.setShapeDistTraveled(gtfsEntity.shapeDistTraveled());
            out.setTimepoint(gtfsEntity.timepoint());

            entities.add(out);

        });

        return  entities;
    }
}
