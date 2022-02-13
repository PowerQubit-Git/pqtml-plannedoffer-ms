package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsRoute;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.tml.plannedoffer.entities.Route;

import java.util.ArrayList;
import java.util.List;

@Flogger
public class RoutesMapper
{

    public static List<Route> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

        List<GtfsRoute> gtfsEntities = container.getEntities();
        List<Route> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new Route();


            out.setFeedId(feedId);

            out.setFeedId(feedId);
            out.setCsvRowNumber(gtfsEntity.csvRowNumber());
            out.setLineId(gtfsEntity.lineId());
            out.setLineShortName(gtfsEntity.LineShortName());
            out.setRouteLongName(gtfsEntity.routeLongName());
            out.setRouteId(gtfsEntity.routeId());
            out.setAgencyId(gtfsEntity.agencyId());
            out.setRouteOrigin(gtfsEntity.routeOrigin());
            out.setRouteDestination(gtfsEntity.routeDestination());
            out.setRouteShortName(gtfsEntity.routeShortName());
            out.setRouteLongName(gtfsEntity.routeLongName());
            out.setRouteDesc(gtfsEntity.routeDesc());
            out.setRouteRemarks(gtfsEntity.routeRemarks());
            out.setRouteType(gtfsEntity.routeType());
            out.setContract(gtfsEntity.contract());
            out.setPathType(gtfsEntity.pathType());
            out.setCircular(gtfsEntity.circular());
            out.setSchool(gtfsEntity.school());
            out.setContinuousPickup(gtfsEntity.continuousPickup());
            out.setContinuousDropOff(gtfsEntity.continuousDropOff());

            entities.add(out);

        });

        return  entities;
    }
}
