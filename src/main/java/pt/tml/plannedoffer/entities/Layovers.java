package pt.tml.plannedoffer.entities;

import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import pt.powerqubit.validator.core.type.GtfsTime;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;

import javax.persistence.*;

@Entity
@Data
@Table(name = "layovers")
@CsvFileName("layovers.txt")
@CsvBindByNameOrder({"LayoverId", "AgencyId", "ServiceId", "StartTime","EndTime",
        "LocationId","BlockId","StartShiftId","StartOvertime","EndShiftId","EndDuration","EndOvertime"})
@IdClass(CsvRowFeedIdCompositeKey.class)
public class Layovers implements FeedIdEntity
{
    @Column(name = "LayoverId")
    String layoverId;

    @Column(name = "AgencyId")
    String agencyId;

    @Column(name = "ServiceId")
    String serviceId;

    @Column(name = "StartTime")
    String startTime;

    @Column(name = "EndTime")
    String endTime;

    @Column(name = "LocationId")
    String locationId;

    @Column(name = "BlockId")
    String blockId;

    @Column(name = "StartShiftId")
    String startShiftId;

    @Column(name = "StartOvertime")
    int startOvertime;

    @Column(name = "EndShiftId")
    String endShiftId;

    @Column(name = "EndDuration")
    int endDuration;

    @Column(name = "EndOvertime")
    int endOvertime;

    @Id
    @CsvIgnore()
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore()
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;

}