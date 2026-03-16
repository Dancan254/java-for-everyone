package bank;

// Parent class — holds the shared structure and behaviour for all account types.
// Subclasses override withdraw() to enforce their own specific rules.
public class BankAccount {

    private String owner;
    private String accountNumber;
    protected double balance;

    private Transaction[] transactionHistory;
    private int transactionCount;

    public BankAccount(String owner, String accountNumber, double balance) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount");
            return;
        }
        Transaction transaction = new Transaction("deposit", amount);
        balance += amount;
       recordTransaction(transaction);
    }

    public void withdraw(double amount) {
        Transaction transaction = new Transaction("withdrawal", amount);
        if (amount <= balance) {
            balance -= amount;
            recordTransaction(transaction);
        } else {
            System.out.println("Insufficient funds");
        }
    }
    public void recordTransaction(Transaction transaction){
        transactionHistory[transactionCount++] = transaction;
    }

    public double getBalance() {
        return balance;
    }

    public String getOwner() {
        return owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public String toString() {
        return owner + " | Acc No: " + accountNumber +
                " | Balance: " + balance;
    }
}
