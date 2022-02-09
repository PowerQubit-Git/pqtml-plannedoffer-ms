package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsAgency;
import pt.powerqubit.validator.core.table.GtfsFeedContainer;
import pt.powerqubit.validator.core.table.GtfsFeedInfo;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.tml.plannedoffer.entities.Agency;
import pt.tml.plannedoffer.entities.FeedInfo;

import java.util.ArrayList;
import java.util.List;

@Flogger
public class FeedInfoMapper
{

    public static List<FeedInfo> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

        List<GtfsFeedInfo> gtfsEntities = container.getEntities();
        List<FeedInfo> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new FeedInfo();

            out.setFeedId(feedId);

            out.setCsvRowNumber(gtfsEntity.csvRowNumber());
            out.setFeedPublisherName(gtfsEntity.feedPublisherName());
            out.setFeedPublisherUrl(gtfsEntity.feedPublisherUrl());
            out.setFeedLang(gtfsEntity.feedLang());
            out.setFeedStartDate(gtfsEntity.feedStartDate().toYYYYMMDD());
            out.setFeedEndDate(gtfsEntity.feedEndDate().toYYYYMMDD());
            out.setFeedVersion(gtfsEntity.feedVersion());
            out.setFeedDesc(gtfsEntity.feedDesc());
            out.setFeedRemarks(gtfsEntity.feedRemarks());

            entities.add(out);

        });

        return  entities;
    }
}
