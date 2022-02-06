//package pt.tml.plannedoffer.solace;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
//@Component
//public class SolaceSenderService
//{
//    @Autowired
//    private JmsTemplate jmsTemplate;
//
//    public void SendToSolace(SolaceEvent event) throws JsonProcessingException
//    {
//        var queueName = event.getTopic();
//        ObjectMapper mapper = new ObjectMapper();
//        var r = event.getMessage();
//        String jsonMessage = mapper.writeValueAsString(r);
//        jmsTemplate.convertAndSend(queueName, jsonMessage);
//    }
//
//
//    @PostConstruct
//    private void customizeJmsTemplate()
//    {
//        // Update the jmsTemplate's connection factory to cache the connection
//        CachingConnectionFactory ccf = new CachingConnectionFactory();
//        ccf.setTargetConnectionFactory(jmsTemplate.getConnectionFactory());
//        jmsTemplate.setConnectionFactory(ccf);
//        // By default Spring Integration uses Queues, but if you set this to true you
//        // will send to a PubSub+ topic destination
//        jmsTemplate.setPubSubDomain(false);
//    }
//
//}
//
