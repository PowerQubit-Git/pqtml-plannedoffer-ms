package pt.tml.plannedoffer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.DeadRuns;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

public interface DeadRunRepository extends JpaRepository<DeadRuns, CsvRowFeedIdCompositeKey>
{

}
