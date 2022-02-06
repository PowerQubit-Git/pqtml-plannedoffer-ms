package pt.tml.plannedoffer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pt.tml.plannedoffer.models.IntendedOfferUpload;

import java.util.List;

public interface IntendedOfferUploadRepository extends MongoRepository<IntendedOfferUpload, String>
{

    @Query(value = "{}", fields = "{file:0, validationReport:0, tableResumeList:0, errorsReport:0}")
    List<IntendedOfferUpload> getShortFormList();

}