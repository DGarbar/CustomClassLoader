import java.util.concurrent.TimeUnit;

public class ProperlySynchronized {
  // Or we can use volatile without synchronized methods
  // private static volatile boolean stopRequested;
  private static boolean flag = true;


  // Synchronization is not guaranteed to work unless both
  // read and write operations are synchronized.

  //the synchronization on these methods is used solely for its
  //communication effects, not for mutual exclusion.
  private static synchronized void requestStop() {
    flag = false;
  }

  private static synchronized boolean getFlag() {
    return flag;
  }

  public static void main(String[] args) throws InterruptedException {
    new Thread(() -> {
      /* Can be transform to
      if (!stopRequested)
      while (true)
      System.out.println("I am alive");
       */
      while (getFlag()) {
        System.out.println("I am alive");
      }
    }).start();
    TimeUnit.SECONDS.sleep(1);
    requestStop();

    }

}
