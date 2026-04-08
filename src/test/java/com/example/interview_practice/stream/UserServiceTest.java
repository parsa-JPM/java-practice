package com.example.interview_practice.stream;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class UserServiceTest {

    private final List<User> users = List.of(
            new User(1L, "Alice", "alice@x.com", true, "ADMIN"),
            new User(4L, "Dave", "dave@x.com", true, "ADMIN"),
            new User(3L, "Carol", "carol@x.com", true, "USER"),
            new User(2L, "Bob", "bob@x.com", false, "USER"),
            new User(5L, "Eve", "eve@x.com", false, "USER")
    );


    private final UserService userService = new UserService();

    //Return a list of email addresses belonging only to active users, sorted alphabetically.
    @Test
    void emailOfActiveUsers() {
        assertThat(userService.emailOfActiveUsers(users))
                .hasSize(3)
                .containsExactlyInAnyOrder("alice@x.com", "carol@x.com", "dave@x.com");
    }

    // Produce a Map<String, Long> where the key is the role and the value is the count of users with that role.
    // sorted descending
    @Test
    void mapOfRoleWithUsers() {
        assertThat(userService.roleUsersMap(users))
                .hasSize(2)
                .containsExactlyInAnyOrderEntriesOf(Map.of("USER", 3L, "ADMIN", 2L));
    }


    @Test
    void test(){
        var result = users.stream()
                .sorted(Comparator.comparing(User::name))
                .map(User::name)
                .toList();

        System.out.println(result);
    }

}
