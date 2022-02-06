package pt.tml.plannedoffer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.tml.plannedoffer.entities.Calendar;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.repository.CalendarRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class CalendarController
{
    @Autowired
    CalendarRepository calendarRepository;

    @GetMapping("calendar/{id}")
    HttpEntity<List<Calendar>> get(@PathVariable String id) throws Exception
    {
        try
        {
            List<Calendar> list = calendarRepository.findByFeedId(id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("calendar/{feedId}/{csvRowNumber}")
    HttpEntity<Calendar> getOne(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        try
        {
            Calendar list = calendarRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                    .orElseThrow(() -> new Exception("not found"));
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("calendar-by-service/{feedId}/{serviceId}")
    HttpEntity<List<Calendar>> getByAgency(@PathVariable(value = "feedId") String feedId, @PathVariable(value = "serviceId") String serviceId) throws Exception
    {
        try
        {
            List<Calendar> list = calendarRepository.findByFeedIdAndServiceId(feedId, serviceId);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("calendar")
    public Calendar create(@Valid @RequestBody Calendar calendar)
    {
        return calendarRepository.save(calendar);
    }

    @PutMapping("calendar/{id}")
    public ResponseEntity<Calendar> update(@PathVariable(value = "id") String id, @Valid @RequestBody Calendar details) throws Exception
    {
        try
        {
            var updatedCalendar = calendarRepository.save(details);
            return ResponseEntity.ok(updatedCalendar);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("calendar/{feedId}/{csvRowNumber}")
    public Map<String, Boolean> delete(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        Calendar calendar = calendarRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                .orElseThrow(() -> new Exception("not found"));
        calendarRepository.delete(calendar);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
