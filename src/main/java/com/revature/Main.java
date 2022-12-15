package com.revature;

import com.revature.persistence.UserDao;
import com.revature.service.UserService;
import io.javalin.Javalin;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {

        Javalin webApp = Javalin.create().start();


        webApp.get("/ping", (ctx) -> {
            ctx.result("Pong!");
            ctx.status(200);
        });

        webApp.get("/getUser", ctx -> {
            String id = ctx.queryParam("user_id");
            UserService userService = new UserService(new UserDao());
            ctx.json(userService.getUserByID(Integer.parseInt(id)));
            ctx.status(200);
        });
    }
}
