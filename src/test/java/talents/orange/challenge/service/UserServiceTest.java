package talents.orange.challenge.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import talents.orange.challenge.model.User;
import talents.orange.challenge.repository.UserRepository;
import talents.orange.challenge.service.exception.UniqueViolationException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @MockBean
    private UserRepository repository;

    private UserService service;

    private User user;

    @BeforeEach
    void setUp() {
        service = new UserService(repository);
        user = User.builder()
            .id(1L)
            .name("Bruce Wayne")
            .email("bwayne@wayneenterprises.com")
            .cpf("27854636419")
            .birthday(LocalDate.of(1972, 2, 19))
            .build();
    }

    @Test
    @DisplayName("Should create entity when CPF and email are valid")
    void createTest() {
        given(repository.save(any(User.class))).willReturn(user);
        assertThat(service.save(user)).isEqualTo(user);
        verify(repository, times(1)).save(user);
    }

    @Test
    @DisplayName("Should throw exception when CPF is duplicated")
    void duplicatedCPFTest() {
        given(repository.existsByCpf(user.getCpf())).willReturn(true);
        assertThat(catchThrowable(() -> service.create(user)))
            .isInstanceOf(UniqueViolationException.class)
            .hasMessageContaining("Provided CPF already exists.");
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when email is duplicated")
    void duplicatedEmailTest() {
        given(repository.existsByEmail(user.getEmail())).willReturn(true);
        assertThat(catchThrowable(() -> service.create(user)))
            .isInstanceOf(UniqueViolationException.class)
            .hasMessageContaining("Provided email already exists.");
        verify(repository, never()).save(any());
    }
}