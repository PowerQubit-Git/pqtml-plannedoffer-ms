package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsAgency;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.powerqubit.validator.core.table.GtfsTrip;
import pt.tml.plannedoffer.entities.Agency;
import pt.tml.plannedoffer.entities.Trip;

import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;

@Flogger
public class TripsMapper
{

    public static List<Trip> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

        List<GtfsTrip> gtfsEntities = container.getEntities();
        List<Trip> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new Trip();

            out.setFeedId(feedId);
            out.setCsvRowNumber(gtfsEntity.csvRowNumber());
            out.setRouteId(gtfsEntity.routeId());
            out.setServiceId(gtfsEntity.serviceId());
            out.setTripId(gtfsEntity.tripId());
            // out.setTripFirt(gtfsEntity.tripFirst());
            // out.setTripLast(gtfsEntity.tripLast());
            out.setTripHeadsign(gtfsEntity.tripHeadsign());
            out.setDirectionId(gtfsEntity.directionId());
            out.setShapeId(gtfsEntity.shapeId());
            out.setWheelchairAccessible(gtfsEntity.wheelchairAccessible());
            out.setBikesAllowed(gtfsEntity.bikesAllowed());

            entities.add(out);

        });

        return  entities;
    }
}
