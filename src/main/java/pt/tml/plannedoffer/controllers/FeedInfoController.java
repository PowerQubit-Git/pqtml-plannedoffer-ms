package pt.tml.plannedoffer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.tml.plannedoffer.entities.FeedInfo;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.repository.FeedInfoRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("feed-info")
public class FeedInfoController
{
    @Autowired
    FeedInfoRepository feedInfoRepository;

    @GetMapping("{id}")
    HttpEntity<List<FeedInfo>> get(@PathVariable String id) throws Exception
    {
        try
        {
            List<FeedInfo> list = feedInfoRepository.findByFeedId(id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}/{csvRowNumber}")
    HttpEntity<FeedInfo> getOne(@PathVariable(value = "id") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        try
        {
            FeedInfo list = feedInfoRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                    .orElseThrow(() -> new Exception("not found"));
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public FeedInfo create(@Valid @RequestBody FeedInfo FeedInfo)
    {
        return feedInfoRepository.save(FeedInfo);
    }

    @PutMapping("{id}")
    public ResponseEntity<FeedInfo> update(@PathVariable(value = "id") String id, @Valid @RequestBody FeedInfo details) throws Exception
    {
        try
        {
            final FeedInfo updatedFeedInfo = feedInfoRepository.save(details);
            return ResponseEntity.ok(updatedFeedInfo);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}/{csvRowNumber}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        FeedInfo FeedInfo = feedInfoRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId)).orElseThrow(() -> new Exception("not found"));

        feedInfoRepository.delete(FeedInfo);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
