package pt.tml.plannedoffer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.FrequencyPeriod;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

public interface FrequencyPeriodRepository extends JpaRepository<FrequencyPeriod, CsvRowFeedIdCompositeKey>
{

}
