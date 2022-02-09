package pt.tml.plannedoffer.entities;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import pt.powerqubit.validator.core.table.*;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;

import javax.persistence.*;

@Entity
@Data
@Table(name = "routes")
@CsvFileName("routes.txt")
@CsvBindByNameOrder({"LineId", "LineShortName", "LineLongName", "RouteId", "AgencyId", "RouteOrigin", "RouteDestination",
        "RouteShortName", "RouteLongName ", "RouteDesc", "RouteRemarks", "RouteType", "Contract", "PathType", "Circular",
        "School", "routeUrl", "routeColor", "routeTextColor", "routeSortOrder", "ContinuousPickup", "ContinuousDropOff"})

@IdClass(CsvRowFeedIdCompositeKey.class)
public class Route implements FeedIdEntity
{

    @Column(name = "LineId")
    private String lineId;

    @Column(name = "LineShortName")
    private String LineShortName;

    @Column(name = "LineLongName")
    private String lineLongName;

    @Column(name = "RouteId")
    private String routeId;

    @Column(name = "AgencyId")
    @CsvBindByName(column = "agency_id")
    private String agencyId;

    @Column(name = "RouteOrigin")
    private String routeOrigin;

    @Column(name = "RouteDestination")
    private String routeDestination;

    @Column(name = "RouteShortName")
    private String routeShortName;

    @Column(name = "RouteLongName")
    @CsvBindByName(column = "route_long_name")
    private String routeLongName;

    @Column(name = "RouteDesc")
    private String routeDesc;

    @Column(name = "RouteRemarks")
    private String routeRemarks;

    @Column(name = "RouteType")
    private GtfsRouteType routeType;

    @Column(name = "Contract")
    private String contract;

    @Column(name = "PathType")
    private GtfsPathType pathType;

    @Column(name = "Circular")
    private GtfsCircular circular;

    @Column(name = "School")
    private GtfsSchool school;

    @Column(name = "RouteUrl")
    private String routeUrl;

//    @Column(name = "RouteColor")
//    private GtfsColor routeColor;
//
//    @Column(name = "RouteTextColor")
//    private GtfsColor routeTextColor;

    @Column(name = "RouteSortOrder")
    private int routeSortOrder;

    @Column(name = "ContinuousPickup")
    private GtfsContinuousPickupDropOff continuousPickup;

    @Column(name = "ContinuousDropOff")
    private GtfsContinuousPickupDropOff continuousDropOff;

    @Id
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;

}
