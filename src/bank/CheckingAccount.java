package bank;

import java.util.ArrayList;
import java.util.List;

public class CheckingAccount extends BankAccount {

    private double overdraftLimit;
    private double withdrawalFee;
    private List<Transaction> transactionHistory;

    public CheckingAccount(String owner,
                           String accNo,
                           double balance,
                           double overdraftLimit,
                           double withdrawalFee) {

        super(owner, accNo, balance);
        this.overdraftLimit = overdraftLimit;
        this.withdrawalFee = withdrawalFee;
        transactionHistory = new ArrayList<>();
    }

    @Override
    public void withdraw(double amount) {
        //amount -> 20
        //balance - 300
        //overdraft -> -200
        //withdrawal -> 10
        double total = amount + withdrawalFee;
        //total -> 30

        if (balance - total < -overdraftLimit) {
            System.out.println("Overdraft limit exceeded");
            return;
        }
        balance -= total;
        Transaction transaction = new Transaction("withdrawal", amount);
        transactionHistory.add(transaction);
    }

    public double getOverdraftUsed() {
        return balance < 0 ? Math.abs(balance) : 0;
    }
}