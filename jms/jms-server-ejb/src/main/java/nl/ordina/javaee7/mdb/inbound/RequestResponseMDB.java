package nl.ordina.javaee7.mdb.inbound;

import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.jms.*;

/**
 *
 */
@MessageDriven(mappedName="jms/RequestQueue")
public class RequestResponseMDB implements MessageListener{

  @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
  ConnectionFactory connectionFactory;

  @Resource(mappedName = "jms/ReplyQueue") Queue queue;

  @Override
  public void onMessage(Message msg) {
    TextMessage message            = (TextMessage) msg;
    System.out.println("RequestResponseMDB: Got message " + msg);
    try {
      int counter = message.getIntProperty("JMSXDeliveryCount");
      System.out.println("Counter = " + counter);
      if(!message.getJMSRedelivered()) {
        System.out.println("ff lachten en we gooien een unchecked exception");
        throw new RuntimeException("be gone");
      }

      connectionFactory.createContext().createProducer().send(queue, "Reply to: " + message.getText());
    } catch (JMSException e) {
      throw new RuntimeException(e);
    }
  }
}
