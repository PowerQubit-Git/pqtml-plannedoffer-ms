package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsLevel;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.tml.plannedoffer.entities.Level;

import java.util.ArrayList;
import java.util.List;

@Flogger
public class LevelMapper
{

    public static List<Level> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

        List<GtfsLevel> gtfsEntities = container.getEntities();
        List<Level> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new Level();


            out.setFeedId(feedId);

            out.setCsvRowNumber(gtfsEntity.csvRowNumber());
            out.setLevelId(gtfsEntity.levelId());
            out.setLevelIndex(gtfsEntity.levelIndex());
            out.setLevelName(gtfsEntity.levelName());

            entities.add(out);

        });

        return  entities;
    }
}
