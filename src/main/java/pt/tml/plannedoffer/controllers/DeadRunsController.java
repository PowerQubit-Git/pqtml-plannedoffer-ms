package pt.tml.plannedoffer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.tml.plannedoffer.entities.DeadRuns;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.repository.DeadRunRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("deadRuns")
public class DeadRunsController
{

    @Autowired
    DeadRunRepository deadRunRepository;

    @GetMapping("{id}")
    HttpEntity<List<DeadRuns>> get(@PathVariable String id) throws Exception
    {
        try
        {
            List<DeadRuns> list = deadRunRepository.findByFeedIdOrderByCsvRowNumberAsc(id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{feedId}/{csvRowNumber}")
    HttpEntity<DeadRuns> getOne(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        try
        {
            DeadRuns list = deadRunRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                    .orElseThrow(() -> new Exception("not found"));
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public DeadRuns create(@Valid @RequestBody DeadRuns deadRuns)
    {
        return deadRunRepository.save(deadRuns);
    }

    @PutMapping()
    public ResponseEntity<DeadRuns> update(@Valid @RequestBody DeadRuns details) throws Exception
    {
        try
        {
            var updatedDeadRuns = deadRunRepository.save(details);
            return ResponseEntity.ok(updatedDeadRuns);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{feedId}/{csvRowNumber}")
    public Map<String, Boolean> deleteDeadRuns(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        DeadRuns dr = deadRunRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                .orElseThrow(() -> new Exception("not found"));
        deadRunRepository.delete(dr);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
