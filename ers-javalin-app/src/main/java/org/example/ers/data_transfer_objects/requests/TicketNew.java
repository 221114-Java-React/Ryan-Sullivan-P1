package org.example.ers.data_transfer_objects.requests;

public class TicketNew {
    double amount;
    String description;

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

