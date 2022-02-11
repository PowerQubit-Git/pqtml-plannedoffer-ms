package pt.tml.plannedoffer.entities;

import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import pt.powerqubit.validator.core.annotation.NonNegative;
import pt.powerqubit.validator.core.type.GtfsTime;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;

import javax.persistence.*;

@Entity
@Data
@Table(name = "dead_runs")
@CsvFileName("dead_runs.txt")
@CsvBindByNameOrder({"DeadRunId", "AgencyId", "ServiceId", "StartTime"
        , "EndTime" , "StartLocationId" , "EndLocationId" , "DistTraveled" ,
        "BlockId" ,"ShiftId" , "Overtime" , })
@IdClass(CsvRowFeedIdCompositeKey.class)
public class DeadRuns implements FeedIdEntity
{

    @Column(name = "DeadRunId")
    private String dedRunId;

    @Column(name = "AgencyId")
    private String agencyId;

    @Column(name = "ServiceId")
    private String serviceId;

    @Column(name = "StartTime")
    private String startTime;

    @Column(name = "EndTime")
    private String endTime;

    @Column(name = "StartLocationId")
    private String startLocationId;

    @Column(name = "EndLocationId")
    private String endLocationId;

    @NonNegative
    @Column(name = "DistTraveled")
    private int distTraveled;

    @Column(name = "BlockId")
    private String blockId;

    @Column(name = "ShiftId")
    private String shiftId;

    @NonNegative
    @Column(name = "Overtime")
    private int overtime;

    @Id
    @CsvIgnore()
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore()
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;

}