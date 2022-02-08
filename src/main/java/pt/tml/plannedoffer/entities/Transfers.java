package pt.tml.plannedoffer.entities;

import com.google.gson.annotations.JsonAdapter;
import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;

import javax.persistence.*;

@Entity
@Table(name = "transfers")
@CsvFileName("transfers.txt")
@CsvBindByNameOrder({"FromStopId", "ToStopId", "TransferType", "MinTransferTime",})

@Data
@IdClass(CsvRowFeedIdCompositeKey.class)
@JsonAdapter(StopTimeSerializer.class)
public class Transfers implements FeedIdEntity
{

    @Column(name = "FromStopId")
    private String fromStopId;

    @Column(name = "ToStopId")
    private String toStopId;

    @Column(name = "TransferType")
    private String transferType;

    @Column(name = "MinTransferTime")
    private String minTransferTime;

    @Id
    @CsvIgnore
    @Column(name = "FeedId")
    private String feedId;

    @Id
    @CsvIgnore
    @Column(name = "CsvRowNumber")
    private long csvRowNumber;
}
