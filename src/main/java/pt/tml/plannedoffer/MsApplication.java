package pt.tml.plannedoffer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {MongoReactiveAutoConfiguration.class})
@EnableMongoRepositories
@EnableAspectJAutoProxy
@EnableAsync
public class MsApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(MsApplication.class, args);
    }
}


