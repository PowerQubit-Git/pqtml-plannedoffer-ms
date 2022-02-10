package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsAgency;
import pt.powerqubit.validator.core.table.GtfsPathway;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.tml.plannedoffer.entities.Agency;
import pt.tml.plannedoffer.entities.Pathway;

import java.util.ArrayList;
import java.util.List;

@Flogger
public class PathwayMapper
{

    public static List<Pathway> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

        List<GtfsPathway> gtfsEntities = container.getEntities();
        List<Pathway> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new Pathway();

            out.setFeedId(feedId);

            out.setCsvRowNumber(gtfsEntity.csvRowNumber());
            out.setPathwayId(gtfsEntity.pathwayId());
            out.setPathwayMode(gtfsEntity.pathwayMode());
            out.setLength(gtfsEntity.length());
            out.setFromStopId(gtfsEntity.fromStopId());
            out.setLength(gtfsEntity.csvRowNumber());
            out.setIsBidirectional(gtfsEntity.isBidirectional());
            out.setMaxSlope(gtfsEntity.maxSlope());
            out.setMinWidth(gtfsEntity.minWidth());
            out.setReversedSignpostedAs(gtfsEntity.reversedSignpostedAs());
            out.setStairCount(gtfsEntity.isBidirectionalValue());
            out.setTraversalTime(gtfsEntity.traversalTime());
            out.setSignpostedAs(gtfsEntity.signpostedAs());

            entities.add(out);

        });

        return  entities;
    }
}
