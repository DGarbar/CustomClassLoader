import Exceptions.WorkQueueIsFullException;
import org.junit.Test;

public class MyBlockingQueueTest {

  @Test(expected = WorkQueueIsFullException.class)
  public void queueOverflowExceptionTest() throws WorkQueueIsFullException {
    MyBlockingQueue<Integer> integerMyBlockingQueue = new MyBlockingQueue<Integer>(2);
    integerMyBlockingQueue.put(2);
    integerMyBlockingQueue.put(2);
    integerMyBlockingQueue.put(2);
  }

  
}