package com.wildcodeschool.fr.springandJPAandHibernate.entities;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMemory {

    User user1 = new User("cordenod", "julien", 46);
    User user2 = new User("marion", "hervé", 46);
    User user3 = new User("couedelo", "stephane", 35);
    User user4 = new User("royer", "julien", 35);
    User user5 = new User("manrique", "jeff", 57);

    public List<User> userAdd() {
        List<User> myList = new ArrayList<>();
        myList.add(user1);
        myList.add(user2);
        myList.add(user3);
        myList.add(user4);
        myList.add(user5);
        return myList;
    }
}
