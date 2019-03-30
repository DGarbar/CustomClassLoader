public class Consumer implements Runnable {

  private BufferInt bufferInt;

  public Consumer(BufferInt bufferInt) {
    this.bufferInt = bufferInt;
  }

  @Override
  public void run() {
    while (true) {
      try {
        Integer i = bufferInt.get();
        System.out
            .printf("Consumer get value %d, from Thread %s\n", i, Thread.currentThread().getName());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }
}
