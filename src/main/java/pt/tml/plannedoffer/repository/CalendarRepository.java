package pt.tml.plannedoffer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.Calendar;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, CsvRowFeedIdCompositeKey>
{
    List<Calendar> findByFeedId(String id);

    Calendar findByServiceId(String id);

    List<Calendar> findByFeedIdAndServiceId(String feedId, String serviceId);
}
