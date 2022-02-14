package pt.tml.plannedoffer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.Agency;
import pt.tml.plannedoffer.entities.Blocks;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

public interface BlocksRepository extends JpaRepository<Blocks, CsvRowFeedIdCompositeKey>
{
    List<Blocks> findByFeedIdOrderByCsvRowNumberAsc(String id);

    Blocks findByBlocksId(String id);
}
