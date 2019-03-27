import static org.junit.Assert.*;

import java.util.concurrent.ThreadLocalRandom;
import org.junit.Test;

public class MyExecutorServiceImplTest {
    MyExecutorServiceImpl myExecutorService = new MyExecutorServiceImpl(10,2);

  @Test
  public void execute() {
    for (int i = 0; i < 10; i++) {
      System.out.println(i);
      Runnable runnable = new Runnable() {
        @Override
        public void run() {
          System.out.println(Thread.currentThread().getName() + " " + ThreadLocalRandom.current()
              .nextInt(10, 1000));
        }
      };
      myExecutorService.execute(runnable);
    }
  }
}