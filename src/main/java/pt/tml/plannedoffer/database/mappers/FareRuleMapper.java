package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsFareRule;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.tml.plannedoffer.entities.FareRule;

import java.util.ArrayList;
import java.util.List;

@Flogger
public class FareRuleMapper
{

    public static List<FareRule> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

        List<GtfsFareRule> gtfsEntities = container.getEntities();
        List<FareRule> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new FareRule();


            out.setFeedId(feedId);

            out.setCsvRowNumber(gtfsEntity.csvRowNumber());
            out.setFareId(gtfsEntity.fareId());
            out.setContainsId(gtfsEntity.containsId());
            out.setDestinationId(gtfsEntity.destinationId());
            out.setOriginId(gtfsEntity.originId());
            out.setRouteId(gtfsEntity.routeId());

            entities.add(out);

        });

        return  entities;
    }
}
