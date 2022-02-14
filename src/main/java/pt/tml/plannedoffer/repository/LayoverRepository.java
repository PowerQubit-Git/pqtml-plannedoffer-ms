package pt.tml.plannedoffer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.Layovers;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

public interface LayoverRepository extends JpaRepository<Layovers, CsvRowFeedIdCompositeKey>
{
    List<Layovers> findByFeedIdOrderByCsvRowNumberAsc(String id);
}
