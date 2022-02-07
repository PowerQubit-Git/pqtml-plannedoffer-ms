package pt.tml.plannedoffer.database;

import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pt.powerqubit.validator.core.table.*;
import pt.tml.plannedoffer.aspects.LogExecutionTime;
import pt.tml.plannedoffer.entities.*;
import pt.tml.plannedoffer.repository.*;

import javax.persistence.EntityManager;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Flogger
@Component
@Data
public class PostgresService
{

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


    /**
     *
     *  Copy GtfsEntities from the Gtfs Container to Jpa Database Entities
     *
     */
    public <T> ArrayList copyEntities(List<GtfsEntity> gtfsEntities, T type, String feedId){
        ArrayList res = new ArrayList<T>();
        gtfsEntities.forEach(e -> {
            Gson gson = new Gson();
            var newObj = gson.fromJson(gson.toJson(e), (Type) type);
            ((pt.tml.plannedoffer.entities.FeedIdEntity) newObj).setFeedId(feedId);
            res.add((T)newObj);
        });
        return res;
    }



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


    @LogExecutionTime
    public void addAgencyToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        GtfsTableContainer agencyContainer = feedContainer.getTableForFilename("agency.txt")
                .orElseThrow(() -> new Exception("agency.txt not found"));

        List<GtfsEntity> entities = agencyContainer.getEntities();

        log.atInfo().log(String.format("Persisting Agency : %d entries", entities.size()));

        ArrayList<Agency> list = copyEntities(entities, Agency.class, feedId);

        try
        {
            agencyRepository.saveAllAndFlush(list);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }



    @LogExecutionTime
    public void addStopsToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        GtfsTableContainer stopsContainer = feedContainer.getTableForFilename("stops.txt")
                .orElseThrow(() -> new Exception("stops.txt not found"));

        List<GtfsEntity> entities = stopsContainer.getEntities();

        log.atInfo().log(String.format("Persisting Stops : %d entries", entities.size()));

        ArrayList<Stop> list = copyEntities(entities, Stop.class, feedId);

        try
        {
            stopRepository.saveAllAndFlush(list);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }


    @LogExecutionTime
    public void addRoutesToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        GtfsTableContainer routeContainer = feedContainer.getTableForFilename("routes.txt")
                .orElseThrow(() -> new Exception("routes.txt not found"));

        List<GtfsEntity> entities = routeContainer.getEntities();

        log.atInfo().log(String.format("Persisting Routes : %d entries", entities.size()));

        ArrayList<Route> list = copyEntities(entities, Route.class, feedId);

        try
        {
            routeRepository.saveAllAndFlush(list);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }


    @LogExecutionTime
    public void addTripsToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        GtfsTableContainer tripsContainer = feedContainer.getTableForFilename("trips.txt").orElseThrow(() -> new Exception("trips.txt not found"));

        List<GtfsEntity> entities = tripsContainer.getEntities();

        log.atInfo().log(String.format("Persisting Trips : %d entries", entities.size()));

        ArrayList<Trip> list = copyEntities(entities, Trip.class, feedId);

        try
        {
            tripRepository.saveAllAndFlush(list);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }


    @LogExecutionTime
    public void addStopTimesToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        GtfsTableContainer stopTimeContainer = feedContainer.getTableForFilename("stop_times.txt").orElseThrow(() -> new Exception("stop_times.txt not found"));
        List<GtfsEntity> entities = stopTimeContainer.getEntities();

        log.atInfo().log(String.format("Persisting StopTimes : %d entries", entities.size()));

        ArrayList<StopTime> list = copyEntities(entities, StopTime.class, feedId);

        try
        {
            stopTimeRepository.saveAllAndFlush(list);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }


    @LogExecutionTime
    public void addCalendarToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        GtfsTableContainer calendarContainer = feedContainer.getTableForFilename("calendar.txt").orElseThrow(() -> new Exception("calendar.txt not found"));
        List<GtfsEntity> entities = calendarContainer.getEntities();

        log.atInfo().log(String.format("Persisting Calendar : %d entries", entities.size()));

        ArrayList<Calendar> list = copyEntities(entities, Calendar.class, feedId);

        try
        {
            calendarRepository.saveAllAndFlush(list);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }


    @LogExecutionTime
    public void addCalendarDatesToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        GtfsTableContainer calendarDatesContainer = feedContainer.getTableForFilename("calendar_dates.txt").orElseThrow(() -> new Exception("calendar_date.txt not found"));
        List<GtfsEntity> entities = calendarDatesContainer.getEntities();

        log.atInfo().log(String.format("Persisting CalendarDates : %d entries", entities.size()));

        ArrayList<CalendarDate> list = copyEntities(entities, CalendarDate.class, feedId);

        try
        {
            calendarDateRepository.saveAllAndFlush(list);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }


    @LogExecutionTime
    public void addShapesToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        GtfsTableContainer shapeContainer = feedContainer.getTableForFilename("shapes.txt").orElseThrow(() -> new Exception("shape.txt not found"));
        List<GtfsEntity> entities = shapeContainer.getEntities();

        log.atInfo().log(String.format("Persisting Shapes : %d entries", entities.size()));

        ArrayList<Shape> list = copyEntities(entities, Shape.class, feedId);

        try
        {
            shapeRepository.saveAllAndFlush(list);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }


    @LogExecutionTime
    public void addFeedInfoToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        GtfsTableContainer feedInfoContainer = feedContainer.getTableForFilename("feed_info.txt").orElseThrow(() -> new Exception("feed_info.txt not found"));
        List<GtfsEntity> entities = feedInfoContainer.getEntities();

        log.atInfo().log(String.format("Persisting FeedInfo : %d entries", entities.size()));

        ArrayList<FeedInfo> list = copyEntities(entities, FeedInfo.class, feedId);

        try
        {
            feedInfoRepository.saveAllAndFlush(list);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }
}
