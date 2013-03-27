package nl.ordina.javaee7.mdb.inbound;

import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 */
@MessageDriven(mappedName="jms/FireAndForgetQueue")
// WERKT NOG NIET DOOR BUG IN GLASSFISH
//@MessageDriven(activationConfig =
//  @ActivationConfigProperty
//          (propertyName = "destinationLookup",
//           propertyValue = "jms/FireAndForgetQueue"))
public class FireAndForgetMDB implements MessageListener{
  @Override
  public void onMessage(Message msg) {
    TextMessage message = (TextMessage) msg;
    try {

      System.out.println("FireAndForgetMDB: Received fire and forget message with content [" + message.getText() + "]");

    } catch (JMSException e) {
      throw new RuntimeException(e);
    }
  }
}
