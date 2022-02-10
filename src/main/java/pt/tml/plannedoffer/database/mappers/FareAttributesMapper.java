package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsAgency;
import pt.powerqubit.validator.core.table.GtfsFareAttribute;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.tml.plannedoffer.entities.Agency;
import pt.tml.plannedoffer.entities.FareAttributes;

import java.util.ArrayList;
import java.util.List;

@Flogger
public class FareAttributesMapper
{

    public static List<FareAttributes> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

        List<GtfsFareAttribute> gtfsEntities = container.getEntities();
        List<FareAttributes> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new FareAttributes();


            out.setFeedId(feedId);

            out.setAgencyId(gtfsEntity.agencyId());
            out.setCsvRowNumber(gtfsEntity.csvRowNumber());
            out.setFareId(gtfsEntity.fareId());
            out.setCurrencyType(gtfsEntity.currencyType());
            out.setPaymentMethod(gtfsEntity.paymentMethod());
            out.setPrice(gtfsEntity.price());
            out.setTransferDuration(gtfsEntity.transferDuration());
            out.setTransfers(gtfsEntity.transfers());

            entities.add(out);

        });

        return  entities;
    }
}
