import Util.SimultaneityExecutor;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

class SimultaneityExecutorTest {

  private AtomicInteger collisions;
  private AtomicBoolean running;
  private List<Callable<Boolean>> collect;
  private int numberOfThread = 10;

  @BeforeEach
  void init() {
    collisions = new AtomicInteger();
    running = new AtomicBoolean();
    collect = IntStream.rangeClosed(1, numberOfThread)
        .mapToObj(value -> getBooleanCallable(collisions, running))
        .collect(Collectors.toList());
  }

  private Callable<Boolean> getBooleanCallable(AtomicInteger collisions, AtomicBoolean running) {
    return () -> {
      if (running.get()) {
        collisions.incrementAndGet();
      }
      running.set(true);
      System.out.println(Thread.currentThread().getName());
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      running.set(false);
      return true;
    };
  }

  //9
  @RepeatedTest(10)
  void testCollisionWithCyclicBarrier() throws InterruptedException, ExecutionException {
    List<Future<Boolean>> futures = SimultaneityExecutor.invokeAllSimultaneously(collect);
    for (Future<Boolean> booleanFuture : futures) {
      booleanFuture.get();
    }
    Assertions.assertEquals(numberOfThread - 1, collisions.get());
  }

  //5-9 (first iteration is false almost every time)
  //Test firstly
  @RepeatedTest(15)
  void testCollisionOnExecutorsInvokeAll() throws InterruptedException, ExecutionException {
    List<Future<Boolean>> futures = Executors.newFixedThreadPool(numberOfThread).invokeAll(collect);
    for (Future<Boolean> booleanFuture : futures) {
      booleanFuture.get();
    }
    Assertions.assertEquals(numberOfThread - 1, collisions.get());
  }
}