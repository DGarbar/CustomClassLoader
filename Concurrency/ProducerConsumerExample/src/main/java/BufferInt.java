public class BufferInt {

  private Integer value;
  private volatile boolean isEmpty = true;

  public synchronized Integer get() throws InterruptedException {
    while (isEmpty) {
      wait();
    }
    Integer tmp = value;
    value = null;
    isEmpty = true;
    notifyAll();
    return tmp;
  }

  public synchronized void set(Integer i) throws InterruptedException {
    while (!isEmpty) {
      wait();
    }
    value = i;
    isEmpty = false;
    notifyAll();
  }
}
