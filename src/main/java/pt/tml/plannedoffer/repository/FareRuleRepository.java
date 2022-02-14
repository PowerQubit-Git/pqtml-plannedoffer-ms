package pt.tml.plannedoffer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.FareAttributes;
import pt.tml.plannedoffer.entities.FareRule;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

public interface FareRuleRepository extends JpaRepository<FareRule, CsvRowFeedIdCompositeKey>
{
    List<FareRule> findByFeedIdOrderByCsvRowNumberAsc(String id);

    FareRule findByFareRuleId(String id);
}
