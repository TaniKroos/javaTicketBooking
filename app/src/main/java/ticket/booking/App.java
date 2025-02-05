package ticket.booking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import ticket.booking.entities.Ticket;
import ticket.booking.entities.User;
import ticket.booking.services.UserBookingService;
import ticket.booking.util.UserServiceUtil;

public class App {
    public static void main(String[] args) {
        System.out.println("Running Train Booking System");
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService = null;
        try {
            userBookingService = new UserBookingService();
        } catch (IOException ex) {
           
            System.out.println("Error loading users");
        }
        while(option!=7){
            System.out.println("Choose option");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel my Booking");
            System.out.println("7. Exit the App");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Enter your name");
                    String name = scanner.next();
                    System.out.println("Enter your password");
                    String password = scanner.next();
                    User userToSignUp = new User(name,password,UserServiceUtil.hashPassword(password),
                    new ArrayList<Ticket>(), UUID.randomUUID().toString());
                    userBookingService.signUp(userToSignUp);
                    
                    break;
                case 2:
                    System.out.println("Enter your name");
                    String nameToLogin = scanner.next();
                    System.out.println("Enter your password");
                    String passwordToLogin = scanner.next();
                    User userToLogin = new User(nameToLogin,passwordToLogin,UserServiceUtil.hashPassword(passwordToLogin),
                    new ArrayList<Ticket>(), UUID.randomUUID().toString());
                    try{
                        userBookingService = new UserBookingService(userToLogin);
                        System.out.println("Login successful");
                    }catch(IOException e){
                        return;
                    }
                    break;
                case 3:
                    System.out.println("Fetching bookings");
                    userBookingService.fetchBookings();
            
                default:
                    break;
            }
        }
    }
}
