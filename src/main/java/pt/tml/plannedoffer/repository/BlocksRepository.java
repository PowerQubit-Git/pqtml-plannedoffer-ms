package pt.tml.plannedoffer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.Blocks;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

public interface BlocksRepository extends JpaRepository<Blocks, CsvRowFeedIdCompositeKey>
{

}
