package pt.tml.plannedoffer.entities;

import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import pt.powerqubit.validator.core.table.*;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;
import pt.tml.plannedoffer.gtfs.tables.*;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "frequencies")
@CsvFileName("frequencies.txt")
@CsvBindByNameOrder({"TripId", "StartTime", "EndTime", "HeadwaySecs", "Frequency", "ExactTimes", "Typology", "Propulsion", "PassengerCounting", "VideoSurveillance"})
@IdClass(CsvRowFeedIdCompositeKey.class)
public class Frequency implements FeedIdEntity
{

    @Column(name = "TripId")
    private String tripId;

    @Column(name = "StartTime")
    private LocalTime startTime;

    @Column(name = "EndTime")
    private LocalTime endTime;

    @Column(name = "HeadwaySecs")
    private int headwaySecs;

    @Column(name = "Frequency")
    private int frequency;

    @Column(name = "ExactTimes")
    private GtfsFrequencyExactTimes exactTimes;

    @Column(name = "Typology")
    private GtfsTypology typology;

    @Column(name = "Propulsion")
    private GtfsPropulsion propulsion;

    @Column(name = "PassengerCounting")
    private GtfsPassengerCounting passengerCounting;

    @Column(name = "VideoSurveillance")
    private GtfsVideoSurveillance videoSurveillance;

    @Id
    @CsvIgnore
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;

}
