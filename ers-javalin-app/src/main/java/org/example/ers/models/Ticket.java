package org.example.ers.models;

import java.time.LocalDateTime;

public class Ticket {
    String reimbursementId;
    double amount;
    LocalDateTime submitted;
    LocalDateTime resolved;
    String description;
    String paymentId;
    String authorId;
    String resolver;
    String statusId;
    String typeId;
}
