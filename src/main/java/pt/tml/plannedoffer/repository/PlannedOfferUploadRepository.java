package pt.tml.plannedoffer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pt.tml.plannedoffer.models.PlannedOfferUpload;

import java.util.List;

public interface PlannedOfferUploadRepository extends MongoRepository<PlannedOfferUpload, String>
{

    @Query(value = "{}", fields = "{file:0, validationReport:0, tableResumeList:0, errorsReport:0}")
    List<PlannedOfferUpload> getShortFormList();

}