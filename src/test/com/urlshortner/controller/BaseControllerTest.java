package test.com.urlshortner.controller;

import main.com.urlshortner.controller.BaseController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration()
public class BaseControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(new BaseController()).build();
    }

    @Test
    public void testHelp () throws Exception{
        mockMvc.perform(get("/help"))
                .andExpect(status().isOk())
                .andExpect(content().string("Use /account for creating an account (POST data must contain 'accountId' in the request JSON)<br>Use /register for registering url's (POST data must contain 'url' in the request JSON). The request header must have the authentication header comprising of Basic auth (Basic 'base64Credentials')<br>Examples :-<br>curl -H \"Content-Type: application/json\" -X POST -d '{\"accountId\":\"sampleAccountId\"}' http://localhost:8080/account<br>curl -H \"Content-Type: application/json\" -H \"Authorization:Basic cG9wOmNvRGo5eg==\" -X POST -d '{\"url\":\"http://google.com\",\"redirectType\":\"301\"}' http://localhost:8080/register<br>Further more statistics of a specific user's url redirection can be obtained by using /statistic/{accountId}. The request header must have the authentication header comprising of Basic auth (Basic 'base64Credentials')<br>"));
    }

}
