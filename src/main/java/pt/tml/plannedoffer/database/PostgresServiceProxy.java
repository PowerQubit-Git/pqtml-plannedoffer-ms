package pt.tml.plannedoffer.database;

import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pt.powerqubit.validator.core.table.GtfsFeedContainer;
import pt.tml.plannedoffer.aspects.LogExecutionTime;
import pt.tml.plannedoffer.global.ApplicationState;

@Flogger
@Component
public class PostgresServiceProxy
{

    @Autowired
    PostgresService postgresService;

    /***
     * Persist GTFS entities to Postgres database
     */

    @Async
    @LogExecutionTime(started = "Persisting to DB", completed = "DB persist completed")
    public void persistAllEntities(String feedId, GtfsFeedContainer feedContainer) throws Exception
    {
        postgresService.addFeedInfoToDatabase(feedContainer, feedId);
        postgresService.addAgencyToDatabase(feedContainer, feedId);
        postgresService.addStopsToDatabase(feedContainer, feedId);
        postgresService.addRoutesToDatabase(feedContainer, feedId);
        postgresService.addTripsToDatabase(feedContainer, feedId);
        postgresService.addStopTimesToDatabase(feedContainer, feedId);
        postgresService.addShapesToDatabase(feedContainer, feedId);
        postgresService.addCalendarToDatabase(feedContainer, feedId);
        postgresService.addCalendarDatesToDatabase(feedContainer, feedId); // TODO: MUST SKIP OR EXPORT THIS ONE
        postgresService.generateFrequencies(feedId);
        ApplicationState.validationBusy = false;
        ApplicationState.entityPersistenceBusy = false;
    }
}
