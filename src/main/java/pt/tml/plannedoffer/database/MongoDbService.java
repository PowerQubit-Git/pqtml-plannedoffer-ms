package pt.tml.plannedoffer.database;

import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.tml.plannedoffer.aspects.LogExecutionTime;
import pt.tml.plannedoffer.models.IntendedOfferUpload;
import pt.tml.plannedoffer.repository.IntendedOfferUploadRepository;

import java.util.List;

@Flogger
@Component
public class MongoDbService
{

    @Autowired
    IntendedOfferUploadRepository mongoRepository;


    /**
     * Read Intended offer from Mongo DB
     */
    @LogExecutionTime(started = "Reading Intended Offer from MongoDb")
    public IntendedOfferUpload getPlan(String offerId)
    {

        IntendedOfferUpload res = null;
        try
        {
            res = mongoRepository.findById(offerId).orElseThrow(() -> new Exception("not found"));
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log("Unable to read intended offer from Mongo");
        }
        return res;
    }


    /**
     * Save Intended Offer blob to Mongo DB
     */
    @LogExecutionTime(started = "Saving Intended Offer to MongoDb")
    public IntendedOfferUpload savePlan(IntendedOfferUpload intendedOfferUpload)
    {
        return mongoRepository.save(intendedOfferUpload);
    }


    /**
     * Read Offer from Mongo
     */
    public IntendedOfferUpload savePlan(String offerId) throws Exception
    {
        return mongoRepository.findById(offerId).orElseThrow(() -> new Exception(String.format("Requested plan id %s not found.", offerId)));
    }


    /**
     * Get all Offers excluding fields:
     * -file
     * -validationReport
     * -tableResumeList
     * -errorsReport
     *
     * @return List of Offer
     */
    public List<IntendedOfferUpload> getMetaPlan()
    {
        return mongoRepository.getShortFormList();
    }

}
