package pt.tml.plannedoffer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.Frequency;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

public interface FrequencyRepository extends JpaRepository<Frequency, CsvRowFeedIdCompositeKey>
{
    List<Frequency> findByFeedId(String id);

    List<Frequency> findByFeedIdAndTripId(String feedid, String tripId);
}
