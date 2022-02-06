package pt.tml.plannedoffer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.tml.plannedoffer.entities.CalendarDate;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.repository.CalendarDateRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class CalendarDateController
{
    @Autowired
    CalendarDateRepository calendarDateRepository;

    @GetMapping("calendarDates/{id}")
    HttpEntity<List<CalendarDate>> get(@PathVariable String id) throws Exception
    {
        try
        {
            List<CalendarDate> list = calendarDateRepository.findByFeedId(id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("calendarDates/{feedId}/{csvRowNumber}")
    HttpEntity<CalendarDate> getOne(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        try
        {
            CalendarDate list = calendarDateRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                    .orElseThrow(() -> new Exception("not found"));
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("calendar-dates-by-service/{feedId}/{serviceId}")
    HttpEntity<List<CalendarDate>> getByAgency(@PathVariable(value = "feedId") String feedId, @PathVariable(value = "serviceId") String serviceId) throws Exception
    {
        try
        {
            List<CalendarDate> list = calendarDateRepository.findByFeedIdAndServiceId(feedId, serviceId);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("calendarDates")
    public CalendarDate create(@Valid @RequestBody CalendarDate calendardate)
    {
        return calendarDateRepository.save(calendardate);
    }

    @PutMapping("calendarDates/{id}")
    public ResponseEntity<CalendarDate> update(@PathVariable(value = "id") String id, @Valid @RequestBody CalendarDate details) throws Exception
    {
        try
        {
            final CalendarDate updatedCalendarDate = calendarDateRepository.save(details);
            return ResponseEntity.ok(updatedCalendarDate);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("calendarDates/{feedId}/{csvRowNumber}")
    public Map<String, Boolean> delete(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber)
            throws Exception
    {
        CalendarDate calendarDate = calendarDateRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                .orElseThrow(() -> new Exception("not found"));

        calendarDateRepository.delete(calendarDate);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
