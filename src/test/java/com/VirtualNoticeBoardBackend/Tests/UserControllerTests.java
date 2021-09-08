package com.VirtualNoticeBoardBackend.Tests;

import com.VirtualNoticeBoardBackend.Model.User;
import com.VirtualNoticeBoardBackend.Payload.Requests.ChangePasswordRequest;
import com.VirtualNoticeBoardBackend.Payload.Requests.ChangePhoneNumberRequest;
import com.VirtualNoticeBoardBackend.Payload.Requests.SignInRequest;
import com.VirtualNoticeBoardBackend.Payload.Requests.SignUpRequest;
import com.VirtualNoticeBoardBackend.Repository.UserRepository;
import com.VirtualNoticeBoardBackend.Security.JWTUtil;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired
    WebApplicationContext context;
    @Autowired
    MockMvc mvc;
    @Autowired
    JWTUtil jwt;
    @Autowired
    UserRepository userRepository;
    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void signInSuccessTest(){
        try{
            SignInRequest req = new SignInRequest("jan_kowalski@op.pl", "zaq1@WSX");
            mvc.perform(post("/api/UserController/signIn")
                    .contentType("application/json").content(new Gson().toJson(req)))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().contentType("application/json")).andExpect(content().string(containsString("token")));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void signInPasswordFailureTest(){
        try{
            SignInRequest req = new SignInRequest("jan_kowalski@op.pl", "wrong_password");
            mvc.perform(post("/api/UserController/signIn")
                    .contentType("application/json").content(new Gson().toJson(req)))
                    .andDo(print()).andExpect(status().isBadRequest())
                    .andExpect(content().contentType("application/json")).andExpect(content().string(containsString("Wrong password")));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void signInEmailFailureTest(){
        try{
            SignInRequest req = new SignInRequest("jan_kowalsski@op.pl", "zaq1@WSX");
            mvc.perform(post("/api/UserController/signIn")
                    .contentType("application/json").content(new Gson().toJson(req)))
                    .andDo(print()).andExpect(status().isBadRequest())
                    .andExpect(content().contentType("application/json")).andExpect(content().string(containsString("Wrong email")));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void singUpSuccessTest(){
        try{
            SignUpRequest req = new SignUpRequest("TestName", "TestSurname", "123456789", "testname_testsurname@test.test", "test"); //String name, String surname, String phoneNumber, String email, String password
            mvc.perform(post("/api/UserController/signUp")
                    .contentType("application/json").content(new Gson().toJson(req)))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().contentType("application/json")).andExpect(content().string(containsString("Registered successfully!")));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void singUpEmailFailureTest(){
        try{
            SignUpRequest req = new SignUpRequest("TestName", "TestSurname", "123456789", "jan_kowalski@op.pl", "test"); //String name, String surname, String phoneNumber, String email, String password
            mvc.perform(post("/api/UserController/signUp")
                    .contentType("application/json").content(new Gson().toJson(req)))
                    .andDo(print()).andExpect(status().isBadRequest())
                    .andExpect(content().contentType("application/json")).andExpect(content().string(containsString("Email already in use")));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void singUpPhoneFailureTest(){
        try{
            SignUpRequest req = new SignUpRequest("TestName", "TestSurname", "746395637", "test_test@test.com", "test"); //String name, String surname, String phoneNumber, String email, String password
            mvc.perform(post("/api/UserController/signUp")
                    .contentType("application/json").content(new Gson().toJson(req)))
                    .andDo(print()).andExpect(status().isBadRequest())
                    .andExpect(content().contentType("application/json")).andExpect(content().string(containsString("Phone number already in use")));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getRoleSuccessTest(){
        try{
            User user = userRepository.findByEmail("jan_kowalski@op.pl");
            String token = jwt.generateToken(user);
            mvc.perform(get("/api/UserController/getRole")
                    .contentType("application/json").header("token", token))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().contentType("application/json")).andExpect(content().string(containsString("ADMIN")));
        } catch (Exception e){ e.printStackTrace(); }
    }

    @Test
    public void changePhoneNumberSuccessTest(){
        try{
            User user = userRepository.findByEmail("jan_kowalski@op.pl");
            String token = jwt.generateToken(user);
            ChangePhoneNumberRequest req = new ChangePhoneNumberRequest("123456789", "zaq1@WSX");
            mvc.perform(put("/api/UserController/changePhoneNumber")
                    .contentType("application/json").content(new Gson().toJson(req)).header("token", token))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().contentType("application/json")).andExpect(content().string(containsString("Phone number changed")));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void changePhoneNumberPasswordFailureTest(){
        //Wrong password
        try{
            User user = userRepository.findByEmail("jan_kowalski@op.pl");
            String token = jwt.generateToken(user);
            ChangePhoneNumberRequest req = new ChangePhoneNumberRequest("123456789", "wrong_password");
            mvc.perform(put("/api/UserController/changePhoneNumber")
                    .contentType("application/json").content(new Gson().toJson(req)).header("token", token))
                    .andDo(print()).andExpect(status().isBadRequest())
                    .andExpect(content().contentType("application/json")).andExpect(content().string(containsString("Wrong password")));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void changePasswordSuccessTest(){
        try{
            //"Password changed"
            User user = userRepository.findByEmail("jan_kowalski@op.pl");
            String token = jwt.generateToken(user);
            ChangePasswordRequest req = new ChangePasswordRequest("zaq1@WSX", "zaq1@WSX"); //String oldPassword, String newPassword
            mvc.perform(put("/api/UserController/changePassword")
                    .contentType("application/json").content(new Gson().toJson(req)).header("token", token))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().contentType("application/json")).andExpect(content().string(containsString("Password changed")));
        } catch(Exception e) {e.printStackTrace();}
    }

    @Test
    public void changePasswordPasswordFailureTest(){
        try{
            //"Password changed"
            User user = userRepository.findByEmail("jan_kowalski@op.pl");
            String token = jwt.generateToken(user);
            ChangePasswordRequest req = new ChangePasswordRequest("wrong_password", "qwerty"); //String oldPassword, String newPassword
            mvc.perform(put("/api/UserController/changePassword")
                    .contentType("application/json").content(new Gson().toJson(req)).header("token", token))
                    .andDo(print()).andExpect(status().isBadRequest())
                    .andExpect(content().contentType("application/json")).andExpect(content().string(containsString("Wrong password")));
        } catch(Exception e) {e.printStackTrace();}
    }

    @Test
    public void getUserByEmailTestSuccess(){
        try{
            User user = userRepository.findByEmail("jan_kowalski@op.pl");
            String token = jwt.generateToken(user);
            mvc.perform(get("/api/UserController/getUserByMail/{email}", "jan_kowalski@op.pl")
                    .contentType("application/json").header("token", token))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().contentType("application/json")).andExpect(content().string(containsString("jan_kowalski@op.pl")));
        } catch(Exception e){
            e.printStackTrace();
        }
    }


}
