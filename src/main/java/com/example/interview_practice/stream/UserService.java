package com.example.interview_practice.stream;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserService {

    public List<String> emailOfActiveUsers(List<User> users) {
        List<String> emails = users.stream()
                .filter(User::active)
                .map(User::email)
                .toList();

        return emails;
    }


    public Map<String, Long> roleUsersMap(List<User> users) {
        Map<String, Long> activeCountByRole = users.stream()
                .collect(Collectors.groupingBy(User::role, Collectors.counting()));


        return activeCountByRole.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        /*
                          It's a merge function — it tells the collector what to do if two entries have the same key.
                          In your case it should never trigger because map keys are already unique (each role appears once).
                          But Collectors.toMap requires it when you provide a map factory (LinkedHashMap::new) — it's just a mandatory parameter.
                         */
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }



}
