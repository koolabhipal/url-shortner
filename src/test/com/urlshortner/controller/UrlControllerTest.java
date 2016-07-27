package test.com.urlshortner.controller;

import main.com.urlshortner.Application;
import main.com.urlshortner.controller.UrlController;
import main.com.urlshortner.service.UserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpHeaders;
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
public class UrlControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;
    @Autowired
    private UrlController urlController;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(urlController).build();


    }

    @Test
    public void testRegistrationOfUrl() throws Exception{

        String userName = "DummyUser1";
        String password = userService.createUser(userName).get();
        byte[] authEncBytes = Base64.encodeBase64((userName + ":" + password).getBytes());
        String authorizationString = new String(authEncBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + authorizationString);
        headers.set("Content-Type", "application/json");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .headers(headers)
                .content("{\"url\":\"http://google.com\",\"redirectType\":\"301\"}"))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andReturn();

        Assert.assertTrue(result.getResponse().getContentAsString().startsWith("{\"shortUrl\":\"localhost:8080/"));
    }

    @Test
    public void testRedirectionAndStatistic() throws Exception{

        String userName = "DummyUser2";
        String password = userService.createUser(userName).get();
        byte[] authEncBytes = Base64.encodeBase64((userName + ":" + password).getBytes());
        String authorizationString = new String(authEncBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + authorizationString);
        headers.set("Content-Type", "application/json");

        MvcResult registerResult = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .headers(headers)
                .content("{\"url\":\"http://google.com\",\"redirectType\":\"301\"}"))
                .andReturn();
        String responseString = registerResult.getResponse().getContentAsString();

        MvcResult redirectionResult = mockMvc.perform(MockMvcRequestBuilders.get("/sh/" + responseString.substring(28).replace("\"", "").replace("}", ""))).andReturn();
        Assert.assertEquals(redirectionResult.getResponse().getRedirectedUrl(), "http://google.com");

        // redirection hit 2 times more count expected 3
        mockMvc.perform(MockMvcRequestBuilders.get("/sh/" + responseString.substring(28).replace("\"", "").replace("}", ""))).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.get("/sh/" + responseString.substring(28).replace("\"", "").replace("}", ""))).andReturn();


        mockMvc.perform(MockMvcRequestBuilders.get("/statistic/" + userName)
                    .headers(headers))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string("{\"http://google.com\":\"3\"}"));

    }

}
