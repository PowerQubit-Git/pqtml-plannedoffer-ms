package pt.tml.plannedoffer.database;

import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.tml.plannedoffer.aspects.LogExecutionTime;
import pt.tml.plannedoffer.models.PlannedOfferUpload;
import pt.tml.plannedoffer.repository.PlannedOfferUploadRepository;

import java.util.List;

@Flogger
@Component
public class MongoDbService
{

    @Autowired
    PlannedOfferUploadRepository mongoRepository;


    /**
     * Read Planned offer from Mongo DB
     */
    @LogExecutionTime(started = "Reading Planned Offer from MongoDb")
    public PlannedOfferUpload getPlan(String offerId)
    {

        PlannedOfferUpload res = null;
        try
        {
            res = mongoRepository.findById(offerId).orElseThrow(() -> new Exception("not found"));
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log("Unable to read planned offer from Mongo");
        }
        return res;
    }


    /**
     * Save Planned Offer blob to Mongo DB
     */
    @LogExecutionTime(started = "Saving Planned Offer to MongoDb")
    public PlannedOfferUpload savePlan(PlannedOfferUpload plannedOfferUpload)
    {
        return mongoRepository.save(plannedOfferUpload);
    }


    /**
     * Read Offer from Mongo
     */
    public PlannedOfferUpload savePlan(String offerId) throws Exception
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
    public List<PlannedOfferUpload> getMetaPlan()
    {
        return mongoRepository.getShortFormList();
    }

}
