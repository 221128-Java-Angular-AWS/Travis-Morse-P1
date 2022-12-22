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
        javalinConnection.get("/user/auth", JavalinConnection::authenticateUser);
        javalinConnection.get("/user", JavalinConnection::getUser);
        javalinConnection.get("/user/email", JavalinConnection::checkEmailAvailable);
        javalinConnection.post("/user/new", JavalinConnection::createNewUser);
        javalinConnection.post("/ticket/new", JavalinConnection::createNewTicket);
        javalinConnection.put("/ticket/status", JavalinConnection::updateTicketStatus);
        javalinConnection.get("/ticket/next", JavalinConnection::getNextTicketInQueue);
        javalinConnection.get("/ticket/history", JavalinConnection::getPreviousTickets);
        javalinConnection.get("/user/promotion", JavalinConnection::promoteUserByID);

    }

    public static void ping(Context ctx) {
        ctx.result("Pong!");
        ctx.status(200);
    }

    public static void authenticateUser(Context ctx) {
        try {
            User user = ctx.bodyAsClass(User.class);
            String auth = userService.authenticateUser(user);
            if (auth.equals("manager") || auth.equals("employee")) {
                ctx.cookie("auth", auth);
                ctx.status(200);
            } else {
                ctx.result("Please ensure email and password are typed correctly.");
                ctx.status(403);
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            ctx.result("Please ensure email and password are typed correctly.");
            ctx.status(403);
        }
    }
    public static void getUser(Context ctx) {
        try {
            String id = ctx.queryParam("user_id");
            ctx.json(userService.getUserByID(Integer.parseInt(id)));
            ctx.status(200);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            ctx.status(400);
        }
    }

    public static void checkEmailAvailable(Context ctx) {
        try {
            String email = ctx.queryParam("email");
            if (userService.checkEmailAvailable(email)) {
                ctx.result("That email is available!");
            } else {
                ctx.result("That email is not available.");
            }
                ctx.status(200);
//            ctx.json(userService.checkEmailAvailable(email));
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            ctx.status(400);
        }
    }

    public static void createNewUser(Context ctx) {
        try {
            User user = ctx.bodyAsClass(User.class);
            userService.createNewUser(user);
            ctx.status(201);
        }
        catch (Exception e) {
            e.printStackTrace();
            ctx.status(400);
        }
    }

    public static void promoteUserByID(Context ctx) {
        try {
            if (ctx.cookie("auth").equals("manager")) {
                User user = userService.getUserByID(Integer.parseInt(ctx.queryParam("userID")));
                user.setRole("manager");
                userService.updateUser(user);
                ctx.status(200);
            } else {
                ctx.result("Invalid authorization.");
                ctx.status(403);
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            ctx.status(403);
        }
    }

    public static void createNewTicket(Context ctx) {
        try {
            if (ctx.cookie("auth").equals("employee")) {
                Ticket ticket = ctx.bodyAsClass(Ticket.class);
                if (ticketService.createNewTicket(ticket)) {
                    ctx.status(201);
                } else {
                    ctx.status(400);
                }
            } else {
                ctx.result("Invalid authorization.");
                ctx.status(403);
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            ctx.status(403);
        }
    }

    public static void updateTicketStatus(Context ctx) {
        try {
            if (ctx.cookie("auth").equals("manager")) {
                Integer ticketID = Integer.parseInt(ctx.queryParam("ticketID"));
                String status = ctx.queryParam("status");
                Integer reviewedBy = Integer.parseInt(ctx.queryParam("reviewedBy"));
                Ticket ticket = ticketService.getTicketById(ticketID);
                ticketService.updateTicketStatus(ticket, status, reviewedBy);
                ctx.status(200);
            } else {
                ctx.result("Invalid authorization.");
                ctx.status(403);
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            ctx.status(403);
        }
    }

    public static void getNextTicketInQueue(Context ctx) {
        try {
            if (ctx.cookie("auth").equals("manager")) {
                ctx.json(ticketService.getNextTicketInQueue());
                ctx.status(200);
            } else {
                ctx.result("Invalid authorization.");
                ctx.status(403);
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            ctx.status(403);
        }
    }

    public static void getPreviousTickets(Context ctx) {
        try {
            if (ctx.cookie("auth").equals("employee")) {
                Integer userID = Integer.parseInt(ctx.queryParam("userID"));
                ctx.json(ticketService.getPreviousTickets(userID));
                ctx.status(200);
            } else {
                ctx.result("Invalid authorization.");
                ctx.status(403);
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            ctx.status(403);
        }
    }
}
