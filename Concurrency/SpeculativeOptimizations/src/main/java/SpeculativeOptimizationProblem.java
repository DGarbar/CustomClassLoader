import java.util.concurrent.TimeUnit;

public class SpeculativeOptimizationProblem {

  private static boolean flag = true;

  public static void main(String[] args) throws InterruptedException {
    new Thread(() -> {
      /* Can be transform to
      if (!stopRequested)
      while (true)
      System.out.println("I am alive");
       */
      while (flag) {
        System.out.println("I am alive");
      }
    }).start();
    TimeUnit.SECONDS.sleep(1);
    flag = false;
  }
}
