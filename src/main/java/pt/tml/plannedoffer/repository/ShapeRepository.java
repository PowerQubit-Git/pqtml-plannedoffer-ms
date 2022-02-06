package pt.tml.plannedoffer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.tml.plannedoffer.entities.Shape;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

public interface ShapeRepository extends JpaRepository<Shape, CsvRowFeedIdCompositeKey>
{
    List<Shape> findByFeedId(String id);

    Shape findByShapeId(String id);

    List<Shape> findByFeedIdAndShapeId(String feedId, String shapeId);
}
