package com.VirtualNoticeBoardBackend.Controller;

import com.VirtualNoticeBoardBackend.Model.*;
import com.VirtualNoticeBoardBackend.Payload.Requests.CreateAnnouncementRequest;
import com.VirtualNoticeBoardBackend.Payload.Requests.RemoveAnnouncementRequest;
import com.VirtualNoticeBoardBackend.Payload.Responses.AnnouncementResponse;
import com.VirtualNoticeBoardBackend.Payload.Responses.CustomResponse;
import com.VirtualNoticeBoardBackend.Repository.*;
import com.VirtualNoticeBoardBackend.Security.JWTUtil;
import com.VirtualNoticeBoardBackend.Security.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/AnnouncementController")
public class AnnouncementController {
    @Autowired
    AnnouncementRepository announcementRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenValidator validator;
    @Autowired
    JWTUtil jwtUtil;

    @GetMapping("/announcements")
    public ResponseEntity<List<Announcement>> getAllAnnouncements(){
        List<Announcement> announcements = announcementRepository.findAll();
        return ResponseEntity.ok().body(announcements);
    }

    @GetMapping("/announcementsByUser")
    public ResponseEntity<?> getUserAnnouncements(@RequestHeader(value = "token") String token){
        if(validator.hasExpired(token)) return ResponseEntity.badRequest().body(new CustomResponse("Not authorized"));
        else {
            return ResponseEntity.ok().body(announcementRepository.findAllByUser(
                    userRepository.findByEmail(jwtUtil.getUsernameFromToken(token))));
        }
    }

    @GetMapping("/announcement/{id}")
    public ResponseEntity<?> getAnnouncementById(@PathVariable(value = "id") Long id, @RequestHeader(value = "token") String token) throws Exception{
        if(validator.hasExpired(token)) return ResponseEntity.badRequest().body(new CustomResponse("Not authorized"));
        else {
            Announcement ann = announcementRepository.findById(id).orElseThrow(()->new Exception("Announcement not found"));
            return ResponseEntity.ok().body(new AnnouncementResponse(ann));
        }
    }

    @PostMapping("/createAnnouncement")
    public ResponseEntity<?> createAnnouncement(@RequestBody CreateAnnouncementRequest req, @RequestHeader(value = "token") String token) throws Exception{
        if(validator.hasExpired(token)) return ResponseEntity.badRequest().body(new CustomResponse("Not authorized"));
        else{
            Address address = addressRepository.findById(req.getAddress().getId()).orElseThrow(()->new Exception("address not found"));
            User user = userRepository.findByEmail(jwtUtil.getUsernameFromToken(token));
            if(user == null) return ResponseEntity.badRequest().body(new CustomResponse("Not authorized"));
            else {
                announcementRepository.save(new Announcement(user, address, req.getType(), req.getTitle(), req.getDescription()));
                return ResponseEntity.ok().body(new CustomResponse("Announcement created!"));
            }
        }
    }

    @DeleteMapping("/removeAnnouncement")
    public ResponseEntity<?> removeAnnouncement(@RequestBody RemoveAnnouncementRequest req, @RequestHeader(value = "token") String token) throws Exception{
        if(validator.hasExpired(token)) return ResponseEntity.badRequest().body(new CustomResponse("Not authorized"));
        else {
            User user = userRepository.findByEmail(jwtUtil.getUsernameFromToken(token));
            Announcement ann = announcementRepository.findById(req.getAnnouncementId()).orElseThrow(()-> new Exception("Announcement not found"));
            if(ann.getUser().getId() == user.getId() || user.getRole().getName().equals("ADMIN")) {
                announcementRepository.delete(ann);
                return ResponseEntity.ok().body(new CustomResponse("Announcement deleted"));
            }
        }
        return ResponseEntity.badRequest().body(new CustomResponse("Error"));
    }
}
