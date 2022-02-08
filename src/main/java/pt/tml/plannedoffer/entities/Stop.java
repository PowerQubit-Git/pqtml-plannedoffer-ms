package pt.tml.plannedoffer.entities;

import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import pt.powerqubit.validator.core.table.*;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;
import pt.tml.plannedoffer.gtfs.tables.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "stops")
@CsvFileName("stops.txt")
@CsvBindByNameOrder({
        "StopId", "StopIdStepp", "StopCode", "StopName", "StopDesc", "StopRemarks", "StopLat", "StopLon", "ZoneShift", "LocationType", "ParentStation",
        "ParentStation", "WheelchairBoarding", "PlatformCode", "EntranceRestriction", "ExitRestriction", "Slot", "Signalling", "Shelter", "Bench", "NetworkMap",
        "Schedule", "RealTimeInformation", "Tariff", "PreservationState", "Equipment", "Observations", "Region", "Municipality", "MunicipalityFare1", "MunicipalityFare2"
})

@IdClass(CsvRowFeedIdCompositeKey.class)
public class Stop implements FeedIdEntity
{
    @Column(name = "StopId")
    private String stopId;

    @Column(name = "StopIdStepp")
    private String stopIdStepp;

    @Column(name = "StopCode")
    private String stopCode;

    @Column(name = "StopName")
    private String stopName;

    @Column(name = "StopDesc")
    private String stopDesc;

    @Column(name = "StopRemarks")
    private String stopRemarks;

    @Column(name = "StopLat")
    private double stopLat;

    @Column(name = "StopLon")
    private double stopLon;

    @Column(name = "ZoneShift")
    private GtfsZoneShiftEnum zoneShift;

    @Column(name = "LocationType")
    private GtfsLocationTypeEnum locationType;

    @Column(name = "ParentStation")
    private String parentStation;

    @Column(name = "WheelchairBoarding")
    private GtfsWheelchairBoardingEnum wheelchairBoarding;

    @Column(name = "PlatformCode")
    private String platformCode;

    @Column(name = "EntranceRestriction")
    private GtfsEntranceRestrictionEnum entranceRestriction;

    @Column(name = "ExitRestriction")
    private GtfsExitRestrictionEnum exitRestriction;

    @Column(name = "Slot")
    private GtfsSlotEnum slot;

    @Column(name = "Signalling")
    private GtfsSignallingEnum signalling;

    @Column(name = "Shelter")
    private GtfsShelterEnum shelter;

    @Column(name = "Bench")
    private GtfsBenchEnum bench;

    @Column(name = "NetworkMap")
    private GtfsNetworkMapEnum networkMap;

    @Column(name = "Schedule")
    private GtfsScheduleEnum schedule;

    @Column(name = "RealTimeInformation")
    private GtfsRealTimeInformationEnum realTimeInformation;

    @Column(name = "Tariff")
    private GtfsTariffEnum tariff;

    @Column(name = "PreservationState")
    private GtfsPreservationStateEnum preservationState;

    @Column(name = "Equipment")
    private String equipment;

    @Column(name = "Observations")
    private String observations;

    @Column(name = "Region")
    private String region;

    @Column(name = "Municipality")
    private GtfsMunicipalityEnum municipality;

    @Column(name = "MunicipalityFare1")
    private GtfsMunicipalityFare1Enum municipalityFare1;

    @Column(name = "MunicipalityFare2")
    private GtfsMunicipalityFare2Enum municipalityFare2;

    @Id
    @CsvIgnore
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;

}
