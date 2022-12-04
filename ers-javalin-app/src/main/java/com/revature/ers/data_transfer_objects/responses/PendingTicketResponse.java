package com.revature.ers.data_transfer_objects.responses;

import java.sql.Timestamp;

public class PendingTicketResponse {
    String ticketId;
    Timestamp submitted;
    double amount;
    String description;
    String author;

    public PendingTicketResponse(String ticketId, Timestamp submitted, double amount, String description, String author) {
        this.ticketId = ticketId;
        this.submitted = submitted;
        this.amount = amount;
        this.description = description;
        this.author = author;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Timestamp getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Timestamp submitted) {
        this.submitted = submitted;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
