import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

public class DiffTests {

  @Test
  public void numberOfThreadCollision() throws InterruptedException, ExecutionException {
    int threads = 10;
    CountDownLatch latch = new CountDownLatch(1);
    AtomicBoolean running = new AtomicBoolean();
    AtomicInteger collisions = new AtomicInteger();
    ExecutorService service = Executors.newFixedThreadPool(threads);
    Collection<Future<Boolean>> futures = new ArrayList<>(threads);
    for (int t = 0; t < threads; t++) {
      futures.add(
          service.submit(
              () -> {
                try {
                  latch.await();
                  if (running.get()) {
                    collisions.incrementAndGet();
                  }
                  running.set(true);

                  //DO smt
                  Thread.sleep(500);

                  running.set(false);
                  return true;
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
                return false;
              }
          ));
    }
    latch.countDown();
    for (Future<Boolean> future : futures) {
      future.get();
    }
    assertEquals(9, collisions.get());
  }
}
