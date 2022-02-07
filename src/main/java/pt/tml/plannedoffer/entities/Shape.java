package pt.tml.plannedoffer.entities;

import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;

import javax.persistence.*;


@Entity
@Table(name = "shapes")
@Data
@IdClass(CsvRowFeedIdCompositeKey.class)
@CsvFileName("shapes.txt")
@CsvBindByNameOrder({"LineId", "shapePtLat", "shapePtLon", "shapePtSequence", "shapeDistTraveled"})
public class Shape implements FeedIdEntity
{

    @Column(name = "LineId")
    private String shapeId;

    @Column(name = "ShapePtLat")
    private double shapePtLat;

    @Column(name = "ShapePtLon")
    private double shapePtLon;

    @Column(name = "ShapePtSequence")
    private int shapePtSequence;

    @Column(name = "ShapeDistTraveled")
    private double shapeDistTraveled;

    @Id
    @CsvIgnore
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;

}
