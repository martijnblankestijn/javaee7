package nl.ordina.javaee7.business.service;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 *
 */
@Stateless public class FireAndForgetSender {
  @Inject JMSContext context;

  @Resource(mappedName = "jms/FireAndForgetQueue")
  Queue queue;

  public void sendMessage(String content) {
    context.createProducer().send(queue, content);
  }
}
