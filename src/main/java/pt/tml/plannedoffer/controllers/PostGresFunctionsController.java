package pt.tml.plannedoffer.controllers;

import lombok.extern.flogger.Flogger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pt.tml.plannedoffer.models.SpRowsByTable;
import pt.tml.plannedoffer.models.SpTripsByLine;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;

@Flogger
@RestController
@CrossOrigin("*")
public class PostGresFunctionsController
{
    @PersistenceContext
    EntityManager entityManager;

    @GetMapping("trips-by-line/{feedId}")
    List<SpTripsByLine> GetSpNumberOfTripsByLine(@PathVariable String feedId) throws Exception
    {
        try
        {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ms_intended_offer.sp_number_of_trips_by_line", SpTripsByLine.class);
            query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
            query.setParameter(1, feedId);
            query.execute();
            return query.getResultList();
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
        return new ArrayList<>();
    }

    @GetMapping("count-rows-by-table/{feedId}")
    public List<SpRowsByTable> countRowsTable(@PathVariable String feedId)
    {
        try
        {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ms_intended_offer.sp_get_rows_by_table", SpRowsByTable.class);
            query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
            query.setParameter(1, feedId);
            query.execute();
            return query.getResultList();
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log();
        }
        return new ArrayList<>();
    }
}