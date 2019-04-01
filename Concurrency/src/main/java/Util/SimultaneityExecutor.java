package Util;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SimultaneityExecutor {
  private static final Logger LOG = Logger.getLogger(SimultaneityExecutor.class.getSimpleName());
  private SimultaneityExecutor() {
  }

  public static <T> List<Future<T>> invokeAllSimultaneously(Collection<Callable<T>> callables)
      throws ExecutionException {
    int size = callables.size();
    CyclicBarrier cyclicBarrier = new CyclicBarrier(size + 1);
    ExecutorService executorService = Executors.newFixedThreadPool(size);

    List<Future<T>> futures = callables.stream()
        .map(tCallable -> decorateCallable(tCallable, cyclicBarrier))
        .map(executorService::submit)
        .collect(Collectors.toList());

    try {
//      futures = executorService.invokeAll(decorated);
      cyclicBarrier.await();
      for (Future<T> tFuture : futures) {
        tFuture.get();
      }
    } catch (InterruptedException | BrokenBarrierException e) {
      LOG.warning(Thread.currentThread().getName() + " interrupted");
    }
    return futures;
  }

  private static <T> Callable<T> decorateCallable(Callable<T> callable,
      CyclicBarrier cyclicBarrier) {
    return () -> {
      //TODO How can I improve that
      try {
        cyclicBarrier.await();
      } catch (InterruptedException | BrokenBarrierException e) {
        LOG.warning(Thread.currentThread().getName() + " interrupted");
      }
      return callable.call();
    };
  }
}
