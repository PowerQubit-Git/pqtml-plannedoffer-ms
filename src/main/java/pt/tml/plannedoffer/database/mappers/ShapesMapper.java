package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsShape;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.tml.plannedoffer.entities.Shape;

import java.util.ArrayList;
import java.util.List;

@Flogger
public class ShapesMapper
{

    public static List<Shape> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

        List<GtfsShape> gtfsEntities = container.getEntities();
        List<Shape> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new Shape();


            out.setFeedId(feedId);

            out.setCsvRowNumber(gtfsEntity.csvRowNumber());
            out.setShapeId(gtfsEntity.shapeId());
            out.setShapePtLat(gtfsEntity.shapePtLat());
            out.setShapePtLon(gtfsEntity.shapePtLon());
            out.setShapePtSequence(gtfsEntity.shapePtSequence());
            out.setShapeDistTraveled(gtfsEntity.shapeDistTraveled());

            entities.add(out);

        });

        return  entities;
    }
}
