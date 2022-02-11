package pt.tml.plannedoffer.entities;

import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;

import javax.persistence.*;

@Entity
@Data
@Table(name = "schedules")
@CsvFileName("schedules.txt")
@CsvBindByNameOrder({"ServiceId","BlockId","ShiftId","VehicleId","DriverId",})
@IdClass(CsvRowFeedIdCompositeKey.class)
public class Schedules implements FeedIdEntity
{
    @Column(name = "ServiceId")
    String serviceId;

    @Column(name = "BlockId")
    String blockId;

    @Column(name = "ShiftId")
    String shift_id;

    @Column(name = "VehicleId")
    String vehicle_id;

    @Column(name = "DriverId")
    String driver_id;

    @Id
    @CsvIgnore()
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore()
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;

}