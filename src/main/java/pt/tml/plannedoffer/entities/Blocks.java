package pt.tml.plannedoffer.entities;

import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import org.checkerframework.checker.index.qual.NonNegative;
import pt.powerqubit.validator.core.table.*;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;

import javax.persistence.*;

@Entity
@Data
@Table(name = "blocks")
@CsvFileName("blocks.txt")
@CsvBindByNameOrder({"BlockId", "RegistrationDate", "AvailableSeats", "AvailableStanding",
        "Typology", "Classes", "Propulsion","Emission","LoweredFloor"})
@IdClass(CsvRowFeedIdCompositeKey.class)
public class Blocks implements FeedIdEntity
{

    @Column(name = "BlockId")
    private String blockId;

    @Column(name = "RegistrationDate")
    private String registrationDate;

    @NonNegative
    @Column(name = "AvailableSeats")
    private int availableSeats;

    @NonNegative
    @Column(name = "AvailableStanding")
    private int availableStanding;

    @Column(name = "Typology")
    private GtfsTypology typology;

    @Column(name = "Classes")
    private GtfsClasses classes;

    @Column(name = "Propulsion")
    private GtfsPropulsion propulsion;

    @Column(name = "Emission")
    private GtfsEmissions emission;

    @Column(name = "LoweredFloor")
    private GtfsLoweredFloor loweredFloor;

    @Id
    @CsvIgnore
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;

}
