import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchExample {

  public static void main(String[] args) throws InterruptedException {
    int numberOfThreads = 5;
    CountDownLatch ready = new CountDownLatch(numberOfThreads);
    CountDownLatch start = new CountDownLatch(1);
    CountDownLatch done = new CountDownLatch(numberOfThreads);
    ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

    for (int i = 0; i < numberOfThreads; i++) {
      executorService.execute(() -> {
        ready.countDown();
        try {
          start.await();
          System.out.println("started");
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        } finally {
          done.countDown();
        }
      });
    }
    ready.await();
    long s = System.nanoTime();
    start.countDown();
    done.await();
    executorService.shutdown();
    System.out.println(System.nanoTime() - s);
  }

}

