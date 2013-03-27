package nl.ordina.javaee7.mdb.inbound;

import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;

/**
 *
 */
@MessageDriven(mappedName="jms/RequestQueue")
public class RequestResponseMDB implements MessageListener{

  @Inject
  JMSContext jmsContext;

  @Resource(mappedName = "jms/ReplyQueue") Queue queue;

  @Override
  public void onMessage(Message msg) {
    TextMessage message            = (TextMessage) msg;
    System.out.println("RequestResponseMDB: Got message " + msg);

    try {
      jmsContext.createProducer().send(queue, "Reply to: " + message.getText());
    } catch (JMSException e) {
      throw new RuntimeException(e);
    }
  }
}
