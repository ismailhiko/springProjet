package com.example.SpringH2.Controller;

import com.example.SpringH2.model.User;
import com.example.SpringH2.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

/**
 * this class uses the UserRepo interface and the User class to create the methods that helps us save a user in the database and get a user from the database
 */
@RestController
public class UserController {

    @Autowired
    private UserRepo userRepo;

    /**
     * a function that saves a user if he's valid and sends a message depending on the error if not
     * @param user : User
     * @return : String
     */
    @PostMapping("/api/users/add")
    public String saveUsers(@RequestBody User user){

        if(isValidUser(user)){
            User users = userRepo.save(user);
            return "User saved";
        }

        if (!isValidUserNull(user)){
            return "Please try again with all required fields (username, birthdate, country)";
        }

        if (!isValidUserCountry(user)){
            return "Not a french resident";
        }

        if (calculateAge(user.getBirthdate())==0){
            return "format of birthdate invalid please use this format dd/MM/yyyy";
        }
        if (!isValidUserBirthdate(user)){
            return "not an adult";
        }

        return "Invalid input";
    }

    /**
     * a function that tests if a user is a French resident or not
     * @param user : User
     * @return : boolean
     */
    private boolean isValidUserCountry(User user) {
        // Implement your validation logic here
        // For example, check if the user's age is within a certain range
        return (Objects.equals(user.getCountry(), "France")) || (Objects.equals(user.getCountry(), "france"))  ;
    }

    /**
     * a function that calculates age from a string and returns 0 if the string was not written in the form dd/MM/yyyy
     * @param dateString : string
     * @return int : user's age
     */
    public static int calculateAge(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate birthDate = LocalDate.parse(dateString, formatter);
            LocalDate currentDate = LocalDate.now();

            Period period = Period.between(birthDate, currentDate);

            return period.getYears();
        } catch (DateTimeParseException e) {
            return 0;
        }
    }

    /**
     * a function that tests if the user's age is more than 18
     * @param user : User
     * @return boolean
     */
    private boolean isValidUserBirthdate(User user){
        if (user.getBirthdate() == null)
            return false;
        return (calculateAge(user.getBirthdate()) >= 18);
    }

    /**
     * a function that tests if all the required fields are not null
     * @param user : User
     * @return : boolean
     */
    private boolean isValidUserNull(User user){
        return user.getCountry()!= null && user.getUsername()!= null && user.getBirthdate()!= null ;
    }

    /**
     * a function that uses all the previous function to determine if the user is valid or not
     * @param user : User
     * @return : boolean
     */
    private boolean isValidUser(User user){
        return isValidUserBirthdate(user) && isValidUserNull(user) && isValidUserCountry(user);
    }

    /**
     * a function that gets you all the user's data in your embedded database
     * @return : ResponseEntity<User>
     */
    @GetMapping("/api/users")
    public List<User> getUsers(){
        return userRepo.findAll();
    }

    /**
     * a function that gets you a user's data depending on his username
     * @param username : String
     * @return : ResponseEntity<User>
     */
    @GetMapping("api/users/{username}")
    public ResponseEntity<Object> getUserByUsername(@PathVariable String username){
        User user = userRepo.findUserByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return new ResponseEntity<Object>("not found" , HttpStatus.NOT_FOUND);
        }
    }
}
