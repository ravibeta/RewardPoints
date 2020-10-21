package org.springframework.samples.rewardpoint.system;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ApplicationController {

    @RequestMapping("/")
    public String home() {
        return "Welcome to RewardPoint";
    }

    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

}
