package com.revature.service;

import com.revature.persistence.TicketDao;
import com.revature.pojos.Ticket;

import java.util.Set;

public class TicketService {
    private TicketDao ticketDao;

    public TicketService(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    public Boolean createNewTicket(Ticket ticket) {

        if (ticket.getAmount() == null || ticket.getDescription() == null) {
            return false;
        } else {
            ticketDao.create(ticket);
            return true;
        }
    }

    public Ticket getTicketById(Integer ticketID) {
        return ticketDao.getTicketByID(ticketID);
    }

    public Ticket getNextTicketInQueue() { return ticketDao.getNextTicketInQueue(); }

    public Set<Ticket> getPreviousTickets(Integer userID) { return ticketDao.getPreviousTickets(userID); }

    public Set<Ticket> getPreviousTicketsByStatus(Integer userID, String status) { return ticketDao.getPreviousTicketsByStatus(userID, status); }

//    public Set<Ticket> getUserTicketSet(Integer userID) {
//        return ticketDao.getUserTicketSet(userID);
//    }

    public void updateTicket(Ticket ticket) {
        ticketDao.update(ticket);
    }

    public void updateTicketStatus(Ticket ticket, String status, Integer reviewedBy) { ticketDao.updateTicketStatus(ticket, status, reviewedBy);}

    public void deleteTicket(Integer ticketID) {
        ticketDao.delete(ticketID);
    }

}
