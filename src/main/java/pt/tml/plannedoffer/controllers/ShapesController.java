package pt.tml.plannedoffer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.tml.plannedoffer.entities.Shape;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.repository.ShapeRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("shapes")
public class ShapesController
{
    @Autowired
    ShapeRepository shapeRepository;

    @GetMapping("{id}")
    HttpEntity<List<Shape>> get(@PathVariable String id) throws Exception
    {
        try
        {
            List<Shape> list = shapeRepository.findByFeedId(id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("by-shapeid/{feedId}/{shapeId}")
    HttpEntity<List<Shape>> getByShapeId(@PathVariable(value = "feedId") String feedId, @PathVariable(value = "shapeId") String shapeId) throws Exception
    {
        try
        {
            List<Shape> list = shapeRepository.findByFeedIdAndShapeId(feedId, shapeId);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public Shape create(@Valid @RequestBody Shape employee)
    {
        return shapeRepository.save(employee);
    }

    @PutMapping("shapes/{id}")
    public ResponseEntity<Shape> update(@PathVariable(value = "id") String id, @Valid @RequestBody Shape details) throws Exception
    {
        try
        {
            final Shape updatedShapes = shapeRepository.save(details);
            return ResponseEntity.ok(updatedShapes);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{feedId}/{csvRowNumber}")
    public Map<String, Boolean> delete(@PathVariable(value = "feedId") long feedId, @PathVariable(value = "csvRowNumber") String csvRowNumber) throws Exception
    {
        Shape Shapes = shapeRepository.findById(new CsvRowFeedIdCompositeKey(csvRowNumber, feedId))
                .orElseThrow(() -> new Exception("not found"));

        shapeRepository.delete(Shapes);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
