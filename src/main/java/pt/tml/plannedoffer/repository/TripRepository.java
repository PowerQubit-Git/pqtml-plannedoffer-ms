package pt.tml.plannedoffer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.Trip;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, CsvRowFeedIdCompositeKey>
{
    List<Trip> findByFeedId(String id);

    Trip findByTripId(String id);

    List<Trip> findByFeedIdAndRouteId(String feedId, String routeId);
}
