package bank;

public class SavingsAccount extends BankAccount {

    private double interestRate;
    private double minimumBalance;

    public SavingsAccount(String owner, String accNo,
                          double balance,
                          double interestRate,
                          double minimumBalance) {
        super(owner, accNo, balance);
        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
    }

    public void applyInterest() {
        balance += balance * interestRate;
    }

    @Override
    public void withdraw(double amount) {
        if (balance - amount < minimumBalance) {
            System.out.println("Withdrawal blocked: minimum balance rule");
            return;
        }
        balance -= amount;
    }
}