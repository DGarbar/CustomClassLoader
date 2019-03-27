import static org.junit.Assert.assertEquals;

import Exceptions.WorkQueueIsFullException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import org.junit.Test;

public class MyExecutorServiceImplTest {

  MyExecutorService myExecutorService = MyExecutors.newFixedThreadPool(3, 10);

  @Test
  public void simpleAddition() throws InterruptedException, WorkQueueIsFullException {
    AtomicInteger atomicInteger = new AtomicInteger();
    for (int i = 0; i < 10; i++) {
      Runnable runnable = () -> {
        atomicInteger.addAndGet(1);
        try {
          System.out.println(atomicInteger.get());
          Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      };
      myExecutorService.execute(runnable);
    }
    Thread.sleep(10000);
    assertEquals(atomicInteger.get(),10);
  }

  @Test(expected = WorkQueueIsFullException.class)
  public void queueOverflowExceptionTest() throws InterruptedException, WorkQueueIsFullException {
    AtomicInteger atomicInteger = new AtomicInteger();
    for (int i = 0; i < 20; i++) {
      Runnable runnable = () -> {
        atomicInteger.addAndGet(1);
        try {
          System.out.println(atomicInteger.get());
          Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      };
      myExecutorService.execute(runnable);
    }
    Thread.sleep(10000);
    assertEquals(atomicInteger.get(),10);
  }

}