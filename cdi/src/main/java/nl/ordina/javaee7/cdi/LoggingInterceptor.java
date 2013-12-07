package nl.ordina.javaee7.cdi;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 */
@Interceptor
@Log
@Priority(Interceptor.Priority.LIBRARY_BEFORE+10)
public class LoggingInterceptor {
  @AroundInvoke
  public Object aroundInvoke(InvocationContext context) throws Exception {
    long start = System.currentTimeMillis();

    Object result = context.proceed();

    System.out.println(context.getMethod() + " took " + (System.currentTimeMillis() - start));

    return result;
  }
}
