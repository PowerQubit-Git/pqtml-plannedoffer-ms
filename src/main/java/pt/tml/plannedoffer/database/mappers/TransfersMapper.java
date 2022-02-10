package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsAgency;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.powerqubit.validator.core.table.GtfsTransfer;
import pt.tml.plannedoffer.entities.Agency;
import pt.tml.plannedoffer.entities.Transfers;

import java.util.ArrayList;
import java.util.List;

@Flogger
public class TransfersMapper
{

    public static List<Transfers> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

        List<GtfsTransfer> gtfsEntities = container.getEntities();
        List<Transfers> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new Transfers();

            out.setFeedId(feedId);

            out.setCsvRowNumber(gtfsEntity.csvRowNumber());
            out.setMinTransferTime(gtfsEntity.fromStopId());
            out.setTransferType(gtfsEntity.fromStopId());
            out.setFromStopId(gtfsEntity.fromStopId());
            out.setToStopId(gtfsEntity.toStopId());

            entities.add(out);

        });

        return  entities;
    }
}
