package pt.tml.plannedoffer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.tml.plannedoffer.entities.StopTime;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.repository.StopTimeRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class StopTimesController
{
    @Autowired
    StopTimeRepository stopTimeRepository;

    @GetMapping("stop_times/{feedId}")
    HttpEntity<List<StopTime>> get(@PathVariable String feedId) throws Exception
    {
        try
        {
            List<StopTime> list = stopTimeRepository.findByFeedId(feedId);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("stop_times/{feedId}/{csvRowNumber}")
    HttpEntity<StopTime> getOne(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        try
        {
            StopTime list = stopTimeRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                    .orElseThrow(() -> new Exception("not found"));
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("stop-times-by-tripid/{feedId}/{tripId}")
    HttpEntity<List<StopTime>> getByAgency(@PathVariable(value = "feedId") String feedId, @PathVariable(value = "tripId") String tripId) throws Exception
    {
        try
        {
            List<StopTime> list = stopTimeRepository.findByFeedIdAndTripId(feedId, tripId);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("stop_times")
    public StopTime create(@Valid @RequestBody StopTime stopTimes)
    {
        return stopTimeRepository.save(stopTimes);
    }

    @PutMapping("stop_times/{id}")
    public ResponseEntity<StopTime> update(@PathVariable(value = "id") String id,
                                           @Valid @RequestBody StopTime details) throws Exception
    {
        try
        {
            var updatedStopTime = stopTimeRepository.save(details);
            return ResponseEntity.ok(updatedStopTime);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("stop_times/{feedId}/{csvRowNumber}")
    public Map<String, Boolean> delete(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber)
            throws Exception
    {
        StopTime s = stopTimeRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                .orElseThrow(() -> new Exception("not found"));

        stopTimeRepository.delete(s);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
