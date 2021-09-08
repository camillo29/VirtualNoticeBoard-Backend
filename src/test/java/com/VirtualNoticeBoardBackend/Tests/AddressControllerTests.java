package com.VirtualNoticeBoardBackend.Tests;

import com.VirtualNoticeBoardBackend.Model.User;
import com.VirtualNoticeBoardBackend.Payload.Requests.AddNewAddressRequest;
import com.VirtualNoticeBoardBackend.Payload.Requests.RemoveAddressRequest;
import com.VirtualNoticeBoardBackend.Repository.AddressRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
@AutoConfigureMockMvc
public class AddressControllerTests {
    @Autowired
    WebApplicationContext context;
    @Autowired
    MockMvc mvc;
    @Autowired
    JWTUtil jwt;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;
    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }
    @Test
    public void addressesSuccessTest(){
        try{
            mvc.perform(get("/api/AddressController/addresses"))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().contentType("application/json")).andExpect(content().string(containsString("id")));
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void createAddressSuccessTest(){
        try{
            User user = userRepository.findByEmail("jan_kowalski@op.pl");
            String token = jwt.generateToken(user);
            AddNewAddressRequest req = new AddNewAddressRequest("Test", "Test", "Test", "Test test");
            mvc.perform(post("/api/AddressController/createAddress")
                    .contentType("application/json").content(new Gson().toJson(req)).header("token", token))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().contentType("application/json")).andExpect(content().string(containsString("Address added!")));
        } catch(Exception e) {e.printStackTrace();}
    }

    @Test
    public void removeAddressSuccessTest(){
        try{
            User user = userRepository.findByEmail("jan_kowalski@op.pl");
            String token = jwt.generateToken(user);
            RemoveAddressRequest req = new RemoveAddressRequest(addressRepository.findById(1L).orElseThrow(()->new Exception("not found")));
            mvc.perform(delete("/api/AddressController/removeAddress")
                    .contentType("application/json").content(new Gson().toJson(req)).header("token", token))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().contentType("application/json")).andExpect(content().string(containsString("Address deleted!")));
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
