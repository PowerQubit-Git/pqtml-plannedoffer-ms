package pt.tml.plannedoffer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.Vehicles;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

public interface VehiclesRepository extends JpaRepository<Vehicles, CsvRowFeedIdCompositeKey>
{
    List<Vehicles> findByFeedIdOrderByCsvRowNumberAsc(String id);
}
