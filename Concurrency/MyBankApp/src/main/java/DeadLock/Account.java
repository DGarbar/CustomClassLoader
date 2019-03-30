package DeadLock;

public class Account {
  private int id;
  private int amount;

  public Account(int id, int amount) {
    this.id = id;
    this.amount = amount;
  }
  public void deposit(int add){
    amount +=add;
  }

  public void withdraw(int minus){
    amount -=minus;
  }

  public int getId() {
    return id;
  }

  public int getAmount() {
    return amount;
  }


  @Override
  public String toString() {
    return "DeadLock.Account{" +
        "id=" + id +
        ", amount=" + amount +
        '}';
  }
}
