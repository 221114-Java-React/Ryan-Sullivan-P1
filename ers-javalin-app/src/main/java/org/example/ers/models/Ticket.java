package org.example.ers.models;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Ticket {
    String ticketId;
    double amount;
    Timestamp submitted;
    Timestamp resolved;
    String description;
    String paymentId;
    String authorId;
    String resolver;
    TicketStatus status;
    String typeId;
    SerialBlob receipt;

    public Ticket(String ticketId, TicketStatus status) {
        this.ticketId = ticketId;
        this.status = status;
    }

    public Ticket(String reimbursementId, double amount, Timestamp submitted, String description, String authorId) {
        this.ticketId = reimbursementId;
        this.amount = amount;
        this.submitted = submitted;
        this.description = description;
        this.authorId = authorId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Timestamp submitted) {
        this.submitted = submitted;
    }

    public Timestamp getResolved() {
        return resolved;
    }

    public void setResolved(Timestamp resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getResolver() {
        return resolver;
    }

    public void setResolver(String resolver) {
        this.resolver = resolver;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public SerialBlob getReceipt() {
        return receipt;
    }

    public void setReceipt(SerialBlob receipt) {
        this.receipt = receipt;
    }
}
