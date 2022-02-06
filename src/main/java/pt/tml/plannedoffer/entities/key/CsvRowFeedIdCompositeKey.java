package pt.tml.plannedoffer.entities.key;

import lombok.Data;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;


@Data
public class CsvRowFeedIdCompositeKey implements Serializable
{

    private static final long serialVersionUID = 1L;
    private String feedId;
    private long csvRowNumber;

    public CsvRowFeedIdCompositeKey()
    {
    }

    public CsvRowFeedIdCompositeKey(String feedId, long csvRowNumber)
    {
        this.feedId = feedId;
        this.csvRowNumber = csvRowNumber;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(feedId, csvRowNumber);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        CsvRowFeedIdCompositeKey entity = (CsvRowFeedIdCompositeKey) o;
        return Objects.equals(this.csvRowNumber, entity.csvRowNumber) && Objects.equals(this.feedId, entity.feedId);
    }
}