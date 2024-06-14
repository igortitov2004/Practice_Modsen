package com.example.practice_modsen_shop.user;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class UserJsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserJsonDataLoader.class);
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public UserJsonDataLoader(ObjectMapper objectMapper, UserRepository userRepository) {
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        if(userRepository.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/users.json")) {
                Users allRuns = objectMapper.readValue(inputStream, Users.class);
                log.info("Reading {} users from JSON data and saving to postgres.", allRuns.users().size());
                userRepository.saveAll(allRuns.users());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading Users from JSON data because the collection contains data.");
        }
    }

}
