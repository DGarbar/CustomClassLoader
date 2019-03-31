package UnlockIfWeCantLockBoth;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import org.junit.jupiter.api.RepeatedTest;

class MyBankTest {


  private MyBank myBank = new MyBank();

  private Runnable deadLockReason1 = () -> {
    for (int i = 0; i < 10000; i++) {
      System.out.printf("Iteration #%d, ThreadName = %s \n", i, Thread.currentThread().getName());
      myBank.transfer(5, 4, 2);
    }
  };
  private Runnable deadLockReason2 = () -> {
    for (int i = 0; i < 10000; i++) {
      System.out.printf("Iteration #%d, ThreadName = %s \n", i, Thread.currentThread().getName());
      myBank.transfer(4, 5, 1);
    }
  };

  @RepeatedTest(5)
  void deadLockTimeout() {
    int numberOfThreads = 2;
    CyclicBarrier cyclicBarrier = new CyclicBarrier(numberOfThreads + 1);
    ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
    Collection<Future<Boolean>> futures = new ArrayList<>(numberOfThreads);
    Function<Runnable, Callable<Boolean>> runnableToCallable = (Runnable runnable) -> () -> {
      try {
        cyclicBarrier.await();
        runnable.run();
      } catch (InterruptedException | BrokenBarrierException e) {
        e.printStackTrace();
      }
      return true;
    };
    futures.add(executorService.submit(runnableToCallable.apply(deadLockReason1)));
    futures.add(executorService.submit(runnableToCallable.apply(deadLockReason2)));
    assertTimeoutPreemptively(ofSeconds(5), () -> {
      cyclicBarrier.await();
      for (Future<Boolean> booleanFuture : futures) {
        booleanFuture.get();
      }
    }, "DEAD LOCK");


  }

  @RepeatedTest(5)
  void consistentResult() {
    int total = myBank.total();
    int numberOfThreads = 5;
    CyclicBarrier cyclicBarrier = new CyclicBarrier(numberOfThreads + 1);
    ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
    Collection<Future<Boolean>> futures = new ArrayList<>(numberOfThreads);
    Function<Runnable, Callable<Boolean>> runnableToCallable = (Runnable runnable) -> () -> {
      try {
        cyclicBarrier.await();
        runnable.run();
      } catch (InterruptedException | BrokenBarrierException e) {
        e.printStackTrace();
      }
      return true;
    };
    for (int i = 0; i < numberOfThreads; i++) {
      Callable<Boolean> callable = runnableToCallable.apply(() -> {
        int to = ThreadLocalRandom.current().nextInt(1, 3);
        int from = ThreadLocalRandom.current().nextInt(4, 6);
        int ammount = ThreadLocalRandom.current().nextInt(1, 100);
        for (int j = 0; j < 10000; j++) {
          myBank.transfer(from, to, ammount);
        }
      });
      futures.add(executorService.submit(callable));
    }
    assertTimeoutPreemptively(ofSeconds(5), () -> {
      cyclicBarrier.await();
      for (Future<Boolean> booleanFuture : futures) {
        booleanFuture.get();
      }
    }, "DEAD LOCK");
    assertEquals(total, myBank.total());
  }

}