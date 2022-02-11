package pt.tml.plannedoffer.entities;

import com.opencsv.bean.CsvIgnore;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.index.qual.NonNegative;
import pt.powerqubit.validator.core.table.*;
import pt.powerqubit.validator.core.type.GtfsDate;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;
import pt.tml.plannedoffer.gtfs.tables.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "vehicles")
@CsvFileName("vehicles.txt")
@CsvBindByNameOrder({"VehicleId", "AgencyId", "StartDate", "EndDate", "LicensePlate",
        "Make", "Model", "RegistrationDate", "AvailableSeats", "AvailableStandings",
        "Typology", "Classes","Propulsion","Emission","NewSeminew","Ecological","Climatization","Wheelchair","Corridor","LoweredFloor",
        "Ramp","FoldingSystem","Kneeling","StaticInformation","OnboardMonitor","FrontDisplay","RearDisplay","SideDisplay","InternalSound",
        "ExternalSound","ConsumptionMeter","Bicycles","PassengerCounting","VideoSurveillance"})
@IdClass(CsvRowFeedIdCompositeKey.class)
public class Vehicles {

    @Column(name = "VehicleId")
    private String vehicleId;

    @Column(name = "AgencyId")
    private String agencyId;

    @Column(name = "StartDate")
    private String startDate;

    @Column(name = "EndDate")
    private String endDate;

    @Column(name = "LicensePlate")
    private String licensePlate;

    @Column(name = "Make")
    private String make;

    @Column(name = "Model")
    private String model;

    @Column(name = "RegistrationDate")
    private String registrationDate;

    @NonNegative
    @Column(name = "AvailableSeats")
    private int availableSeats;

    @NonNegative
    @Column(name = "AvailableStandings")
    private int availableStandings;

    @Column(name = "Typology")
    private GtfsTypology typology;

    @Column(name = "Classes")
    private GtfsClasses classes;

    @Column(name = "Propulsion")
    private GtfsPropulsion propulsion;

    @Column(name = "Emission")
    private GtfsEmissions emission;

    @Column(name = "NewSeminew")
    private GtfsNewSeminew newSeminew;

    @Column(name = "Ecological")
    private GtfsEcological ecological;

    @Column(name = "Climatization")
    private GtfsClimatization climatization;

    @Column(name = "Wheelchair")
    private GtfsWheelchair wheelchair;

    @Column(name = "Corridor")
    private GtfsCorridor corridor;

    @Column(name = "LoweredFloor")
    private GtfsLoweredFloor loweredFloor;

    @Column(name = "Ramp")
    private GtfsRamp ramp;

    @Column(name = "FoldingSystem")
    private GtfsFoldingSystem foldingSystem;

    @Column(name = "Kneeling")
    private GtfsKneeling kneeling;

    @Column(name = "StaticInformation")
    private GtfsStaticInformation staticInformation;

    @Column(name = "OnboardMonitor")
    private  GtfsOnBoardMonitor onboardMonitor;

    @Column(name = "FrontDisplay")
    private GtfsFrontDisplay frontDisplay;

    @Column(name = "RearDisplay")
    private GtfsRearDisplay rearDisplay;

    @Column(name = "SideDisplay")
    private GtfsSideDisplay sideDisplay;

    @Column(name = "InternalSound")
    private GtfsInternalSound internalSound;

    @Column(name = "ExternalSound")
    private GtfsExternalSound externalSound;

    @Column(name = "ConsumptionMeter")
    private GtfsConsumptionMeter consumptionMeter;

    @Column(name = "Bicycles")
    private GtfsBicycles bicycles;

    @Column(name = "PassengerCounting")
    private  GtfsPassengerCounting passengerCounting;

    @Column(name = "VideoSurveillance")
    private GtfsVideoSurveillance videoSurveillance;

    @Id
    @CsvIgnore()
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore()
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;
}