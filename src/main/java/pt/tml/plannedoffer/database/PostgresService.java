package pt.tml.plannedoffer.database;

import lombok.Data;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pt.powerqubit.validator.core.table.GtfsFeedContainer;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.tml.plannedoffer.aspects.LogExecutionTime;
import pt.tml.plannedoffer.database.mappers.*;
import pt.tml.plannedoffer.repository.*;

import javax.persistence.EntityManager;

@Flogger
@Component
@Data
public class PostgresService
{

    @Autowired
    AttributionRepository attributionRepository;
    @Autowired
    TransferRepository transferRepository;
    @Autowired
    PathwayRepository pathwayRepository;
    @Autowired
    LevelRepository levelRepository;
    @Autowired
    FrequencyPeriodRepository frequencyPeriodRepository;
    @Autowired
    FareRuleRepository fareRuleRepository;
    @Autowired
    FrequencyRepository frequencyRepository;
    @Autowired
    FareAttributesRepository fareAttributesRepository;
    @Autowired
    StopRepository stopRepository;
    @Autowired
    AgencyRepository agencyRepository;
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    TripRepository tripRepository;
    @Autowired
    StopTimeRepository stopTimeRepository;
    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    CalendarDateRepository calendarDateRepository;
    @Autowired
    ShapeRepository shapeRepository;
    @Autowired
    FeedInfoRepository feedInfoRepository;
    @Autowired
    EntityManager entityManager;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DeadRunRepository deadRunRepository;
    @Autowired
    SchedulesRepository schedulesRepository;
    @Autowired
    BlocksRepository blocksRepository;
    @Autowired
    VehiclesRepository vehiclesRepository;
    @Autowired
    LayoverRepository layoverRepository;



    /**
     * Generate frequencies for a plan
     * (Inserts records in "frequencies" table)
     */
    @LogExecutionTime(started = "Generating frequencies")
    public void generateFrequencies(String feedId)
    {
        try
        {
            String sql = String.format("call sp_generate_frequencies('%s');", feedId);
            jdbcTemplate.execute(sql);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log(String.format("Unable to generate Frequencies for plan %s", feedId));
            throw (e);
        }
    }


    @LogExecutionTime(started = "Deleting plan from Postgres")
    public void deletePlan(String feedId)
    {
        try
        {
            String sql = String.format("call sp_erase_plan('%s');", feedId);
            jdbcTemplate.execute(sql);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log(String.format("Unable to delete plan %s", feedId));
            throw (e);
        }
    }

    /**
     * Add Agency fields to database
     * (Inserts records in "agency" table)
     */
    @LogExecutionTime
    public void addAgencyToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "agency.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = AgencyMapper.map(tableName, container, feedId);

        try
        {
            agencyRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }


    /**
     * Add Stops fields to database
     * (Inserts records in "stops" table)
     */
    @LogExecutionTime
    public void addStopsToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "stops.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = StopsMapper.map(tableName, container, feedId);

        try
        {
            stopRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }


    /**
     * Add Routes fields to database
     * (Inserts records in "routes" table)
     */
    @LogExecutionTime
    public void addRoutesToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "routes.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = RoutesMapper.map(tableName, container, feedId);

        try
        {
            routeRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }


    /**
     * Add Trip fields to database
     * (Inserts records in "stops" table)
     */
    @LogExecutionTime
    public void addTripsToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "trips.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = TripsMapper.map(tableName, container, feedId);

        try
        {
            tripRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }

    /**
     * Add StopTimes fields to database
     * (Inserts records in "stop_times" table)
     */
    @LogExecutionTime
    public void addStopTimesToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "stop_times.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = StopTimeMapper.map(tableName, container, feedId);

        try
        {
            stopTimeRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }

    /**
     * Add Calendar fields to database
     * (Inserts records in "calendar" table)
     */
    @LogExecutionTime
    public void addCalendarToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "calendar.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = CalendarMapper.map(tableName, container, feedId);

        try
        {
            calendarRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }

    /**
     * Add CalendarDates fields to database
     * (Inserts records in "calendar_dates" table)
     */
    @LogExecutionTime
    public void addCalendarDatesToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "calendar_dates.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = CalendarMapper.map(tableName, container, feedId);

        try
        {
            calendarRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }

    /**
     * Add Shapes fields to database
     * (Inserts records in "shapes" table)
     */
    @LogExecutionTime
    public void addShapesToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "shapes.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = ShapesMapper.map(tableName, container, feedId);

        try
        {
            shapeRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }


    /**
     * Add FeedInfo fields to database
     * (Inserts records in "feed_info" table)
     */
    @LogExecutionTime
    public void addFeedInfoToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "feed_info.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = FeedInfoMapper.map(tableName, container, feedId);

        try
        {
            feedInfoRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }

    @LogExecutionTime
    public void addAttributionsToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "attributions.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = AttributionsMapper.map(tableName, container, feedId);

        try
        {
            attributionRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }

    @LogExecutionTime
    public void addFareAttributesToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "fare_attributes.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = FareAttributesMapper.map(tableName, container, feedId);

        try
        {
            fareAttributesRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }

    @LogExecutionTime
    public void addFareRuleToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "fare_rules.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = FareRuleMapper.map(tableName, container, feedId);

        try
        {
            fareRuleRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }

    @LogExecutionTime
    public void addFrequencyToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "frequencies.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = FrequencyMapper.map(tableName, container, feedId);

        try
        {
            frequencyRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }

    @LogExecutionTime
    public void addLevelToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "levels.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = LevelMapper.map(tableName, container, feedId);

        try
        {
            levelRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }

    @LogExecutionTime
    public void addPathwayToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "pathways.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = PathwayMapper.map(tableName, container, feedId);

        try
        {
            pathwayRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }

    @LogExecutionTime
    public void addTransferToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "transfers.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = TransfersMapper.map(tableName, container, feedId);

        try
        {
            transferRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }


    /////////////////////////////////////////////////// UP TESTED AND WORKING
    // NEW STUFF
    ////////////////////////////////////////////////// DOWN NOT TESTED

    @LogExecutionTime
    public void addDeadRunsDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "dead_runs.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = DeadRunsMapper.map(tableName, container, feedId);

        try
        {
            deadRunRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }

    @LogExecutionTime
    public void addSchedulesDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "schedules.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = ScheduleMapper.map(tableName, container, feedId);

        try
        {
            schedulesRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }

    @LogExecutionTime
    public void addBlocksDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "blocks.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = BlocksMapper.map(tableName, container, feedId);

        try
        {
            blocksRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }

    @LogExecutionTime
    public void addVehiclesDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "vehicles.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = VehiclesMapper.map(tableName, container, feedId);

        try
        {
            vehiclesRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }

    @LogExecutionTime
    public void addLayoverDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        var tableName = "vehicles.txt";
        GtfsTableContainer container = feedContainer.getTableForFilename(tableName)
                .orElseThrow(() -> new Exception(String.format("%s not found", tableName)));
        var out = LayoversMapper.map(tableName, container, feedId);

        try
        {
            layoverRepository.saveAllAndFlush(out);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }

}

