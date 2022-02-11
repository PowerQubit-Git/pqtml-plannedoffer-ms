package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsLayovers;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.tml.plannedoffer.entities.Layovers;

import java.util.ArrayList;
import java.util.List;

@Flogger
public class LayoversMapper
{

    public static List<Layovers> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

        List<GtfsLayovers> gtfsEntities = container.getEntities();
        List<Layovers> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new Layovers();

            out.setFeedId(feedId);

            out.setCsvRowNumber(gtfsEntity.csvRowNumber());
            out.setAgencyId(gtfsEntity.agencyId());
            out.setBlockId(gtfsEntity.blockId());
            out.setServiceId(gtfsEntity.serviceId());
            out.setStartTime(gtfsEntity.startShiftId());
            out.setEndOvertime(gtfsEntity.endOvertime());
            out.setEndDuration(gtfsEntity.endDuration());
            out.setEndTime(gtfsEntity.endTime().toString());
            out.setEndShiftId(gtfsEntity.endShiftId());
            out.setLayoverId(gtfsEntity.layoverId());
            out.setLocationId(gtfsEntity.locationId());
            out.setStartOvertime(gtfsEntity.startOvertime());
            out.setStartShiftId(gtfsEntity.startShiftId());

            entities.add(out);

        });

        return  entities;
    }
}
