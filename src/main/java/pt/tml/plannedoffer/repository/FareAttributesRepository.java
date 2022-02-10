package pt.tml.plannedoffer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.FareAttributes;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

public interface FareAttributesRepository extends JpaRepository<FareAttributes, CsvRowFeedIdCompositeKey>
{

}