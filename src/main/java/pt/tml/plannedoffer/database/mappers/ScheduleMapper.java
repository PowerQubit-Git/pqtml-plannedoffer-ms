package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsSchedules;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.tml.plannedoffer.entities.Schedules;

import java.util.ArrayList;
import java.util.List;

@Flogger
public class ScheduleMapper
{

    public static List<Schedules> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

        List<GtfsSchedules> gtfsEntities = container.getEntities();
        List<Schedules> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new Schedules();

            out.setFeedId(feedId);

            out.setCsvRowNumber(gtfsEntity.csvRowNumber());
            out.setBlockId(gtfsEntity.blockId());
            out.setDriver_id(gtfsEntity.driverId());
            out.setServiceId(gtfsEntity.serviceId());
            out.setShift_id(gtfsEntity.shiftId());
            out.setVehicle_id(gtfsEntity.vehicleId());

            entities.add(out);
        });

        return  entities;
    }
}
