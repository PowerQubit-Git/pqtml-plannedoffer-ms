package pt.tml.plannedoffer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.tml.plannedoffer.entities.FareAttributes;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.repository.FareAttributesRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("fareAttributes")
public class FareAttributesController
{
    @Autowired
    FareAttributesRepository fareAttributesRepository;

    @GetMapping("{id}")
    HttpEntity<List<FareAttributes>> get(@PathVariable String id) throws Exception
    {
        try
        {
            List<FareAttributes> list = fareAttributesRepository.findByFeedIdOrderByCsvRowNumberAsc(id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{feedId}/{csvRowNumber}")
    HttpEntity<FareAttributes> getOne(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        try
        {
            FareAttributes list = fareAttributesRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                    .orElseThrow(() -> new Exception("not found"));
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public FareAttributes create(@Valid @RequestBody FareAttributes fareAttributes)
    {
        return fareAttributesRepository.save(fareAttributes);
    }

    @PutMapping()
    public ResponseEntity<FareAttributes> update(@Valid @RequestBody FareAttributes details) throws Exception
    {
        try
        {
            var updatedFareAttributes = fareAttributesRepository.save(details);
            return ResponseEntity.ok(updatedFareAttributes);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{feedId}/{csvRowNumber}")
    public Map<String, Boolean> delete(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber)
            throws Exception
    {
        FareAttributes fa = fareAttributesRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                .orElseThrow(() -> new Exception("not found"));

        fareAttributesRepository.delete(fa);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
