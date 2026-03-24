package bank;

public class Main {

    public static void main(String[] args) {

        BankAccount[] accounts = new BankAccount[2];

        accounts[0] = new SavingsAccount(
                "Ian", "SA001", 1000,
                0.05, 500);

        accounts[1] = new CheckingAccount(
                "Peris", "CA001", 500,
                300, 10);

        for (BankAccount acc : accounts) {
            acc.deposit(200);
            acc.withdraw(400);
        }

        for (BankAccount acc : accounts) {
            System.out.println(acc);
        }

        BankUtils.transfer(accounts[0], accounts[1], 100);
    }
}