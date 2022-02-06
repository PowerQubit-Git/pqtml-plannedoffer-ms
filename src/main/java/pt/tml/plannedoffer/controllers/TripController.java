package pt.tml.plannedoffer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.tml.plannedoffer.entities.Trip;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.repository.TripRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class TripController
{
    @Autowired
    TripRepository tripRepository;

    @GetMapping("trips/{id}")
    HttpEntity<List<Trip>> get(@PathVariable String id) throws Exception
    {
        try
        {
            List<Trip> list = tripRepository.findByFeedId(id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("trips/{feedId}/{csvRowNumber}")
    HttpEntity<Trip> getOne(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        try
        {
            Trip list = tripRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                    .orElseThrow(() -> new Exception("not found"));
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("trips-by-route/{feedId}/{routeId}")
    HttpEntity<List<Trip>> getByRoute(@PathVariable(value = "feedId") String feedId, @PathVariable(value = "routeId") String routeId) throws Exception
    {
        try
        {
            List<Trip> list = tripRepository.findByFeedIdAndRouteId(feedId, routeId);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("trips")
    public Trip create(@Valid @RequestBody Trip Trip)
    {
        return tripRepository.save(Trip);
    }

    @PutMapping("trips/{id}")
    public ResponseEntity<Trip> update(@PathVariable(value = "id") String id, @Valid @RequestBody Trip details) throws Exception
    {
        try
        {
            var updatedTrip = tripRepository.save(details);
            return ResponseEntity.ok(updatedTrip);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("trips/{feedId}/{csvRowNumber}")
    public Map<String, Boolean> deleteTrip(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        Trip Trip = tripRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                .orElseThrow(() -> new Exception("not found"));

        tripRepository.delete(Trip);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
