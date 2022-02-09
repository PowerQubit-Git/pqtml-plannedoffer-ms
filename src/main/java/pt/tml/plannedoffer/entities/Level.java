package pt.tml.plannedoffer.entities;

import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;

import javax.persistence.*;

@Entity
@Data
@Table(name = "level")
@CsvFileName("level.txt")
@CsvBindByNameOrder({"LevelId", "LevelIndex", "LevelName",})
@IdClass(CsvRowFeedIdCompositeKey.class)
public class Level implements FeedIdEntity
{

    @Column(name = "LevelId")
    private String levelId;

    @Column(name = "LevelIndex")
    private double levelIndex;

    @Column(name = "LevelName")
    private String levelName;

    @Id
    @CsvIgnore
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;

}
