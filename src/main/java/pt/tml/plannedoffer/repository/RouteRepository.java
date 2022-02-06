package pt.tml.plannedoffer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.Route;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, CsvRowFeedIdCompositeKey>
{
    List<Route> findByFeedId(String id);

    Route findByRouteId(String id);

    List<Route> findByFeedIdAndAgencyId(String feedId, String agencyId);
}
