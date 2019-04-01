package SynchronizeOnMethod;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import Util.SimultaneityExecutor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

class MyMethodSyncBankTest {

  MyBank myBank = new MyBank();

  Callable<Boolean> deadLockReason1 = () -> {
    for (int i = 0; i < 10000; i++) {
      myBank.transfer(5, 4, 2);
    }
    return true;
  };
  Callable<Boolean> deadLockReason2 = () -> {
    for (int i = 0; i < 10000; i++) {
      myBank.transfer(4, 5, 1);
    }
    return true;
  };
  ArrayList<Callable<Boolean>> deadLockCallable;

  Callable<Boolean> raceCondition1;
  Callable<Boolean> raceCondition2;
  ArrayList<Callable<Boolean>> raceConditionCallable;

  @BeforeEach
  void init() {
    deadLockCallable = new ArrayList<>();
    deadLockCallable.add(deadLockReason1);
    deadLockCallable.add(deadLockReason2);

    int to = ThreadLocalRandom.current().nextInt(1, 3);
    int from = ThreadLocalRandom.current().nextInt(4, 6);
    raceCondition1 = () -> {
      int amount = ThreadLocalRandom.current().nextInt(1, 100);
      for (int j = 0; j < 10000; j++) {
        myBank.transfer(from, to, amount);
      }
      return true;
    };
    raceCondition2 = () -> {
      int amount = ThreadLocalRandom.current().nextInt(1, 100);
      for (int j = 0; j < 10000; j++) {
        myBank.transfer(to, from, amount);
      }
      return true;
    };
    raceConditionCallable = new ArrayList<>();
    raceConditionCallable.add(raceCondition1);
    raceConditionCallable.add(raceCondition2);
  }

  @RepeatedTest(5)
  void deadLockTimeout() {
    assertTimeoutPreemptively(ofSeconds(5), () -> {
      List<Future<Boolean>> futures = SimultaneityExecutor
          .invokeAllSimultaneously(deadLockCallable);
      for (Future<Boolean> booleanFuture : futures) {
        booleanFuture.get();
      }
    }, "DEAD LOCK");
  }

  @RepeatedTest(5)
  void consistentResult() {
    int total = myBank.total();
    assertTimeoutPreemptively(ofSeconds(5), () -> {
      List<Future<Boolean>> futures = SimultaneityExecutor
          .invokeAllSimultaneously(raceConditionCallable);
      for (Future<Boolean> booleanFuture : futures) {
        booleanFuture.get();
      }
    }, "DEAD LOCK");
    assertEquals(total, myBank.total(), "Data is not consistent");
  }
}