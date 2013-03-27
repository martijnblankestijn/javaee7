package nl.ordina.javaee7.mdb.inbound;

import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 */
@MessageDriven(mappedName="jms/RedeliveryCountRequestQueue")
public class RedeliveryCountMDB implements MessageListener{
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
    } catch (JMSException e) {
      throw new RuntimeException(e);
    }
  }
}
