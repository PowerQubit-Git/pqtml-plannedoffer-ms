package pt.tml.plannedoffer.entities;

import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import pt.powerqubit.validator.core.table.GtfsBikesAllowed;
import pt.powerqubit.validator.core.table.GtfsTripDirectionId;
import pt.powerqubit.validator.core.table.GtfsWheelchairBoarding;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "trips")
@CsvFileName("trips.txt")
@CsvBindByNameOrder({"RouteId", "ServiceId", "TripId", "TripFirst", "TripLast", "TripHeadsign", "DirectionId", "" +
        "ShapeId", "WheelchairAccessible", "BikesAllowed"})

@IdClass(CsvRowFeedIdCompositeKey.class)
public class Trip
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

    @Column(name = "DirectionId")
    private GtfsTripDirectionId directionId;

    @Column(name = "ShapeId")
    private String shapeId;

    @CsvIgnore
    @Column(name = "WheelchairAccessible")
    private GtfsWheelchairBoarding wheelchairAccessible;

    @Column(name = "BikesAllowed")
    private GtfsBikesAllowed bikesAllowed;

    @Id
    @CsvIgnore
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;

}
