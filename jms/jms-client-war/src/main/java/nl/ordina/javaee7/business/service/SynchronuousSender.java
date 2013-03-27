package nl.ordina.javaee7.business.service;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;

@Stateless
public class SynchronuousSender {
  //    De default staat hieronder
//    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
  @Inject
  JMSContext context;
  @Resource(mappedName = "jms/RequestQueue")
  Queue requestQueue;
  @Resource(mappedName = "jms/ReplyQueue")
  Queue replyQueue;
  @Inject
  RequestSender requestSender;

  public String sendAndReceive(String content) {
    String messageId = requestSender.send(requestQueue, replyQueue, content);

    String selector = String.format("JMSCorrelationID = '%s'", messageId);
    System.out.println("Selector: " + selector);

    JMSConsumer consumer = context.createConsumer(replyQueue, selector);
    String received = consumer.receiveBody(String.class, 2000);
    if (received == null) {
      throw new RuntimeException("GOT NOTHING");
    }
    return received;
  }
}