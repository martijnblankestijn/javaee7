package nl.ordina.javaee7.business.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jms.*;

/**
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class RequestSender {
  @Inject
  JMSContext context;

  public String send(Queue requestQueue, Queue replyQueue, String content) {
    TextMessage textMessage = context.createTextMessage();
    try {
      textMessage.setText(content);
      textMessage.setJMSReplyTo(replyQueue);
    } catch (JMSException e) {
      return throwJmsException(e);
    }
    return send(requestQueue, textMessage);
  }

  private String send(Destination destination, TextMessage textMessage) {
    context.createProducer().send(destination, textMessage);
    try {
      return textMessage.getJMSMessageID();
    } catch (JMSException e) {
      return throwJmsException(e);
    }
  }

  private String throwJmsException(JMSException e) {
    JMSRuntimeException jmsRuntimeException = new JMSRuntimeException(e.getMessage());
    jmsRuntimeException.initCause(e);
    throw jmsRuntimeException;
  }


}
