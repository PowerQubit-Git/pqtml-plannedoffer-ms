package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsAgency;
import pt.powerqubit.validator.core.table.GtfsFeedContainer;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.tml.plannedoffer.entities.Agency;


import java.util.ArrayList;
import java.util.List;

@Flogger
public class AgencyMapper
{

    public static List<Agency> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

        List<GtfsAgency> gtfsEntities = container.getEntities();
        List<Agency> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new Agency();


            out.setFeedId(feedId);

            out.setAgencyId(gtfsEntity.agencyId());
            out.setAgencyName(gtfsEntity.agencyName());
            out.setAgencyUrl(gtfsEntity.agencyUrl());
//          out.setAgencyTimezone(gtfsEntity.agencyTimezone());
            out.setAgencyLang(gtfsEntity.agencyLang());
            out.setCsvRowNumber(gtfsEntity.csvRowNumber());

            entities.add(out);

        });

        return  entities;
    }
}
