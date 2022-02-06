package pt.tml.plannedoffer.solace;

import lombok.Data;

@Data
public class SolaceEvent
{

    private String topic;
    private String message;
    private boolean saveToMongo;

    public SolaceEvent(String topic, String message, boolean saveToMongo)
    {
        this.topic = topic;
        this.message = message;
        this.saveToMongo = saveToMongo;
    }

}
