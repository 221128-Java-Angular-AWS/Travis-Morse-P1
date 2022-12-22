package com.revature;

import com.revature.Javalin.JavalinConnection;
import com.revature.persistence.TicketDao;
import com.revature.persistence.UserDao;
import com.revature.pojos.Ticket;
import com.revature.pojos.User;
import com.revature.service.TicketService;
import com.revature.service.UserService;
import io.javalin.Javalin;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args ) throws NoSuchAlgorithmException {

        Javalin javalin = JavalinConnection.getJavalinConnection();

    }
}



        //Moved to CLI package
//        System.out.println("Welcome! Please enter your email address to login, or type 'register' to register a new account.");
//        Scanner input = new Scanner(System.in);
//        String userInput = input.nextLine();
//        if (userInput.toLowerCase().equalsIgnoreCase("register")) {
//            User newUser = new User();
//            UserService userService = new UserService(new UserDao());
//            System.out.print("Okay, let's register a new account. ");
//            Boolean validEmail = false;
//            while (!validEmail) {
//                System.out.println("Please enter the email address you would like to use to log in: ");
//                newUser.setEmail(input.nextLine());
//                if (userService.checkEmailAvailable(newUser.getEmail())) {
//                    //TODO: email validation
//                    validEmail = true;
//                } else {
//                    System.out.println("That email is not available.");
//                }
//            }
//            Boolean validPassword = false;
//            while (!validPassword) {
//                System.out.println("Now please enter a password that you will remember for this account: ");
//                newUser.setPassword(input.nextLine());
//                //TODO: password validation
//                validPassword = true;
//            }
//            System.out.println("Great! Now we just need some information to complete your account.");
//            Boolean validFirstName = false;
//            while (!validFirstName) {
//                System.out.print("Please enter your first name: ");
//                newUser.setFirstName(input.nextLine());
//                //TODO: name verification
//                validFirstName = true;
//            }
//            Boolean validLastName = false;
//            while (!validLastName) {
//                System.out.print("Please enter your last name: ");
//                newUser.setLastName(input.nextLine());
//                //TODO: name verification
//                validLastName = true;
//            }
//            System.out.println("Looks great! Submitting registration...");
//            userService.createNewUser(newUser);
//            //TODO: try/catch this in case of db error
//            System.out.println("Done! You are now registered.");
//        }

