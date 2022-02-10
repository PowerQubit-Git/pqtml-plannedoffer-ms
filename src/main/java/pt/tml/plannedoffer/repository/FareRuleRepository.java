package pt.tml.plannedoffer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.Agency;
import pt.tml.plannedoffer.entities.FareRule;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

public interface FareRuleRepository extends JpaRepository<FareRule, CsvRowFeedIdCompositeKey>
{

}
