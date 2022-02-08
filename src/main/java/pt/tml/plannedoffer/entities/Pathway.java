package pt.tml.plannedoffer.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pt.powerqubit.validator.core.table.GtfsPathwayIsBidirectional;
import pt.powerqubit.validator.core.table.GtfsPathwayMode;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "pathway")
@CsvFileName("pathway.txt")
@CsvBindByNameOrder({"PathwayId", "FromStopId", "ToStopId", "PathwayMode", "IsBidirectional", "Length", "TraversalTime", "StairCount", "MaxSlope", "MinWidth", "SignpostedAs", "ReversedSignpostedAs",})
@IdClass(CsvRowFeedIdCompositeKey.class)
public class Pathway
{
    @Column(name = "PathwayId")
    private String pathwayId;

    @Column(name = "FromStopId")
    private String fromStopId;

    @Column(name = "ToStopId")
    private String toStopId;

    @Column(name = "PathwayMode")
    private GtfsPathwayMode pathwayMode;

    @Column(name = "IsBidirectional")
    private GtfsPathwayIsBidirectional isBidirectional;

    @Column(name = "Length")
    private double length;

    @Column(name = "TraversalTime")
    private int traversalTime;

    @Column(name = "StairCount")
    private int stairCount;

    @Column(name = "MaxSlope")
    private double maxSlope;

    @Column(name = "MinWidth")
    private double minWidth;

    @Column(name = "SignpostedAs")
    private String signpostedAs;

    @Column(name = "ReversedSignpostedAs")
    private String reversedSignpostedAs;
}