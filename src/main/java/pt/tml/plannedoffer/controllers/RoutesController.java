package pt.tml.plannedoffer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.tml.plannedoffer.entities.Route;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.repository.RouteRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class RoutesController
{
    @Autowired
    RouteRepository routeRepository;

    @GetMapping("routes/{id}")
    HttpEntity<List<Route>> get(@PathVariable String id) throws Exception
    {
        try
        {
            List<Route> list = routeRepository.findByFeedId(id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("routes/{feedId}/{csvRowNumber}")
    HttpEntity<Route> getOne(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        try
        {
            Route list = routeRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                    .orElseThrow(() -> new Exception("not found"));
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("routes-by-agency/{feedId}/{agencyId}")
    HttpEntity<List<Route>> getByAgency(@PathVariable(value = "feedId") String feedId, @PathVariable(value = "agencyId") String agencyId) throws Exception
    {
        try
        {
            List<Route> list = routeRepository.findByFeedIdAndAgencyId(feedId, agencyId);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("routes")
    public Route create(@Valid @RequestBody Route route)
    {
        return routeRepository.save(route);
    }

    @PutMapping("routes/{id}")
    public ResponseEntity<Route> update(@PathVariable(value = "id") String id, @Valid @RequestBody Route details) throws Exception
    {
        try
        {
            final Route updatedRoutes = routeRepository.save(details);
            return ResponseEntity.ok(updatedRoutes);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("routes/{feedId}/{csvRowNumber}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        Route route = routeRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                .orElseThrow(() -> new Exception("not found"));

        routeRepository.delete(route);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
