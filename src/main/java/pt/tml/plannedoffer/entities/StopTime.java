package pt.tml.plannedoffer.entities;

import com.google.gson.annotations.JsonAdapter;
import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;
import pt.tml.plannedoffer.gtfs.tables.GtfsContinuousPickupDropOffEnum;
import pt.tml.plannedoffer.gtfs.tables.GtfsDropOffTypeEnum;
import pt.tml.plannedoffer.gtfs.tables.GtfsPickupTypeEnum;
import pt.tml.plannedoffer.gtfs.tables.GtfsStopTimeTimepointEnum;

import javax.persistence.*;

@Entity
@Table(name = "stop_times")
@CsvFileName("stop_times.txt")
@CsvBindByNameOrder({"TripId", "ArrivalTime", "DepartureTime", "StopId", "StopSequence", "StopHeadsign",
        "PickupType", "DropOffType", "ContinuousPickup", "ContinuousDropOff", "ShapeDistTraveled", "Timepoint"})

@Data
@IdClass(CsvRowFeedIdCompositeKey.class)
@JsonAdapter(StopTimeSerializer.class)
public class StopTime implements FeedIdEntity
{

    @Column(name = "TripId")
    private String tripId;

    @Column(name = "ArrivalTime")
    private String arrivalTime;

    @Column(name = "DepartureTime")
    private String departureTime;

    @Column(name = "StopId")
    private String stopId;

    @Column(name = "StopSequence")
    private int stopSequence;

    @Column(name = "StopHeadsign")
    private String stopHeadsign;

    @Column(name = "ContinuousPickup")
    private GtfsContinuousPickupDropOffEnum continuousPickup;

    @Column(name = "ContinuousDropOff")
    private GtfsContinuousPickupDropOffEnum continuousDropOff;

    @Column(name = "ShapeDistTraveled")
    private double shapeDistTraveled;

    @Column(name = "PickupType")
    private GtfsPickupTypeEnum pickupType;

    @Column(name = "DropOffType")
    private GtfsDropOffTypeEnum dropOffType;

    @Column(name = "Timepoint")
    private GtfsStopTimeTimepointEnum timepoint;

    @Id
    @CsvIgnore
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;
}
