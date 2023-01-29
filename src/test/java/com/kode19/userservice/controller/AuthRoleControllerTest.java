package com.kode19.userservice.controller;

import com.kode19.userservice.service.AuthRoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AuthRoleControllerTest {

    @InjectMocks
    private AuthRoleController authRoleController;

    @Mock
    private AuthRoleService authRoleService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authRoleController).build();
    }


    @Test
    public void test_api_create_role() throws Exception {

        getPerform("role_name", "KEPALA KANTOR KAS")
                .andExpect(status().isCreated());

        getPerform("role_name", "")
                .andExpect(status().isBadRequest());

        getPerform("role_name", "ADMIN")
                .andExpect(status().isBadRequest());

        getPerform("role_name", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.")
                .andExpect(status().isBadRequest());

        getPerform("xxx", "ADMINISTRATOR")
                .andExpect(status().isBadRequest());
    }


    @Test
    public void test_api_get_roles() throws Exception {
        mockMvc.perform(get("/api/v1/auth/role")).andExpect(status().isOk());
    }


    private ResultActions getPerform(String key, String role) throws Exception {
        return mockMvc.perform(post("/api/v1/auth/role")
                .content("{ \"" + key + "\" : \"" + role + "\" }")
                .contentType(MediaType.APPLICATION_JSON));
    }

}