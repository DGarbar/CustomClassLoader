public class Producer implements Runnable {
  static int i = 0;
  private BufferInt bufferInt;

  public Producer(BufferInt bufferInt) {
    this.bufferInt = bufferInt;
  }

  @Override
  public void run() {
    while (true) {
      try {
        i += 5;
        bufferInt.set(i);
        System.out.println("Producer set " + i + " on a Thread " + Thread.currentThread().getName());
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
