package com.VirtualNoticeBoardBackend.Controller;

import com.VirtualNoticeBoardBackend.Model.Person;
import com.VirtualNoticeBoardBackend.Model.Role;
import com.VirtualNoticeBoardBackend.Model.User;
import com.VirtualNoticeBoardBackend.Payload.Requests.ChangePasswordRequest;
import com.VirtualNoticeBoardBackend.Payload.Requests.ChangePhoneNumberRequest;
import com.VirtualNoticeBoardBackend.Payload.Requests.SignInRequest;
import com.VirtualNoticeBoardBackend.Payload.Requests.SignUpRequest;
import com.VirtualNoticeBoardBackend.Payload.Responses.CustomResponse;
import com.VirtualNoticeBoardBackend.Payload.Responses.SignInResponse;
import com.VirtualNoticeBoardBackend.Payload.Responses.UserResponse;
import com.VirtualNoticeBoardBackend.Repository.PersonRepository;
import com.VirtualNoticeBoardBackend.Repository.RoleRepository;
import com.VirtualNoticeBoardBackend.Repository.UserRepository;
import com.VirtualNoticeBoardBackend.Security.JWTUtil;
import com.VirtualNoticeBoardBackend.Security.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/UserController")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JWTUtil jwt;
    @Autowired
    TokenValidator validator;
    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/getUserByMail/{email}")
    public ResponseEntity<?> getUserByMail(@PathVariable(value = "email") String email, @RequestHeader(value = "token") String token){
        if(validator.hasExpired(token)) return ResponseEntity.badRequest().body(new CustomResponse("Not authorized"));
        else {
            User user = userRepository.findByEmail(email);
            return ResponseEntity.ok().body(new UserResponse(user.getPerson().getName(), user.getPerson().getSurname(),
                    user.getPerson().getPhoneNumber(), user.getEmail()));
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest req){
        if(userRepository.findByEmail(req.getEmail())!=null) return ResponseEntity.badRequest().body(new CustomResponse("Email already in use"));
        else if(personRepository.findByPhoneNumber(req.getPhoneNumber())!=null) return ResponseEntity.badRequest().body(new CustomResponse("Phone number already in use"));
        else {
            Person person = personRepository.save(new Person(req.getName(), req.getSurname(), req.getPhoneNumber(), req.getEmail()));
            Role client = roleRepository.findByName("CLIENT");
            User user = userRepository.save(new User(person.getEmail(), encoder.encode(req.getPassword()), client, person));
            if(user != null) return ResponseEntity.ok().body(new CustomResponse("Registered successfully!"));
            else ResponseEntity.badRequest().body(new CustomResponse("Error, try again later"));
        }
        return ResponseEntity.badRequest().body(new CustomResponse("Error, try again later"));
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest req){
        User user = userRepository.findByEmail(req.getEmail());
        if(user!=null){
            if(encoder.matches(req.getPassword(), user.getPassword())){
                String token = jwt.generateToken(user);
                return ResponseEntity.ok().body(new SignInResponse(token, user.getEmail()));
            } else return ResponseEntity.badRequest().body(new CustomResponse("Wrong password"));
        } else return ResponseEntity.badRequest().body(new CustomResponse("Wrong email"));
    }

    @GetMapping("/getRole")
    public ResponseEntity<?> getUserRole(@RequestHeader(value = "token") String token){
        if(validator.hasExpired(token)) return ResponseEntity.badRequest().body(new CustomResponse("Not authorized"));
        else{
            Role role = userRepository.findByEmail(jwt.getUsernameFromToken(token)).getRole();
            return ResponseEntity.ok().body(new CustomResponse(role.getName()));
        }
    }

    @PutMapping("/changePassword")
    public ResponseEntity<CustomResponse> changePassword(@RequestBody ChangePasswordRequest req, @RequestHeader(value = "token") String token){
        if(validator.hasExpired(token)) return ResponseEntity.badRequest().body(new CustomResponse("Not authorized"));
        else {
            User user = userRepository.findByEmail(jwt.getUsernameFromToken(token));
            if(user == null) return ResponseEntity.badRequest().body(new CustomResponse("User not found!"));
            else {
                if(encoder.matches(req.getOldPassword(), user.getPassword())){
                    user.setPassword(encoder.encode(req.getNewPassword()));
                    userRepository.save(user);
                    return ResponseEntity.ok().body(new CustomResponse("Password changed"));
                }
                else return ResponseEntity.badRequest().body(new CustomResponse("Wrong password"));
            }
        }
    }

    @PutMapping("/changePhoneNumber")
    public ResponseEntity<CustomResponse> changePhoneNumber(@RequestBody ChangePhoneNumberRequest req, @RequestHeader(value = "token") String token) throws Exception{
        if(validator.hasExpired(token)) return ResponseEntity.badRequest().body(new CustomResponse("Not authorized"));
        else {
            User user = userRepository.findByEmail(jwt.getUsernameFromToken(token));
            if(user == null) return ResponseEntity.badRequest().body(new CustomResponse("User not found!"));
            else {
                if(encoder.matches(req.getPassword(), user.getPassword())){
                    Person person = personRepository.findById(user.getPerson().getId()).orElseThrow(()->new Exception("Person not found"));
                    person.setPhoneNumber(req.getNewPhoneNumber());
                    personRepository.save(person);
                    return ResponseEntity.ok().body(new CustomResponse("Phone number changed"));
                } else return ResponseEntity.badRequest().body(new CustomResponse("Wrong password"));
            }
        }
    }



}
