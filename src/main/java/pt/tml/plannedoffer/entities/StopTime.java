package pt.tml.plannedoffer.entities;

import com.google.gson.annotations.JsonAdapter;
import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import pt.powerqubit.validator.core.table.GtfsContinuousPickupDropOff;
import pt.powerqubit.validator.core.table.GtfsDropOffType;
import pt.powerqubit.validator.core.table.GtfsPickupType;
import pt.powerqubit.validator.core.table.GtfsStopTimeTimepoint;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;

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
    private GtfsContinuousPickupDropOff continuousPickup;

    @Column(name = "ContinuousDropOff")
    private GtfsContinuousPickupDropOff continuousDropOff;

    @Column(name = "ShapeDistTraveled")
    private double shapeDistTraveled;

    @Column(name = "PickupType")
    private GtfsPickupType pickupType;

    @Column(name = "DropOffType")
    private GtfsDropOffType dropOffType;

    @Column(name = "Timepoint")
    private GtfsStopTimeTimepoint timepoint;

    @Id
    @CsvIgnore
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;
}
