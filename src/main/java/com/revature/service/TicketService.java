package com.revature.service;

import com.revature.persistence.TicketDao;
import com.revature.pojos.Ticket;

import java.util.Set;

public class TicketService {
    private TicketDao ticketDao;

    public TicketService(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    public void createNewTicket(Ticket ticket) {
        ticketDao.create(ticket);
    }

    public Ticket getTicketById(Integer ticketID) {
        return ticketDao.getTicketByID(ticketID);
    }

    public Ticket getNextTicketInQueue() { return ticketDao.getNextTicketInQueue(); }

    public Set<Ticket> getPreviousTickets(Integer userID) { return ticketDao.getPreviousTickets(userID); }

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
