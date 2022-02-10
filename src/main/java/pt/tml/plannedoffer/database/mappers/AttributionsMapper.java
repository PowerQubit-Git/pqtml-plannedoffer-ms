package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsAttribution;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.tml.plannedoffer.entities.Attribution;

import java.util.ArrayList;
import java.util.List;

@Flogger
public class AttributionsMapper
{

    public static List<Attribution> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

        List<GtfsAttribution> gtfsEntities = container.getEntities();
        List<Attribution> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new Attribution();


            out.setFeedId(feedId);

              out.setAgencyId(gtfsEntity.agencyId());
              out.setCsvRowNumber(gtfsEntity.csvRowNumber());
              out.setAttributionId(gtfsEntity.attributionId());
              out.setAttributionUrl(gtfsEntity.attributionUrl());
              out.setAttributionEmail(gtfsEntity.attributionEmail());
              out.setAttributionPhone(gtfsEntity.attributionPhone());
              out.setRouteId(gtfsEntity.routeId());
              out.setIsAuthority(gtfsEntity.isAuthority());
              out.setIsOperator(gtfsEntity.isOperator());
              out.setIsProducer(gtfsEntity.isProducer());
              out.setOrganizationName(gtfsEntity.organizationName());
              out.setTripId(gtfsEntity.tripId());

            entities.add(out);

        });

        return  entities;
    }
}
