package pt.tml.plannedoffer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.Transfers;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

public interface TransferRepository extends JpaRepository<Transfers, CsvRowFeedIdCompositeKey>
{

}
