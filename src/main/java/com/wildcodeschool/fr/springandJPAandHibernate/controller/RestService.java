package com.wildcodeschool.fr.springandJPAandHibernate.controller;

import com.wildcodeschool.fr.springandJPAandHibernate.entities.User;
import com.wildcodeschool.fr.springandJPAandHibernate.entities.UserMemory;
import com.wildcodeschool.fr.springandJPAandHibernate.repositories.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestService {

    @Autowired
    private UserMemory userMemory;

    @Autowired
    private UserDao userDao;

    public void SaveUser() {

    }

    @GetMapping("/users")       //requete en GET http://localhost:8080/users
    private List<User> GetMapping() {
        for (User user : userMemory.userAdd()
        ) {
            userDao.save(user);
        }
        return userDao.findAll();
    }

    @GetMapping("/users/{id}")      //requete en GET http://localhost:8080/users/5
    User one(@PathVariable Long id)  {
        try{
            userDao.findById(id).get();
        } catch (Exception e) {
            throw new RuntimeException("Erreur : cet id n'existe pas", e);
        }
        return userDao.findById(id).get();
    }

    @DeleteMapping("/users/{id}")   //requete en DELETE http://localhost:8080/users/5
    void deleteEmployee(@PathVariable Long id) {
        try{
        userDao.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur : cet id n'existe pas", e);
        }
    }

    @PutMapping("/users/create")    //requete en PUT http://localhost:8080/users/create?name=duce&surname=jeanclaude&age=40
    void saveEmployee(@RequestParam(value = "name") String name, @RequestParam(value = "surname") String surname, @RequestParam(value = "age") int age){
        User user = new User(name, surname,age);
        userDao.save(user);
    }
}




