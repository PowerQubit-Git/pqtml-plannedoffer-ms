package pt.tml.plannedoffer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.tml.plannedoffer.entities.FrequencyPeriod;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.repository.FrequencyPeriodRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("frequencyPeriod")
public class FrequencyPeriodController
{

    @Autowired
    FrequencyPeriodRepository frequencyPeriodRepository;

    @GetMapping("{id}")
    HttpEntity<List<FrequencyPeriod>> get(@PathVariable String id) throws Exception
    {
        try
        {
            List<FrequencyPeriod> list = frequencyPeriodRepository.findByFeedIdOrderByCsvRowNumberAsc(id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{feedId}/{csvRowNumber}")
    HttpEntity<FrequencyPeriod> getOne(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        try
        {
            FrequencyPeriod list = frequencyPeriodRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                    .orElseThrow(() -> new Exception("not found"));
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public FrequencyPeriod create(@Valid @RequestBody FrequencyPeriod frequencyPeriod)
    {
        return frequencyPeriodRepository.save(frequencyPeriod);
    }

    @PutMapping()
    public ResponseEntity<FrequencyPeriod> update(@Valid @RequestBody FrequencyPeriod details) throws Exception
    {
        try
        {
            var updatedFrequencyPeriod = frequencyPeriodRepository.save(details);
            return ResponseEntity.ok(updatedFrequencyPeriod);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{feedId}/{csvRowNumber}")
    public Map<String, Boolean> deleteFrequencyPeriod(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        FrequencyPeriod fp = frequencyPeriodRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                .orElseThrow(() -> new Exception("not found"));
        frequencyPeriodRepository.delete(fp);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
