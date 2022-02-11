package pt.tml.plannedoffer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.Schedules;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

public interface SchedulesRepository extends JpaRepository<Schedules, CsvRowFeedIdCompositeKey>
{

}
