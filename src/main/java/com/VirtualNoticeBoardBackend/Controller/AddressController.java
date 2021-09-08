package com.VirtualNoticeBoardBackend.Controller;

import com.VirtualNoticeBoardBackend.Model.Address;
import com.VirtualNoticeBoardBackend.Model.Announcement;
import com.VirtualNoticeBoardBackend.Payload.Requests.AddNewAddressRequest;
import com.VirtualNoticeBoardBackend.Payload.Requests.RemoveAddressRequest;
import com.VirtualNoticeBoardBackend.Payload.Responses.CustomResponse;
import com.VirtualNoticeBoardBackend.Repository.AddressRepository;
import com.VirtualNoticeBoardBackend.Repository.AnnouncementRepository;
import com.VirtualNoticeBoardBackend.Repository.UserRepository;
import com.VirtualNoticeBoardBackend.Security.JWTUtil;
import com.VirtualNoticeBoardBackend.Security.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/AddressController")
public class AddressController {

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AnnouncementRepository announcementRepository;
    @Autowired
    JWTUtil jwtUtil;
    @Autowired
    TokenValidator validator;

    @GetMapping("/addresses")
    public ResponseEntity<List<Address>> getAddresses(){
            return ResponseEntity.ok().body(addressRepository.findAll());
    }

    @GetMapping("/addressesByUser")
    public ResponseEntity<?> getAddressesByUser(@RequestHeader(value = "token") String token){
        if(validator.hasExpired(token)) return ResponseEntity.badRequest().body(new CustomResponse("Not authorised"));
        else {
            return ResponseEntity.ok().body(addressRepository.findByUser(userRepository.findByEmail(jwtUtil.getUsernameFromToken(token))));
        }
    }

    @PostMapping("/createAddress")
    public ResponseEntity<?> createAddress(@RequestHeader(value = "token") String token, @RequestBody AddNewAddressRequest req) {
        if (validator.hasExpired(token)) return ResponseEntity.badRequest().body(new CustomResponse("Not authorised"));
        else {
            addressRepository.save(new Address(req.getStreet(), req.getCity(), req.getProvince(), req.getCountry(), userRepository.findByEmail(jwtUtil.getUsernameFromToken(token))));
            return ResponseEntity.ok().body(new CustomResponse("Address added!"));
        }
    }

    @DeleteMapping("/removeAddress")
    public ResponseEntity<?> removeAddress(@RequestHeader(value = "token") String token, @RequestBody RemoveAddressRequest req){
        if (validator.hasExpired(token)) return ResponseEntity.badRequest().body(new CustomResponse("Not authorised"));
        else {
            List<Announcement> announcementsToRemove = announcementRepository.findAllByAddress(req.getAddress());
            for(Announcement ann: announcementsToRemove){
                announcementRepository.delete(ann);
            }
            addressRepository.delete(req.getAddress());
            return ResponseEntity.ok().body(new CustomResponse("Address deleted!"));
        }
    }
}
