package pt.tml.plannedoffer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.tml.plannedoffer.entities.Frequency;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.repository.FrequencyRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class FrequenciesController
{
    @Autowired
    FrequencyRepository frequencyRepository;

    @GetMapping("frequencies/{feedId}")
    HttpEntity<List<Frequency>> get(@PathVariable String feedId) throws Exception
    {
        try
        {
            List<Frequency> list = frequencyRepository.findByFeedId(feedId);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("frequencies/{feedId}/{csvRowNumber}")
    HttpEntity<Frequency> getOne(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        try
        {
            Frequency list = frequencyRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                    .orElseThrow(() -> new Exception("not found"));
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("frequencies-by-trip/{feedId}/{tripId}")
    HttpEntity<List<Frequency>> getByTrip(@PathVariable(value = "feedId") String feedId, @PathVariable(value = "tripId") String tripId) throws Exception
    {
        try
        {
            List<Frequency> list = frequencyRepository.findByFeedIdAndTripId(feedId, tripId);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("frequencies")
    public Frequency create(@Valid @RequestBody Frequency frequency)
    {
        return frequencyRepository.save(frequency);
    }

    @PutMapping("frequencies/{id}")
    public ResponseEntity<Frequency> update(@PathVariable(value = "id") String id,
                                            @Valid @RequestBody Frequency details) throws Exception
    {
        try
        {
            var updatedFrequency = frequencyRepository.save(details);
            return ResponseEntity.ok(updatedFrequency);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("frequencies/{feedId}/{csvRowNumber}")
    public Map<String, Boolean> delete(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber)
            throws Exception
    {
        Frequency s = frequencyRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                .orElseThrow(() -> new Exception("not found"));

        frequencyRepository.delete(s);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
