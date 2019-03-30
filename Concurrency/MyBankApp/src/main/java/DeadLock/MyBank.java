package DeadLock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyBank {

  private List<Account> accounts = new ArrayList<>();

  public MyBank() {
    accounts.add(new Account(1, 1000));
    accounts.add(new Account(2, 2000));
    accounts.add(new Account(3, 3000));
    accounts.add(new Account(4, 4000));
    accounts.add(new Account(5, 5000));
    accounts.add(new Account(6, 6000));
  }

  public List<Account> getAccounts() {
    return accounts;
  }

  public void transfer(int fromId, int toId, int amount) {
    Optional<Account> from = accounts.stream().filter(account -> account.getId() == fromId)
        .findAny();
    Optional<Account> to = accounts.stream().filter(account -> account.getId() == toId)
        .findAny();
    if (from.isEmpty() && to.isEmpty()) {
      throw new IllegalArgumentException();
    }
    Account fromAccount = from.get();
    Account toAccount = to.get();

    //DeadLock if transfer 1 -> 2 && transfer 2 -> 1
    synchronized (fromAccount) {
      fromAccount.withdraw(amount);
      synchronized (toAccount) {
        toAccount.deposit(amount);
      }
    }
  }


  public int total() {
    return accounts.stream().mapToInt(Account::getAmount).sum();
  }
}
