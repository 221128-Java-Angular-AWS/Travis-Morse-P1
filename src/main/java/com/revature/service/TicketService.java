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

    public Set<Ticket> getUserTicketSet(Integer userID) {
        return ticketDao.getUserTicketSet(userID);
    }

    public void updateTicket(Ticket ticket) {
        ticketDao.update(ticket);
    }

    public void deleteTicket(Integer ticketID) {
        ticketDao.delete(ticketID);
    }

}
