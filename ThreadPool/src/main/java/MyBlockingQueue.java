import Exceptions.WorkQueueIsFullException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.logging.Logger;

public class MyBlockingQueue<T> {

  private static final Logger LOGGER = Logger.getLogger(MyBlockingQueue.class.getSimpleName());
  private Queue<T> queue;
  private final int size;

  public MyBlockingQueue(int size) {
    this.queue = new LinkedList<>();
    this.size = size;
  }

  public synchronized T take() {
    try {
      while (queue.isEmpty()) {
        wait();
      }
    } catch (InterruptedException e) {
      LOGGER.warning(Thread.currentThread().getName() + " interrupted in take");
    }
    if (queue.size() == size) {
      notifyAll();
    }
    return queue.poll();
  }


  public synchronized void put(T t) throws WorkQueueIsFullException {
    Objects.requireNonNull(t);
    if (queue.size() >= size) {
      throw new WorkQueueIsFullException();
    }

//    try {
//      while (queue.size() >= size) {
//        wait();
//      }
//    } catch (InterruptedException e) {
//    LOGGER.warning(Thread.currentThread().getName() + " interrupted in put");
//    }

//    if (queue.isEmpty()) {
      notifyAll();
//    }
    queue.add(t);
  }
}
