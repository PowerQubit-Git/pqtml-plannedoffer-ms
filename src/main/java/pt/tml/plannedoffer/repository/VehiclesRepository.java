package pt.tml.plannedoffer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.Vehicles;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

public interface VehiclesRepository extends JpaRepository<Vehicles, CsvRowFeedIdCompositeKey>
{

}
