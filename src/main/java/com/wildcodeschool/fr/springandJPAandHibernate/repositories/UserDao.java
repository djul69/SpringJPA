package com.wildcodeschool.fr.springandJPAandHibernate.repositories;

import com.wildcodeschool.fr.springandJPAandHibernate.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
}
