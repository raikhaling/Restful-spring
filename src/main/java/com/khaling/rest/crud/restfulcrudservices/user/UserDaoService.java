package com.khaling.rest.crud.restfulcrudservices.user;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Component to tell the Spring to manage the beans
 * For now only static
 */
@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static Integer idCount = 0;
    static {
        users.add(new User(1,"Nabin", LocalDate.now()));
        users.add(new User(2,"Santosh", LocalDate.now().plusYears(1)));
        users.add(new User(3,"Mukesh", LocalDate.now().plusYears(2)));
    }
    //find all users
    public List<User> findAll(){
        return users;
    }
    //save a user
    public User save(User user){
        if(user.getId()==null){
            user.setId(++idCount);
        }
        users.add(user);
        return user;

    }
    //findOne user
    public User findOne(int id){
       for (User user : users){
           if(user.getId() == id){
               return user;
           }
       }
       return null;
    }

}
