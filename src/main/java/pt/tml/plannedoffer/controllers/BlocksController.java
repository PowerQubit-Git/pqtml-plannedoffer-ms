package pt.tml.plannedoffer.controllers;

import com.mongodb.Block;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.tml.plannedoffer.entities.Blocks;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.repository.BlocksRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("blocks")
public class BlocksController
{

    @Autowired
    BlocksRepository blocksRepository;

    @GetMapping("{id}")
    HttpEntity<List<Blocks>> get(@PathVariable String id) throws Exception
    {
        try
        {
            List<Blocks> list = blocksRepository.findByFeedIdOrderByCsvRowNumberAsc(id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{feedId}/{csvRowNumber}")
    HttpEntity<Blocks> getOne(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        try
        {
            Blocks list = blocksRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                    .orElseThrow(() -> new Exception("not found"));
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public Blocks create(@Valid @RequestBody Blocks blocks)
    {
        return blocksRepository.save(blocks);
    }

    @PutMapping()
    public ResponseEntity<Blocks> update(@Valid @RequestBody Blocks details) throws Exception
    {
        try
        {
            var updatedBlocks = blocksRepository.save(details);
            return ResponseEntity.ok(updatedBlocks);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{feedId}/{csvRowNumber}")
    public Map<String, Boolean> deleteBlocks(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        Blocks b = blocksRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                .orElseThrow(() -> new Exception("not found"));
        blocksRepository.delete(b);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
