package nl.ordina.javaee7.async;

import java.util.concurrent.Callable;

/**
 *
 */
public class ReporterTask implements Callable<String> {
  private final String reportName;

  public ReporterTask(final String reportName) {
    this.reportName = reportName;
  }

  @Override public String call() throws Exception {
    System.out.println("ReporterTask.forWait");
    Thread.sleep(10000);
    long einde = System.currentTimeMillis();
    System.out.println("ReporterTask.done at " + einde);
    return "DONE " + reportName + " at " + einde;
  }
}
