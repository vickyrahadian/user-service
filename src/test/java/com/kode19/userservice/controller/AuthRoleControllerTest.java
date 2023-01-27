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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    public void test_api_create_role_succeed() throws Exception {

        ResultActions result = mockMvc.perform(post("/api/v1/auth/role")
                .content("""
                        {
                            "role_name":"PEMIMPIN KANTOR KAS"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isCreated());
    }

    @Test
    public void test_api_create_role_failed_blank_role_name() throws Exception {

        ResultActions result = mockMvc.perform(post("/api/v1/auth/role")
                .content("""
                        {
                            "role_name":""
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void test_api_create_role_failed_minimum_length_less_than_10() throws Exception {

        ResultActions result = mockMvc.perform(post("/api/v1/auth/role")
                .content("""
                        {
                            "role_name":"admin"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isBadRequest());
    }


    @Test
    public void test_api_create_role_failed_maximum_size_more_than_255() throws Exception {

        ResultActions result = mockMvc.perform(post("/api/v1/auth/role")
                .content("""
                        {
                            "role_name":"Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32."
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void test_api_create_role_failed_no_role_name_key() throws Exception {

        ResultActions result = mockMvc.perform(post("/api/v1/auth/role")
                .content("""
                        {
                            "xxx": "administrator"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void test_api_get_role() throws Exception {

        ResultActions result = mockMvc.perform(get("/api/v1/auth/role"));

        //then
        result
                .andExpect(status().isOk());
    }

}