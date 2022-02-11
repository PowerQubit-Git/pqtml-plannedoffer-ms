package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsDeadRun;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.tml.plannedoffer.entities.DeadRuns;

import java.util.ArrayList;
import java.util.List;

@Flogger
public class DeadRunsMapper
{

    public static List<DeadRuns> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

        List<GtfsDeadRun> gtfsEntities = container.getEntities();
        List<DeadRuns> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new DeadRuns();

            out.setFeedId(feedId);

            out.setCsvRowNumber(gtfsEntity.csvRowNumber());
            out.setAgencyId(gtfsEntity.agencyId());
            out.setBlockId(gtfsEntity.blockId());
            out.setDistTraveled(gtfsEntity.distTraveled());
            out.setDedRunId(gtfsEntity.deadRunId());
            out.setEndTime(gtfsEntity.endTime().toString());
            out.setStartTime(gtfsEntity.startTime().toString());
            out.setEndLocationId(gtfsEntity.endLocationId());
            out.setOvertime(gtfsEntity.overtime());
            out.setBlockId(gtfsEntity.blockId());
            out.setServiceId(gtfsEntity.serviceId());
            out.setShiftId(gtfsEntity.shiftId());

            entities.add(out);

        });

        return  entities;
    }
}
