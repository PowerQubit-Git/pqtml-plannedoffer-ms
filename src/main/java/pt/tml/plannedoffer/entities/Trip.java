package pt.tml.plannedoffer.entities;

import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;
import pt.tml.plannedoffer.gtfs.tables.GtfsBikesAllowedEnum;
import pt.tml.plannedoffer.gtfs.tables.GtfsTripDirectionIdEnum;
import pt.tml.plannedoffer.gtfs.tables.GtfsWheelchairBoardingEnum;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "trips")
@CsvFileName("trips.txt")
@CsvBindByNameOrder({"RouteId", "ServiceId", "TripId", "TripFirst", "TripLast", "TripHeadsign", "DirectionId", "" +
        "ShapeId", "WheelchairAccessible", "BikesAllowed"})

@IdClass(CsvRowFeedIdCompositeKey.class)
public class Trip implements FeedIdEntity
{

    @Column(name = "RouteId")
    private String routeId;

    @Column(name = "ServiceId")
    private String serviceId;

    @CsvIgnore
    @Column(name = "TripId")
    private String tripId;

    @Column(name = "TripFirst")
    private LocalTime tripFirst;

    @Column(name = "TripLast")
    private LocalTime tripLast;

    @Column(name = "TripHeadsign")
    private String tripHeadsign;

    @Column(name = "TripShortName")
    private String tripShortName;

    @Column(name = "DirectionId")
    private GtfsTripDirectionIdEnum directionId;

    @Column(name = "BlockId")
    private String blockId;

    @Column(name = "ShapeId")
    private String shapeId;

    @CsvIgnore
    @Column(name = "WheelchairAccessible")
    private GtfsWheelchairBoardingEnum wheelchairAccessible;

    @Column(name = "BikesAllowed")
    private GtfsBikesAllowedEnum bikesAllowed;

    @Id
    @CsvIgnore
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;

}
