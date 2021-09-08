package com.VirtualNoticeBoardBackend.Controller;

import com.VirtualNoticeBoardBackend.Model.*;
import com.VirtualNoticeBoardBackend.Payload.Responses.CustomResponse;
import com.VirtualNoticeBoardBackend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/DebugController")
public class DebugController {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    AnnouncementRepository announcementRepository;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/initialFill")
    public ResponseEntity<?> initialFill(){
        Role admin = roleRepository.save(new Role("ADMIN"));
        Role client = roleRepository.save(new Role("CLIENT"));

        Person adminPerson = personRepository.save(new Person("Jan", "Kowalski", "364758354", "jan_kowalski@op.pl"));
        Person clientPerson = personRepository.save(new Person("Piotr", "Drewno", "365946564", "piotr_drewno@op.pl"));

        User adminUser = userRepository.save(new User(adminPerson.getEmail(), encoder.encode("zaq1@WSX"), admin, adminPerson));
        User clientUser = userRepository.save(new User(clientPerson.getEmail(), encoder.encode("zaq1@WSX"), client, clientPerson));

        Address address = addressRepository.save(new Address("Krakowska 7", "Kraków", "Małopolska", "Poland", clientUser));

        Type type = typeRepository.save(new Type("Sale"));
        typeRepository.save(new Type("Rent"));
        typeRepository.save(new Type("Give away for free"));
        typeRepository.save(new Type("Event"));
        typeRepository.save(new Type("Work Offer"));
        typeRepository.save(new Type("Announcement"));

        announcementRepository.save(new Announcement(clientUser, address, type, "Bike for sell",
                "Cannondale 1994 Mountain Bike Excellent Body Condition. An item that has been used previously." +
                        " The item may have some signs of cosmetic wear, but is fully operational and functions as intended. " +
                        "This item may be a floor model or store return that has been used. Price 600 PLN, can be negotiated." ));
                        //User user, Address address, Type type, String title, String description

        return ResponseEntity.ok().body("Data filled");
    }

    @GetMapping("/checkIfDataExists")
    public ResponseEntity<CustomResponse> checkIfDataExists(){
        if(roleRepository.findAll().size() > 0) return ResponseEntity.ok().body(new CustomResponse("true"));
        else return ResponseEntity.ok().body(new CustomResponse("false"));
    }
}
