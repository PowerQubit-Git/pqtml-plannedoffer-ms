package pt.tml.plannedoffer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.tml.plannedoffer.entities.FeedInfo;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;

import java.util.List;

@Document
public interface FeedInfoRepository extends JpaRepository<FeedInfo, CsvRowFeedIdCompositeKey>
{
    List<FeedInfo> findByFeedId(String id);

    FeedInfo findByFeedInfoId(String id);
}
