package pt.tml.plannedoffer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.CalendarDate;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

public interface CalendarDateRepository extends JpaRepository<CalendarDate, CsvRowFeedIdCompositeKey>
{
    List<CalendarDate> findByFeedId(String id);

    CalendarDate findByServiceId(String id);

    List<CalendarDate> findByFeedIdAndServiceId(String feedId, String serviceId);
}
