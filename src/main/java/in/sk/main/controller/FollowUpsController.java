package in.sk.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("sessionEmployee")
public class FollowUpsController {

    @GetMapping("/followUps")
    public String opneFollowUpsPage(){
        return "follow-ups";
    }

}
