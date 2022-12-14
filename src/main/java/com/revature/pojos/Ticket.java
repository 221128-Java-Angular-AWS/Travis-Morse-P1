package com.revature.pojos;

import java.sql.Date;
import java.util.Objects;

public class Ticket {
    private Integer ticketID;
    private Integer employee;
    private Float amount;
    private String description;
    private String status;
    private Date dateSubmitted;
    private Integer reviewedBy;

    public Ticket(){};

    public Ticket(Integer ticketID, Integer employee, Float amount, String description, String status, Date dateSubmitted, Integer reviewedBy) {
        this.ticketID = ticketID;
        this.employee = employee;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.dateSubmitted = dateSubmitted;
        this.reviewedBy = reviewedBy;
    }

    public Integer getTicketID() {
        return ticketID;
    }

    public void setTicketID(Integer ticketID) {
        this.ticketID = ticketID;
    }

    public Integer getEmployee() {
        return employee;
    }

    public void setEmployee(Integer employee) {
        this.employee = employee;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public Integer getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(Integer reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(ticketID, ticket.ticketID) && Objects.equals(employee, ticket.employee) && Objects.equals(amount, ticket.amount) && Objects.equals(description, ticket.description) && Objects.equals(status, ticket.status) && Objects.equals(dateSubmitted, ticket.dateSubmitted) && Objects.equals(reviewedBy, ticket.reviewedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketID, employee, amount, description, status, dateSubmitted, reviewedBy);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketID=" + ticketID +
                ", employee=" + employee +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", dateSubmitted=" + dateSubmitted +
                ", reviewedBy=" + reviewedBy +
                '}';
    }
}
