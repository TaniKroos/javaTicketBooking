package ticket.booking.services;

import java.io.File;
import java.io.IOException;
// import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;

public class UserBookingService {
    private User user;
    private List<User> userList;
    private static final String USERS_PATH = "app/src/main/java/ticket/booking/localDb/users.json";
    private ObjectMapper  objectMapper = new ObjectMapper();
    public UserBookingService(User user1)throws IOException{
        this.user = user1;
        loadUsers();
    }
    public UserBookingService() throws IOException{
         
        loadUsers();
    }

    public List<User> loadUsers()throws IOException{
        File users = new File(USERS_PATH);
        return  objectMapper.readValue(users, new TypeReference<List<User>> () {});

    }

    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }
    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch(IOException ex){
            return Boolean.FALSE;
        }
    }
    private void saveUserListToFile() throws IOException{
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile, userList);
    }
    public void fetchBookings(){
        user.printTickets();
    }

    public Boolean cancelBooking(String ticketId) throws IOException{
        if(!user.cancelTicket(ticketId)){
            return Boolean.FALSE;
        }
        user.cancelTicket(ticketId);
        saveUserListToFile();
        return Boolean.TRUE;
    }

    public List<Train> getTrains(String source, String destination){
        try{
            TrainService trainService = new TrainService();
            return trainService.getTrains(source, destination);
        }
    }
}
