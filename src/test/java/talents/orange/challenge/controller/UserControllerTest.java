package talents.orange.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import talents.orange.challenge.model.User;
import talents.orange.challenge.service.UserService;
import talents.orange.challenge.service.exception.UniqueViolationException;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService service;

    private User user;

    private static Stream<LocalDate> localDateArguments() {
        return Stream.of(LocalDate.MAX, LocalDate.now(), null);
    }

    @BeforeEach
    void setUp() {
        user = User.builder()
            .id(1L)
            .name("Bruce Wayne")
            .email("bwayne@wayneenterprises.com")
            .cpf("27854636419")
            .birthday(LocalDate.of(1972, 2, 19))
            .build();
    }

    @Test
    @DisplayName("Should create a new user when all fields are valid.")
    void createUserTest() throws Exception {
        when(service.create(any(User.class))).thenReturn(user);

        mvc.perform(post("/api/user/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(user)))
            .andExpect(status().isCreated())
            .andExpect(header().exists("location"))
            .andExpect(header().string("location", containsString("/api/user/" + user.getId())));

        verify(service).create(any(User.class));
    }

    @ParameterizedTest(name = "{index} Name = {0} returns status 400")
    @NullAndEmptySource
    void createUserInvalidNameTest(String name) throws Exception {
        user.setName(name);

        mvc.perform(post("/api/user/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest());

        verify(service, never()).create(any());
    }

    @ParameterizedTest(name = "{index} CPF = {0} returns status 400")
    @NullAndEmptySource
    @ValueSource(strings = {"1111111111", "abcdfgh"})
    void createUserInvalidCPFTest(String cpf) throws Exception {
        user.setCpf(cpf);

        mvc.perform(post("/api/user/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest());

        verify(service, never()).create(any());
    }

    @ParameterizedTest(name = "{index} Email = {0} returns status 400")
    @NullAndEmptySource
    @ValueSource(strings = {"1111111111", "not an email", "email.com"})
    void createUserInvalidEmailTest(String email) throws Exception {
        user.setEmail(email);

        mvc.perform(post("/api/user/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest());

        verify(service, never()).create(any());
    }

    @ParameterizedTest(name = "{index} Birthday = {0} returns status 400")
    @MethodSource("localDateArguments")
    void createUserInvalidBirthdayTest(LocalDate birthday) throws Exception {
        user.setBirthday(birthday);

        mvc.perform(post("/api/user/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest());

        verify(service, never()).create(any());
    }

    @Test
    @DisplayName("Should return status 400 Bad Request when service method throws Exception")
    void serviceExceptionTest() throws Exception {
        when(service.create(any())).thenThrow(UniqueViolationException.class);
        mvc.perform(post("/api/user/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest());
        verify(service).create(any(User.class));
    }

    @Test
    @DisplayName("Should return status 200 OK when entity exists")
    void findByIdTest() throws Exception {
        when(service.findById(user.getId())).thenReturn(user);
        mvc.perform(get("/api/user/" + user.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return status 404 Not Found when entity doesn't exist")
    void findByIdNotFoundTest() throws Exception {
        when(service.findById(user.getId())).thenThrow(ObjectNotFoundException.class);
        mvc.perform(get("/api/user/" + user.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
}