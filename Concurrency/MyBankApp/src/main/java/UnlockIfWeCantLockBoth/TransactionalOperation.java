package UnlockIfWeCantLockBoth;

import java.util.concurrent.locks.Lock;
import java.util.function.BiConsumer;

public class TransactionalOperation {

  public synchronized void provideTransactionOn(Account account1, Account account2,
      BiConsumer<Account, Account> operation) {
    Lock lock2 = account2.getLock();
    Lock lock1 = account1.getLock();
    try {
      while (!Thread.currentThread().isInterrupted()) {
        if (lock1.tryLock()) {
          if (lock2.tryLock()) {
            operation.accept(account1, account2);
            break;
          } else {
            lock1.unlock();
          }
        }
      }

    } finally {
      lock1.unlock();
      lock2.unlock();
      notifyAll();
    }
  }
}
