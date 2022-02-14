package pt.tml.plannedoffer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.tml.plannedoffer.entities.FareRule;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.repository.FareRuleRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("fareRule")
public class FareRulesController
{

    @Autowired
    FareRuleRepository fareRuleRepository;

    @GetMapping("{id}")
    HttpEntity<List<FareRule>> get(@PathVariable String id) throws Exception
    {
        try
        {
            List<FareRule> list = fareRuleRepository.findByFeedIdOrderByCsvRowNumberAsc(id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{feedId}/{csvRowNumber}")
    HttpEntity<FareRule> getOne(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        try
        {
            FareRule list = fareRuleRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                    .orElseThrow(() -> new Exception("not found"));
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public FareRule create(@Valid @RequestBody FareRule fareRule)
    {
        return fareRuleRepository.save(fareRule);
    }

    @PutMapping()
    public ResponseEntity<FareRule> update(@Valid @RequestBody FareRule details) throws Exception
    {
        try
        {
            var updatedFareRule = fareRuleRepository.save(details);
            return ResponseEntity.ok(updatedFareRule);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{feedId}/{csvRowNumber}")
    public Map<String, Boolean> deleteFareRule(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        FareRule fr = fareRuleRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                .orElseThrow(() -> new Exception("not found"));
        fareRuleRepository.delete(fr);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
