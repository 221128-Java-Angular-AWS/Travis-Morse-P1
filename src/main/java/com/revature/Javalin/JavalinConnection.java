package com.revature.Javalin;

import com.revature.persistence.TicketDao;
import com.revature.persistence.UserDao;
import com.revature.pojos.Ticket;
import com.revature.pojos.User;
import com.revature.service.TicketService;
import com.revature.service.UserService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class JavalinConnection {

    private static Javalin javalinConnection;
    private static UserService userService;
    private static TicketService ticketService;

    private JavalinConnection() {};

    public static Javalin getJavalinConnection() {
        if (javalinConnection == null) {
            userService = new UserService(new UserDao());
            ticketService = new TicketService(new TicketDao());
            init();
        }
        return javalinConnection;
    }

    private static void init() {
        javalinConnection = Javalin.create().start();

        javalinConnection.get("/ping", JavalinConnection::ping);
        javalinConnection.get("/getUser", JavalinConnection::getUser);
        javalinConnection.get("/checkEmailAvailable", JavalinConnection::checkEmailAvailable);
        javalinConnection.post("/createNewUser", JavalinConnection::createNewUser);
        javalinConnection.post("/createNewTicket", JavalinConnection::createNewTicket);
        javalinConnection.put("/updateTicketStatus", JavalinConnection::updateTicketStatus);
        javalinConnection.get("/getNextTicketInQueue", JavalinConnection::getNextTicketInQueue);
        javalinConnection.get("/getPreviousTickets", JavalinConnection::getPreviousTickets);
        javalinConnection.get("/promoteUserByID", JavalinConnection::promoteUserByID);

    }

    public static void ping(Context ctx) {
        ctx.result("Pong!");
        ctx.status(200);
    }

    public static void getUser(Context ctx) {
        String id = ctx.queryParam("user_id");
        ctx.json(userService.getUserByID(Integer.parseInt(id)));
        ctx.status(200);
    }

    public static void checkEmailAvailable(Context ctx) {
        String email = ctx.queryParam("email");
        ctx.json(userService.checkEmailAvailable(email));
        ctx.status(200);
    }

    public static void createNewUser(Context ctx) {
        User user = ctx.bodyAsClass(User.class);
        userService.createNewUser(user);
        ctx.status(201);
    }

    public static void promoteUserByID(Context ctx) {
        User user = userService.getUserByID(Integer.parseInt(ctx.queryParam("userID")));
        user.setRole("manager");
        userService.updateUser(user);
    }

    public static void createNewTicket(Context ctx) {
        Ticket ticket = ctx.bodyAsClass(Ticket.class);
        if (ticketService.createNewTicket(ticket)) {
            ctx.status(201);
        } else {
            ctx.status(400);
        }
    }

    public static void updateTicketStatus(Context ctx) {
        Integer ticketID = Integer.parseInt(ctx.queryParam("ticketID"));
        String status = ctx.queryParam("status");
        Integer reviewedBy = Integer.parseInt(ctx.queryParam("reviewedBy"));
        Ticket ticket = ticketService.getTicketById(ticketID);
        ticketService.updateTicketStatus(ticket, status, reviewedBy);
        ctx.status(200);
    }

    public static void getNextTicketInQueue(Context ctx) {
        ctx.json(ticketService.getNextTicketInQueue());
        ctx.status(200);
    }

    public static void getPreviousTickets(Context ctx) {
        Integer userID = Integer.parseInt(ctx.queryParam("userID"));
        ctx.json(ticketService.getPreviousTickets(userID));
        ctx.status(200);
    }
}
