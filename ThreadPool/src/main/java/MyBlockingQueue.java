import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class MyBlockingQueue<T> {

  private Queue<T> queue;
  private final int size;

  public MyBlockingQueue(int size) {
    this.queue = new LinkedList<>();
    this.size = size;
  }

  public synchronized T take() {
    while (queue.size() == 0) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    T poll = queue.poll();
    notifyAll();
    return poll;
  }


  public synchronized void put(T t) {
    Objects.requireNonNull(t);
    while (queue.size() >= size) {
      try {
        wait();
      } catch (InterruptedException e1) {
        e1.printStackTrace();
      }
    }
    queue.add(t);
    notifyAll();
  }
}
