package com.wildcodeschool.fr.springandJPAandHibernate.controller;

import com.wildcodeschool.fr.springandJPAandHibernate.entities.DeleteForm;
import com.wildcodeschool.fr.springandJPAandHibernate.entities.User;
import com.wildcodeschool.fr.springandJPAandHibernate.entities.UserForm;
import com.wildcodeschool.fr.springandJPAandHibernate.repositories.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class mainController {
    @Autowired
    private UserDao userDao;

    @Value("JPA, THYMELEAF, MVC avec SPRING")
    private String message;

    @Value("${error.message}")                      //(inject) via application.properties
    private String errorMessage;

    @Value("${error.message2}")                     //(inject) via application.properties
    private String errorMessage2;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("message", message);         //soit le <h2 th:utext="${message}">..!..</h2>
        return "index";
    }

    @RequestMapping(value = {"/personList"}, method = RequestMethod.GET)
    public String personList(Model model) {
        model.addAttribute("users", userDao.findAll());
        DeleteForm deleteForm = new DeleteForm();
        model.addAttribute("deleteForm", deleteForm);
        return "personList";
    }

    @RequestMapping(value = {"/addPerson"}, method = RequestMethod.GET)
    public String showAddPersonPage(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "addPerson";
    }

    @RequestMapping(value = {"/addPerson"}, method = RequestMethod.POST)
    public String savePerson(Model model, @ModelAttribute("userForm") UserForm userForm) {
        String firstName = userForm.getFirstName();
        String lastName = userForm.getLastName();
        int age = userForm.getAge();
        userDao.save(new User(firstName, lastName, age));
        if (firstName != null && firstName.length() > 0 //
                && lastName != null && lastName.length() > 0) {
            return "redirect:/personList";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "addPerson";
    }

    @RequestMapping(value = {"/deletePerson"}, method = RequestMethod.POST)
    public String deletePerson(Model model, @ModelAttribute("deleteForm") DeleteForm deleteForm) {
        Long id = deleteForm.getId();
        if (userDao.findById(id).isPresent()) {
            userDao.deleteById(id);
            return "redirect:/personList";
        }
        model.addAttribute("errorMessage2", errorMessage2);
        model.addAttribute("users", userDao.findAll());
        return "personList";
    }
}
