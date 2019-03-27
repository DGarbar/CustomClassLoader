import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class Starter {

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(9);
    for (int i = 0; i < 10; i++) {
      Runnable runnable = () -> {
        try {
          System.out.println(Thread.currentThread().getName());
          Thread.sleep(ThreadLocalRandom.current().nextInt(500,4000));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      };
      executorService.submit(runnable);
    }
    executorService.shutdown();
  }

}

