package pt.tml.plannedoffer.database;

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
     * Generate frequencies for a plan
     * (Inserts records in "frequencies" table)
     *
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


    /**
     *
     * Add Agency fields to database
     * (Inserts records in "agency" table)
     *
     */
    @LogExecutionTime
    public void addAgencyToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        GtfsTableContainer agencyContainer = feedContainer.getTableForFilename("agency.txt").orElseThrow(() -> new Exception("agency.txt not found"));
        List<GtfsAgency> entities = agencyContainer.getEntities();
        List<Agency> ioAgency = new ArrayList<>();
        log.atInfo().log(String.format("Persisting Agency : %d entries", entities.size()));
        entities.forEach(agency -> {
            Agency newAgency = new Agency();
            newAgency.setFeedId(feedId);
            newAgency.setAgencyId(agency.agencyId());
            newAgency.setAgencyName(agency.agencyName());
            newAgency.setAgencyUrl(agency.agencyUrl());
//          newAgency.setAgencyTimezone(agency.agencyTimezone());
            newAgency.setAgencyLang(agency.agencyLang());
            newAgency.setCsvRowNumber(agency.csvRowNumber());
            ioAgency.add(newAgency);
        });
        try
        {
            agencyRepository.saveAllAndFlush(ioAgency);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }


    /**
     *
     * Add Stops fields to database
     * (Inserts records in "stops" table)
     *
     */
    @LogExecutionTime
    public void addStopsToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        GtfsTableContainer stopsContainer = feedContainer.getTableForFilename("stops.txt").orElseThrow(() -> new Exception("stops.txt not found"));
        List<GtfsStop> entities = stopsContainer.getEntities();
        List<Stop> ioStops = new ArrayList<>();
        log.atInfo().log(String.format("Persisting Stops : %d entries", entities.size()));
        entities.forEach(stop -> {
            Stop newStop = new Stop();
            newStop.setFeedId(feedId);
            newStop.setCsvRowNumber(stop.csvRowNumber());
            newStop.setStopId(stop.stopId());
            newStop.setStopIdStepp(stop.stopIdStepp());
            newStop.setStopCode(stop.stopCode());
            newStop.setStopName(stop.stopName());
            newStop.setStopDesc(stop.stopDesc());
            newStop.setStopRemarks(stop.stopRemarks());
            newStop.setStopLat(stop.stopLat());
            newStop.setStopLon(stop.stopLon());
            newStop.setZoneShift(stop.zone_shift());
            newStop.setLocationType(stop.locationType());
            newStop.setParentStation(stop.parentStation());
            newStop.setWheelchairBoarding(stop.wheelchairBoarding());
            newStop.setPlatformCode(stop.platformCode());
            newStop.setEntranceRestriction(stop.entranceRestriction());
            newStop.setExitRestriction(stop.exitRestriction());
            newStop.setSlot(stop.slot());
            newStop.setSignalling(stop.signalling());
            newStop.setShelter(stop.shelter());
            newStop.setBench(stop.bench());
            newStop.setNetworkMap(stop.networkMap());
            newStop.setSchedule(stop.schedule());
            newStop.setRealTimeInformation(stop.realTimeInformation());
            newStop.setTariff(stop.tariff());
            newStop.setPreservationState(stop.preservationState());
            newStop.setEquipment(stop.equipment());
            newStop.setObservations(stop.observations());
            //newStop.setRegion(stop.region());
            newStop.setMunicipality(stop.municipality());
            newStop.setMunicipalityFare1(stop.municipalityFare1());
            newStop.setMunicipalityFare2(stop.municipalityFare2());
            ioStops.add(newStop);
        });
        try
        {
            stopRepository.saveAllAndFlush(ioStops);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }


    /**
     *
     * Add Routes fields to database
     * (Inserts records in "routes" table)
     *
     */
    @LogExecutionTime
    public void addRoutesToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        GtfsTableContainer routesContainer = feedContainer.getTableForFilename("routes.txt").orElseThrow(() -> new Exception("routes.txt not found"));
        List<GtfsRoute> entities = routesContainer.getEntities();
        List<Route> ioRoutes = new ArrayList<>();
        log.atInfo().log(String.format("Persisting Routes : %d entries", entities.size()));
        entities.forEach(route -> {
            Route newRoute = new Route();
            newRoute.setFeedId(feedId);
            newRoute.setCsvRowNumber(route.csvRowNumber());
            newRoute.setLineId(route.lineId());
            newRoute.setLineShortName(route.LineShortName());
            newRoute.setRouteLongName(route.routeLongName());
            newRoute.setRouteId(route.routeId());
            newRoute.setAgencyId(route.agencyId());
            newRoute.setRouteOrigin(route.routeOrigin());
            newRoute.setRouteDestination(route.routeDestination());
            newRoute.setRouteShortName(route.routeShortName());
            newRoute.setRouteLongName(route.routeLongName());
            newRoute.setRouteDesc(route.routeDesc());
            newRoute.setRouteRemarks(route.routeRemarks());
            newRoute.setRouteType(route.routeType());
            newRoute.setContract(route.contract());
            newRoute.setPathType(route.pathType());
            newRoute.setCircular(route.circular());
            newRoute.setSchool(route.school());
            newRoute.setContinuousPickup(route.continuousPickup());
            newRoute.setContinuousDropOff(route.continuousDropOff());
            ioRoutes.add(newRoute);
        });
        try
        {
            routeRepository.saveAllAndFlush(ioRoutes);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }



    /**
     *
     * Add Trip fields to database
     * (Inserts records in "stops" table)
     *
     */
    @LogExecutionTime
    public void addTripsToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        GtfsTableContainer tripsContainer = feedContainer.getTableForFilename("trips.txt").orElseThrow(() -> new Exception("trips.txt not found"));
        List<GtfsTrip> entities = tripsContainer.getEntities();
        List<Trip> ioTrips = new ArrayList<>();
        log.atInfo().log(String.format("Persisting Trips : %d entries", entities.size()));
        entities.forEach(trips -> {
            Trip newTrips = new Trip();
            newTrips.setFeedId(feedId);
            newTrips.setCsvRowNumber(trips.csvRowNumber());
            newTrips.setRouteId(trips.routeId());
            newTrips.setServiceId(trips.serviceId());
            newTrips.setTripId(trips.tripId());
            // newTrips.setTripFirt(trips.tripFirst());
            // newTrips.setTripLast(trips.tripLast());
            newTrips.setTripHeadsign(trips.tripHeadsign());
            newTrips.setDirectionId(trips.directionId());
            newTrips.setShapeId(trips.shapeId());
            newTrips.setWheelchairAccessible(trips.wheelchairAccessible());
            newTrips.setBikesAllowed(trips.bikesAllowed());
            ioTrips.add(newTrips);
        });
        try
        {
            tripRepository.saveAllAndFlush(ioTrips);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }


    /**
     *
     * Add StopTimes fields to database
     * (Inserts records in "stop_times" table)
     *
     */
    @LogExecutionTime
    public void addStopTimesToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        GtfsTableContainer stopTimeContainer = feedContainer.getTableForFilename("stop_times.txt").orElseThrow(() -> new Exception("stop_times.txt not found"));
        List<GtfsStopTime> entities = stopTimeContainer.getEntities();
        List<StopTime> ioStopTime = new ArrayList<>();
        log.atInfo().log(String.format("Persisting StopTimes : %d entries", entities.size()));
        entities.forEach(stopTime -> {
            StopTime newStopTime = new StopTime();
            newStopTime.setFeedId(feedId);
            newStopTime.setCsvRowNumber(stopTime.csvRowNumber());
            newStopTime.setTripId(stopTime.tripId());
            newStopTime.setArrivalTime(stopTime.arrivalTime().toHHMMSS());
            newStopTime.setDepartureTime(stopTime.departureTime().toHHMMSS());
            newStopTime.setStopId(stopTime.stopId());
            newStopTime.setStopSequence(stopTime.stopSequence());
            newStopTime.setStopHeadsign(stopTime.stopHeadsign());
            newStopTime.setContinuousPickup(stopTime.continuousPickup());
            newStopTime.setContinuousDropOff(stopTime.continuousDropOff());
            newStopTime.setShapeDistTraveled(stopTime.shapeDistTraveled());
            newStopTime.setTimepoint(stopTime.timepoint());
            ioStopTime.add(newStopTime);
        });
        try
        {
            stopTimeRepository.saveAllAndFlush(ioStopTime);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }


    /**
     *
     * Add Calendar fields to database
     * (Inserts records in "calendar" table)
     *
     */
    @LogExecutionTime
    public void addCalendarToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        GtfsTableContainer calendarContainer = feedContainer.getTableForFilename("calendar.txt").orElseThrow(() -> new Exception("calendar.txt not found"));
        List<GtfsCalendar> entities = calendarContainer.getEntities();
        List<Calendar> ioCalendar = new ArrayList<>();
        log.atInfo().log(String.format("Persisting Calendar : %d entries", entities.size()));
        entities.forEach(calendar -> {
            Calendar newCalendar = new Calendar();
            newCalendar.setFeedId(feedId);
            newCalendar.setCsvRowNumber(calendar.csvRowNumber());
            newCalendar.setServiceId(calendar.serviceId());
            newCalendar.setCalendarName(calendar.calendarName());
            newCalendar.setPeriod(calendar.period());
            newCalendar.setMonday(calendar.monday());
            newCalendar.setTuesday(calendar.tuesday());
            newCalendar.setWednesday(calendar.wednesday());
            newCalendar.setThursday(calendar.thursday());
            newCalendar.setFriday(calendar.friday());
            newCalendar.setSaturday(calendar.saturday());
            newCalendar.setSunday(calendar.sunday());
//          newCalendar.setStartDate(calendar.startDate());
//          newCalendar.setEndDate(calendar.endDate());
            ioCalendar.add(newCalendar);
        });
        try
        {
            calendarRepository.saveAllAndFlush(ioCalendar);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }


    /**
     *
     * Add CalendarDates fields to database
     * (Inserts records in "calendar_dates" table)
     *
     */
    @LogExecutionTime
    public void addCalendarDatesToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        GtfsTableContainer calendarDatesContainer = feedContainer.getTableForFilename("calendar_dates.txt").orElseThrow(() -> new Exception("calendar_date.txt not found"));
        List<GtfsCalendarDate> entities = calendarDatesContainer.getEntities();
        List<CalendarDate> ioCalendarDate = new ArrayList<>();
        log.atInfo().log(String.format("Persisting CalendarDates : %d entries", entities.size()));
        entities.forEach(calendarDate -> {
            CalendarDate newCalendarDate = new CalendarDate();
            newCalendarDate.setFeedId(feedId);
            newCalendarDate.setCsvRowNumber(calendarDate.csvRowNumber());
            newCalendarDate.setServiceId(calendarDate.serviceId());
            newCalendarDate.setCalendarName(calendarDate.calendarName());
            newCalendarDate.setHoliday(calendarDate.holiday());
            newCalendarDate.setPeriod(calendarDate.period());
            //newCalendarDate.setDate(calendarDate.date());
            newCalendarDate.setExceptionType(calendarDate.exceptionType());
            ioCalendarDate.add(newCalendarDate);
        });
        try
        {
            calendarDateRepository.saveAllAndFlush(ioCalendarDate);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }



    /**
     *
     * Add Shapes fields to database
     * (Inserts records in "shapes" table)
     *
     */
    @LogExecutionTime
    public void addShapesToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        GtfsTableContainer shapeContainer = feedContainer.getTableForFilename("shapes.txt").orElseThrow(() -> new Exception("shape.txt not found"));
        List<GtfsShape> entities = shapeContainer.getEntities();
        List<Shape> ioShape = new ArrayList<>();
        log.atInfo().log(String.format("Persisting Shapes : %d entries", entities.size()));
        entities.forEach(shape -> {
            Shape newShape = new Shape();
            newShape.setFeedId(feedId);
            newShape.setCsvRowNumber(shape.csvRowNumber());
            newShape.setShapeId(shape.shapeId());
            newShape.setShapePtLat(shape.shapePtLat());
            newShape.setShapePtLon(shape.shapePtLon());
            newShape.setShapePtSequence(shape.shapePtSequence());
            newShape.setShapeDistTraveled(shape.shapeDistTraveled());
            ioShape.add(newShape);
        });
        try
        {
            shapeRepository.saveAllAndFlush(ioShape);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }


    /**
     *
     * Add FeedInfo fields to database
     * (Inserts records in "feed_info" table)
     *
     */
    @LogExecutionTime
    public void addFeedInfoToDatabase(GtfsFeedContainer feedContainer, String feedId) throws Exception
    {
        GtfsTableContainer feedInfoContainer = feedContainer.getTableForFilename("feed_info.txt").orElseThrow(() -> new Exception("feed_info.txt not found"));
        List<GtfsFeedInfo> entities = feedInfoContainer.getEntities();
        List<FeedInfo> ioFeedInfo = new ArrayList<>();
        log.atInfo().log(String.format("Persisting FeedInfo : %d entries", entities.size()));
        entities.forEach(feedInfo -> {
            FeedInfo newFeedInfo = new FeedInfo();
            newFeedInfo.setFeedId(feedId);
            newFeedInfo.setCsvRowNumber(feedInfo.csvRowNumber());
            newFeedInfo.setFeedPublisherName(feedInfo.feedPublisherName());
            newFeedInfo.setFeedPublisherUrl(feedInfo.feedPublisherUrl());
            newFeedInfo.setFeedLang(feedInfo.feedLang());
            newFeedInfo.setFeedStartDate(feedInfo.feedStartDate().toYYYYMMDD());
            newFeedInfo.setFeedEndDate(feedInfo.feedEndDate().toYYYYMMDD());
            newFeedInfo.setFeedVersion(feedInfo.feedVersion());
            newFeedInfo.setFeedDesc(feedInfo.feedDesc());
            newFeedInfo.setFeedRemarks(feedInfo.feedRemarks());
            ioFeedInfo.add(newFeedInfo);
        });
        try
        {
            feedInfoRepository.saveAllAndFlush(ioFeedInfo);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
    }
}
