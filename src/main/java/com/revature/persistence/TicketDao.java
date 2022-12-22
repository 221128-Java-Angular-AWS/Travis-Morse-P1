package com.revature.persistence;

import com.revature.pojos.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class TicketDao {

    private Connection connection;

    public TicketDao() {
        this.connection = ConnectionManager.getConnection();
    }

    public void create(Ticket ticket) {
        try {
            String sql = "INSERT INTO tickets (employee, amount, description, status, date_submitted, category) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, ticket.getEmployee());
            pstmt.setFloat(2, ticket.getAmount());
            pstmt.setString(3, ticket.getDescription());
            pstmt.setString(4, ticket.getStatus());
            pstmt.setDate(5, ticket.getDateSubmitted());
//            pstmt.setInt(6, ticket.getReviewedBy());
            pstmt.setString(6, ticket.getCategory());
            pstmt.executeUpdate();


        } catch (SQLException e) {
            // TODO improve exception handling
            throw new RuntimeException(e);
        }
    }

    public Ticket getTicketByID(Integer ticketID) {
        Ticket ticket = new Ticket();

        try {
            String sql = "SELECT * FROM tickets WHERE ticket_id = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, ticketID);

            ResultSet results = pstmt.executeQuery();
            while (results.next()) {
                ticket.setTicketID(results.getInt("ticket_id"));
                ticket.setEmployee(results.getInt("employee"));
                ticket.setAmount(results.getFloat("amount"));
                ticket.setDescription(results.getString("description"));
                ticket.setStatus(results.getString("status"));
                ticket.setDateSubmitted(results.getDate("date_submitted"));
                ticket.setReviewedBy(results.getInt("reviewed_by"));
            }
        } catch (SQLException e) {
            //TODO: improve exception handling
            throw new RuntimeException(e);
        }

        return ticket;
    }

    public Set<Ticket> getPreviousTickets(Integer userID) {
        Set<Ticket> userTicketSet = new HashSet<Ticket>();
        try {
            String sql = "SELECT * FROM tickets WHERE employee = ? ORDER BY date_submitted;";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userID);
            ResultSet results = pstmt.executeQuery();

            while (results.next()) {
                userTicketSet.add(new Ticket(
                        results.getInt("ticket_id"),
                        results.getInt("employee"),
                        results.getFloat("amount"),
                        results.getString("description"),
                        results.getString("status"),
                        results.getDate("date_submitted"),
                        results.getInt("reviewed_by"),
                        results.getString("category")
                ));
            }
        } catch (SQLException e) {
            //TODO: improve exception handling
            throw new RuntimeException(e);
        }
        return userTicketSet;
    }
    public Set<Ticket> getPreviousTicketsByStatus(Integer userID, String status) {
        Set<Ticket> userTicketSet = new HashSet<Ticket>();
        try {
            String sql = "SELECT * FROM tickets WHERE employee = ? AND status = ? ORDER BY date_submitted;";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userID);
            pstmt.setString(2, status);
            ResultSet results = pstmt.executeQuery();

            while (results.next()) {
                userTicketSet.add(new Ticket(
                        results.getInt("ticket_id"),
                        results.getInt("employee"),
                        results.getFloat("amount"),
                        results.getString("description"),
                        results.getString("status"),
                        results.getDate("date_submitted"),
                        results.getInt("reviewed_by"),
                        results.getString("category")
                ));
            }
        } catch (SQLException e) {
            //TODO: improve exception handling
            throw new RuntimeException(e);
        }
        return userTicketSet;
    }


    public void update(Ticket ticket) {
        try {
            String sql = "UPDATE tickets SET employee = ?, amount = ?, description = ?, status = ?, date_submitted = ?, reviewed_by = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, ticket.getEmployee());
            pstmt.setFloat(2, ticket.getAmount());
            pstmt.setString(3, ticket.getDescription());
            pstmt.setString(4, ticket.getStatus());
            pstmt.setDate(5, ticket.getDateSubmitted());
            pstmt.setInt(6, ticket.getReviewedBy());
            pstmt.executeUpdate();


        } catch (SQLException e) {
            // TODO improve exception handling
            throw new RuntimeException(e);
        }
    }

    public void updateTicketStatus(Ticket ticket, String status, Integer reviewedBy) {
        try {
            String sql = "UPDATE tickets SET status = ?, reviewed_by = ? WHERE ticket_id = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setInt(2, reviewedBy);
            pstmt.setInt(3, ticket.getTicketID());
            pstmt.executeUpdate();


        } catch (SQLException e) {
            // TODO improve exception handling
            throw new RuntimeException(e);
        }
    }

    public Ticket getNextTicketInQueue() {
        try {
            //TODO: what if there are no pending tickets?
            String sql = "SELECT * FROM tickets WHERE status = 'pending' ORDER BY date_submitted LIMIT 1;";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet results = pstmt.executeQuery();
            results.next();
            Ticket ticket = new Ticket(
                    results.getInt("ticket_id"),
                    results.getInt("employee"),
                    results.getFloat("amount"),
                    results.getString("description"),
                    results.getString("status"),
                    results.getDate("date_submitted"),
                    results.getInt("reviewed_by"),
                    results.getString("category")
                );

            return ticket;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Integer ticket_id) {
        try {
            String sql = "DELETE FROM tickets WHERE ticket_id = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, ticket_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //TODO: improve exception handling
            throw new RuntimeException(e);
        }
    }
}
