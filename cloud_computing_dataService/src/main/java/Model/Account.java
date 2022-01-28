package Model;

public class Account {
    public int id;
    public String accountNumber;
    public double balance;
    public AccountStatus status;


    public Account(int id, String accountNumber, double balance, AccountStatus status) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
    }
}
