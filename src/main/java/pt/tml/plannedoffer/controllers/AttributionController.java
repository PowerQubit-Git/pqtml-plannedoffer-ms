package pt.tml.plannedoffer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.tml.plannedoffer.entities.Attribution;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.repository.AttributionRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("attribution")
public class AttributionController
{

    @Autowired
    AttributionRepository attributionRepository;

    @GetMapping("{id}")
    HttpEntity<List<Attribution>> get(@PathVariable String id) throws Exception
    {
        try
        {
            List<Attribution> list = attributionRepository.findByFeedIdOrderByCsvRowNumberAsc(id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{feedId}/{csvRowNumber}")
    HttpEntity<Attribution> getOne(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        try
        {
            Attribution list = attributionRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                    .orElseThrow(() -> new Exception("not found"));
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public Attribution create(@Valid @RequestBody Attribution attribution)
    {
        return attributionRepository.save(attribution);
    }

    @PutMapping()
    public ResponseEntity<Attribution> update(@Valid @RequestBody Attribution details) throws Exception
    {
        try
        {
            var updatedAttribution = attributionRepository.save(details);
            return ResponseEntity.ok(updatedAttribution);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{feedId}/{csvRowNumber}")
    public Map<String, Boolean> deleteAttribution(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        Attribution a = attributionRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                .orElseThrow(() -> new Exception("not found"));
        attributionRepository.delete(a);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
