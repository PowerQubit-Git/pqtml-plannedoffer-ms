package pt.tml.plannedoffer.controllers;

import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.tml.plannedoffer.entities.Stop;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.repository.StopRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@Flogger
public class StopsController
{
    @Autowired
    StopRepository stopRepository;

    @GetMapping("stops/{id}")
    HttpEntity<List<Stop>> get(@PathVariable String id) throws Exception
    {
        try
        {
            List<Stop> list = stopRepository.findByFeedIdOrderByCsvRowNumberAsc(id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("stops/{feedId}/{csvRowNumber}")
    HttpEntity<Stop> getOne(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        try
        {
            Stop list = stopRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                    .orElseThrow(() -> new Exception("not found"));
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("stop-by-stopid/{feedId}/{stopId}")
    HttpEntity<List<Stop>> getByStopId(@PathVariable(value = "feedId") String feedId, @PathVariable(value = "stopId") String stopId) throws Exception
    {
        try
        {
            List<Stop> list = stopRepository.findByFeedIdAndStopId(feedId, stopId);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("stops")
    public Stop create(@Valid @RequestBody Stop employee)
    {
        return stopRepository.save(employee);
    }

    @PutMapping("stops")
    public ResponseEntity<Stop> update(@Valid @RequestBody Stop details) throws Exception
    {
        try
        {
            final Stop updatedStop = stopRepository.save(details);
            return ResponseEntity.ok(updatedStop);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log("Erro");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("stops/{feedId}/{csvRowNumber}")
    public Map<String, Boolean> delete(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber)
            throws Exception
    {
        Stop s = stopRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                .orElseThrow(() -> new Exception("not found"));
        stopRepository.delete(s);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
