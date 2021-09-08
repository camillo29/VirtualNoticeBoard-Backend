package com.VirtualNoticeBoardBackend.Tests;

import com.VirtualNoticeBoardBackend.Model.Address;
import com.VirtualNoticeBoardBackend.Model.User;
import com.VirtualNoticeBoardBackend.Payload.Requests.CreateAnnouncementRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
@AutoConfigureMockMvc
public class AnnouncementControllerTests {
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
    public void announcementsSuccessTest(){
        try{
            mvc.perform(get("/api/AnnouncementController/announcements"))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().contentType("application/json")).andExpect(content().string(containsString("id")));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void announcementsByUserSuccessTest(){
        try{
            User user = userRepository.findByEmail("piotr_drewno@op.pl");
            String token = jwt.generateToken(user);
            mvc.perform(get("/api/AnnouncementController/announcementsByUser").header("token", token))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().contentType("application/json")).andExpect(content().string(containsString("id")));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void announcementSuccessTest(){
        try{
            User user = userRepository.findByEmail("piotr_drewno@op.pl");
            String token = jwt.generateToken(user);
            mvc.perform(get("/api/AnnouncementController/announcement/{id}", "1").header("token", token))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().contentType("application/json")).andExpect(content().string(containsString("id")));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void createAnnouncementAddressFailureTest(){
        try{
            User user = userRepository.findByEmail("piotr_drewno@op.pl");
            String token = jwt.generateToken(user);
            Address address = new Address(); address.setId(1L);
            CreateAnnouncementRequest req = new CreateAnnouncementRequest("test", "test", address, null); //String title, String description, Address address, Type type
            mvc.perform(post("/api/AnnouncementController/createAnnouncement").contentType("application/json")
                    .content(new Gson().toJson(req)).header("token", token))
                    .andDo(print()).andExpect(status().isBadRequest())
                    .andExpect(content().contentType("application/json")).andExpect(content().string(containsString("address not found")));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
