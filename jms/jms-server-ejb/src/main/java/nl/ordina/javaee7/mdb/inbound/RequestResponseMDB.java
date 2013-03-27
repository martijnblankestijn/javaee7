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

  @Override
  public void onMessage(Message msg) {
    TextMessage message = (TextMessage) msg;
    System.out.println("RequestResponseMDB: Got message " + msg);
    try {
      String messageContent = "Reply to: " + message.getText();

      connectionFactory
              .createContext()
              .createProducer()
              .setJMSCorrelationID(msg.getJMSMessageID())
              .send(msg.getJMSReplyTo(), messageContent);

      System.out.println("Replied to queue " + msg.getJMSReplyTo() + " met " + messageContent);
    } catch (JMSException e) {
      throw new RuntimeException(e);
    }
  }
}
