package pt.tml.plannedoffer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.Level;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

public interface LevelRepository extends JpaRepository<Level, CsvRowFeedIdCompositeKey>
{
    List<Level> findByFeedIdOrderByCsvRowNumberAsc(String id);

    Level findByLevelId(String id);
}
