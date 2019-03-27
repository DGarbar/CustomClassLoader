import Exceptions.ThreadPoolSizeException;
import java.util.LinkedList;
import java.util.Queue;

public class MyExecutorServiceImpl implements MyExecutorService {

  private Queue<Thread> workers;
  private final int poolSize;
  private MyBlockingQueue<Runnable> workQueue;


  public MyExecutorServiceImpl(int poolSize, int workQueueSize) {
    this.poolSize = poolSize;
    workers = new LinkedList<>();
    workQueue = new MyBlockingQueue<>(workQueueSize);
    updateThreadPool();
  }


  private synchronized Thread getThread(Runnable runnable) throws ThreadPoolSizeException {
    if (workers.size() >= poolSize) {
      if (!cleanThreadPool()) {
        throw new ThreadPoolSizeException("Thread is not empty");
      }
    }
    Thread thread = new Thread(runnable);
    workers.add(thread);
    return thread;
  }

  private synchronized boolean cleanThreadPool() {
    return workers.removeIf(next -> !next.isAlive());
  }

  private synchronized void updateThreadPool() {
    cleanThreadPool();
    try {
      for (int i = 0; i < poolSize - workers.size(); i++) {
        Thread thread = getThread(workQueue.take());
        thread.start();
      }
    } catch (ThreadPoolSizeException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void execute(Runnable command) {
    workQueue.put(command);
  }

  @Override
  public void shutdownNow() {

  }

}
