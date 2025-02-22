package com.pds.my_events.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.pds.my_events.Model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {}

