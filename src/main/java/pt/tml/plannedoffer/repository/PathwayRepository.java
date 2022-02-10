package pt.tml.plannedoffer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.Agency;
import pt.tml.plannedoffer.entities.Pathway;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

public interface PathwayRepository extends JpaRepository<Pathway, CsvRowFeedIdCompositeKey>
{
    List<Pathway> findByFeedIdOrderByCsvRowNumberAsc(String id);

    Pathway findByPathwayId(String id);
}
