package pt.tml.plannedoffer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.Stop;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

public interface StopRepository extends JpaRepository<Stop, CsvRowFeedIdCompositeKey>
{
    List<Stop> findByFeedIdOrderByCsvRowNumberAsc(String id);

    List<Stop> findByFeedIdAndStopId(String feedId, String stopId);

    Stop findByStopId(String id);
}
