package com.VirtualNoticeBoardBackend.Repository;

import com.VirtualNoticeBoardBackend.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByPhoneNumber(String phoneNumber);
}
