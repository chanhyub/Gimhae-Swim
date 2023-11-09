package com.alijas.gimhaeswim.repository;

import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.common.enums.RoleType;
import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.module.user.enums.Gender;
import com.alijas.gimhaeswim.module.user.enums.UserStatus;
import com.alijas.gimhaeswim.module.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        setUp(
                "Jorge",
                "1234",
                "1999-10-28",
                "010-8338-4583",
                "test1234@test.com",
                Gender.M,
                true,
                UserStatus.ACTIVE,
                ApplyStatus.APPROVED,
                RoleType.USER
        );
    }

    @Test
    public void selectAll() {
        var users = userRepository.findAll();
        assertNotEquals(users.size(), 0);

        assertEquals(users.get(0).getUsername(), "Jorge");
        assertEquals(users.get(0).getPassword(), "1234");
        assertEquals(users.get(0).getBirthday(), "1999-10-28");
        assertEquals(users.get(0).getPhoneNumber(), "010-8338-4583");
        assertEquals(users.get(0).getEmail(), "test1234@test.com");
        assertEquals(users.get(0).getGender(), Gender.M);
        assertTrue(users.get(0).isAgree());
        assertEquals(users.get(0).getStatus(), UserStatus.ACTIVE);
        assertEquals(users.get(0).getApplyStatus(), ApplyStatus.APPROVED);
        assertEquals(users.get(0).getRole(), RoleType.USER);
    }

    @Test
    public void selectAndUpdate() {
        //given
        var optionalUser = this.userRepository.findById(1L);

        if (optionalUser.isPresent()) {
            var result = optionalUser.get();

            assertEquals(result.getUsername(), "Jorge");
            assertEquals(result.getPassword(), "1234");
            assertEquals(result.getBirthday(), "1999-10-28");
            assertEquals(result.getPhoneNumber(), "010-8338-4583");
            assertEquals(result.getEmail(), "test1234@test.com");
            assertEquals(result.getGender(), Gender.M);
            assertTrue(result.isAgree());
            assertEquals(result.getStatus(), UserStatus.ACTIVE);
            assertEquals(result.getApplyStatus(), ApplyStatus.APPROVED);
            assertEquals(result.getRole(), RoleType.USER);

            //when
            String password = "4312";
            result.setPassword(password);
            User merge = entityManager.merge(result);

            //then
            assertEquals(merge.getPassword(), "4312");
        }
    }

    @Test
    public void insertAndDelete() {
        //given
        var user = setUp(
                "Jorge",
                "1234",
                "1999-10-28",
                "010-8338-4583",
                "test1234@test.com",
                Gender.M,
                true,
                UserStatus.ACTIVE,
                ApplyStatus.APPROVED,
                RoleType.USER
        );

        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            User result = optionalUser.get();
            assertEquals(result.getUsername(), "Jorge");
            assertEquals(result.getPassword(), "1234");
            assertEquals(result.getBirthday(), "1999-10-28");
            assertEquals(result.getPhoneNumber(), "010-8338-4583");
            assertEquals(result.getEmail(), "test1234@test.com");
            assertEquals(result.getGender(), Gender.M);
            assertTrue(result.isAgree());
            assertEquals(result.getStatus(), UserStatus.ACTIVE);
            assertEquals(result.getApplyStatus(), ApplyStatus.APPROVED);
            assertEquals(result.getRole(), RoleType.USER);

            entityManager.remove(result);
            Optional<User> deleteUser = userRepository.findById(user.getId());

            deleteUser.ifPresent(Assertions::assertNull);
        }
    }


    public User setUp(
            String username,
            String password,
            String birthday,
            String phoneNumber,
            String email,
            Gender gender,
            boolean isAgree,
            UserStatus status,
            ApplyStatus applyStatus,
            RoleType role
    ) {
        return entityManager.persist(
                new User(
                        null,
                        username,
                        password,
                        birthday,
                        phoneNumber,
                        email,
                        gender,
                        isAgree,
                        status,
                        applyStatus,
                        role
                )
        );
    }


}
