import static org.junit.Assert.*;

public class MyBlockingQueueTest {
  MyBlockingQueue<Integer> q = new MyBlockingQueue<Integer>(2);
  @org.junit.Test
  public void take() {

      q.put(2);
      q.put(3);
    q.take();
    q.put(4);

  }

  @org.junit.Test
  public void put() {
  }
}