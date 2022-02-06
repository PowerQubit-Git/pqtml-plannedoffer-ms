package pt.tml.plannedoffer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.StopTime;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

public interface StopTimeRepository extends JpaRepository<StopTime, CsvRowFeedIdCompositeKey>
{
    List<StopTime> findByFeedId(String id);

    List<StopTime> findByFeedIdAndTripId(String feedId, String tripId);
}
