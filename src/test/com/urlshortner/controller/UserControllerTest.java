package test.com.urlshortner.controller;

import main.com.urlshortner.Application;
import main.com.urlshortner.controller.UserController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration()
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserController userController;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testSuccessfulAccountRegistration() throws Exception{
        String userName = "TestUser1";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/account")
                .content("{\"accountId\":\"" + userName + "\"}"))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andReturn();
        Assert.assertTrue(result.getResponse().getContentAsString().startsWith("{\"success\":\"true\",\"description\":\"Your account is opened\",\"password\":\""));
    }

    @Test
    public void testFailureRegistration() throws Exception{
        String userName = "TestUser2";
        mockMvc.perform(MockMvcRequestBuilders.post("/account")
                .content("{\"accountId\":\"" + userName + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        //Trying registration of same userName
        mockMvc.perform(MockMvcRequestBuilders.post("/account")
                .content("{\"accountId\":\"" + userName + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().string("{\"success\":\"false\",\"description\":\"Account with that ID already exists\"}"));

    }

}
