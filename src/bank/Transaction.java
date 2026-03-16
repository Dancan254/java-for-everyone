package bank;

// Represents a single recorded transaction on an account.
public class Transaction {

    private String type;    // e.g. "DEPOSIT", "WITHDRAWAL", "INTEREST", "FEE", "TRANSFER IN", "TRANSFER OUT"
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("%-14s  $%8.2f", type, amount);
    }
}
