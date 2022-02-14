package pt.tml.plannedoffer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.Agency;
import pt.tml.plannedoffer.entities.DeadRuns;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

public interface DeadRunRepository extends JpaRepository<DeadRuns, CsvRowFeedIdCompositeKey>
{
    List<DeadRuns> findByFeedIdOrderByCsvRowNumberAsc(String id);

    DeadRuns findByDeadRunsId(String id);
}
