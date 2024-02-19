package com.iprody.userprofile.userprofileservice.micrometer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ExampleController {
    /**
     * An Example Controller for testing Micrometer.
     * @return the status of the object
     */
    @GetMapping("/status")
    public List<String> status() {

        log.info("This is a info message");
        log.debug("This is an debug message");
        log.warn("This is a warn message");
        log.error("This is an error message");

        return List.of("hello", "world");
    }
}
