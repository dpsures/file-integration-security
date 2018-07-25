package org.poc.app;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestBasicAuthRole {

	@Autowired
    private MockMvc mockMvc;

    @Test
    public void accessProtected() throws Exception {
        this.mockMvc.perform(post("/api/file/upload").header("X-CSRF-TOKEN", "123123123"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void validUser() throws Exception {
        this.mockMvc.perform(post("/api/file/upload").header("X-CSRF-TOKEN", "123123123").with(httpBasic("admin", "password")))
                .andExpect(authenticated());
    }
    
    @Test
    public void noAccessUser() throws Exception {
        this.mockMvc.perform(post("/api/file/upload").header("X-CSRF-TOKEN", "123123123").with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }
}