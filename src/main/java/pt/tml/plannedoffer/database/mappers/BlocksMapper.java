package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsBlocks;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.tml.plannedoffer.entities.Blocks;

import java.util.ArrayList;
import java.util.List;

@Flogger
public class BlocksMapper {

    public static List<Blocks> map(String fileName, GtfsTableContainer container, String feedId) throws Exception {

        List<GtfsBlocks> gtfsEntities = container.getEntities();
        List<Blocks> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new Blocks();

            out.setFeedId(feedId);

            out.setCsvRowNumber(gtfsEntity.csvRowNumber());
            out.setBlockId(gtfsEntity.blockId());
            out.setAvailableSeats(gtfsEntity.availableSeats());
            out.setTypology(gtfsEntity.typology());
            out.setRegistrationDate(gtfsEntity.registrationDate().toYYYYMMDD());
            out.setAvailableStanding(gtfsEntity.availableSeats());
            out.setPropulsion(gtfsEntity.propulsion());
            out.setLoweredFloor(gtfsEntity.loweredFloor());
            out.setEmission(gtfsEntity.emission());
            out.setClasses(gtfsEntity.classess());

            entities.add(out);

        });

        return entities;
    }
}
