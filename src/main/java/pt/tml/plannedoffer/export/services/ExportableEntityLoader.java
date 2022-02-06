package pt.tml.plannedoffer.export.services;

import org.springframework.stereotype.Component;
import pt.tml.plannedoffer.export.annotations.CsvFileName;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;


@Component
public class ExportableEntityLoader
{
    @PersistenceContext
    EntityManager entityManager;

    /**
     * Find all Entities with @CsvFileName annotation
     */
    public ArrayList<EntityType<?>> findTargetEntities()
    {
        ArrayList<EntityType<?>> entityMap = new ArrayList<EntityType<?>>();

        for (EntityType<?> entity : entityManager.getMetamodel().getEntities())
        {
            var type = entity.getJavaType();
            if (type.isAnnotationPresent(CsvFileName.class))
            {
                entityMap.add(entity);
            }
        }
        return entityMap;
    }
}
