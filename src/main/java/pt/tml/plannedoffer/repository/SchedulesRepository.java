package pt.tml.plannedoffer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.Schedules;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

public interface SchedulesRepository extends JpaRepository<Schedules, CsvRowFeedIdCompositeKey>
{
    List<Schedules> findByFeedIdOrderByCsvRowNumberAsc(String id);
}
