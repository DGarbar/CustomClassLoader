public class Starter {

  public static void main(String[] args) throws InterruptedException {
    BufferInt bufferInt = new BufferInt();
    Producer producer = new Producer(bufferInt);
    Producer producer2 = new Producer(bufferInt);
    Consumer consumer = new Consumer(bufferInt);
    Consumer consumer2 = new Consumer(bufferInt);

    Thread threadP1 = new Thread(producer);
    Thread threadP2 = new Thread(producer2);
    Thread threadC1 = new Thread(consumer);
    Thread threadC2 = new Thread(consumer2);

    threadP1.start();
    threadP2.start();
    threadC1.start();
    threadC2.start();

  }
}
