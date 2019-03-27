import Exceptions.WorkQueueIsFullException;
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
    initThreadPoll();
  }

  private void initThreadPoll() {
    for (int i = 0; i < poolSize; i++) {
      Thread thread = new Thread(() -> {
        while (true) {
          workQueue.take().run();
        }
      });
      workers.add(thread);
      thread.start();
    }
  }

  @Override
  public void execute(Runnable command) throws WorkQueueIsFullException {
    workQueue.put(command);
  }

  @Override
  public void shutdownNow() {
    workers.forEach(Thread::interrupt);
  }

}
