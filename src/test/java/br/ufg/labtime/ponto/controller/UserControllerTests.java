package br.ufg.labtime.ponto.controller;


import br.ufg.labtime.ponto.PontoApplication;
import br.ufg.labtime.ponto.dto.user.CreateUserDTO;
import br.ufg.labtime.ponto.dto.user.UserSearch;
import br.ufg.labtime.ponto.model.User;
import br.ufg.labtime.ponto.model.type.Situation;
import br.ufg.labtime.ponto.service.ModelMapperService;
import br.ufg.labtime.ponto.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static br.ufg.labtime.ponto.utils.MockUtils.createUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureWebMvc
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
@ContextConfiguration(classes = PontoApplication.class)
public class UserControllerTests {

    @MockBean
    UserService userService;

    ObjectMapper mapper = new ObjectMapper();
    Gson g = new Gson();

    @Autowired
    ModelMapperService modelMapperService;

    @Autowired
    MockMvc mockMvc;

    private ArrayList<User> mUserList;

    @Before
    public void setup() {
        mUserList = new ArrayList<>();
        mUserList.add(createUser());
    }

    @Test
    public void it_should_return_saved_user() throws Exception {
        CreateUserDTO newUser = new CreateUserDTO();
        newUser.setEmail("test@mail.com");
        newUser.setPassword("123456a");
        newUser.setName("Test");

        User user = new User();
        user.setName("Test");
        user.setEmail("test@mail.com");
        user.setPassword("123456a");
        user.setAdm(false);
        user.setSituation(Situation.ACTIVE);
        when(userService.save(any(User.class))).thenReturn(user);

        mockMvc.perform(put("/users")
                .content(mapper.writeValueAsString(newUser))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(newUser.getEmail()));
    }

    @Test
    @WithMockUser()
    public void it_should_return_user_list_when_logged() throws Exception {
        UserSearch search = new UserSearch();
        Pageable pageable = PageRequest.of(0, search.getQuantity());
        Page<User> page = new PageImpl<>(mUserList, pageable, mUserList.size());

        when(userService.getPage(any(UserSearch.class))).thenReturn(page);
        mockMvc.perform(
                post("/users")
                        .content(mapper.writeValueAsString(search))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").isArray());
    }

    @Test
    public void it_should_return_user_list_when_not_logged() throws Exception {
        UserSearch search = new UserSearch();
        Pageable pageable = PageRequest.of(0, search.getQuantity());
        Page<User> page = new PageImpl<>(mUserList, pageable, mUserList.size());

        when(userService.getPage(any(UserSearch.class))).thenReturn(page);
        mockMvc.perform(
                post("/users")
                        .content(mapper.writeValueAsString(search))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser()
    public void it_should_return_edited_user_when_logged() throws Exception {
        User user = createUser();
        User updatedUser = createUser();
        updatedUser.setName("Edited Test");
        updatedUser.setEmail("test2@mail.com");
        updatedUser.setId(user.getId());

        when(userService.update(any(User.class), any(Long.class))).thenReturn(updatedUser);
        mockMvc.perform(put("/users/" + user.getId().toString())
                .content(mapper.writeValueAsString(updatedUser))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(updatedUser.getEmail()));
    }

    @Test
    public void it_should_return_edited_user_when_not_logged() throws Exception {
        User user = createUser();
        User updatedUser = createUser();
        updatedUser.setName("Edited Test");
        updatedUser.setEmail("test2@mail.com");

        when(userService.update(any(User.class), any(Long.class))).thenReturn(updatedUser);
        mockMvc.perform(put("/users/" + user.getId().toString())
                .content(mapper.writeValueAsString(updatedUser))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser()
    public void it_should_return_user_when_logged() throws Exception {
        User user = createUser();
        Long id = (long) (Math.random() * 100);
        user.setId(id);
        when(userService.detail(any(Long.class))).thenReturn(user);

        mockMvc.perform(get("/users/" + id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    public void it_should_return_user_when_not_logged() throws Exception {
        User user = createUser();
        Long id = (long) (Math.random() * 100);
        user.setId(id);
        when(userService.detail(any(Long.class))).thenReturn(user);

        mockMvc.perform(get("/users/" + id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void it_should_be_ok_when_admin_deletes() throws Exception {
        Long id = (long) (Math.random() * 100);

        mockMvc.perform(delete("/users/" + id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void it_should_forbid_when_not_admin_deletes() throws Exception {
        Long id = (long) (Math.random() * 100);

        mockMvc.perform(delete("/users/" + id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void it_should_be_ok_when_admin_alter_admin() throws Exception {
        Long id = (long) (Math.random() * 100);

        mockMvc.perform(get("/users/" + id.toString() + "/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void it_should_forbid_when_not_admin_alter_admin() throws Exception {
        Long id = (long) (Math.random() * 100);

        mockMvc.perform(get("/users/" + id.toString() + "/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


}
