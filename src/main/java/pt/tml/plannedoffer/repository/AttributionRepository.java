package pt.tml.plannedoffer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.Attribution;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

public interface AttributionRepository extends JpaRepository<Attribution, CsvRowFeedIdCompositeKey>
{
    List<Attribution> findByFeedIdOrderByCsvRowNumberAsc(String id);

    Attribution findByAttributionId(String id);
}
