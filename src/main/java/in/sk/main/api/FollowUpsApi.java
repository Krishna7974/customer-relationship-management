package in.sk.main.api;

import in.sk.main.entities.FollowUp;
import in.sk.main.services.FollowUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FollowUpsApi {

    @Autowired
    private FollowUpService followUpService;

    @GetMapping("/myFollowUps")
    public ResponseEntity<List<FollowUp>> getFollowUpsCustomer(@RequestParam("empEmail") String empEmail,@RequestParam("followUpDate") String followUpDate){
        List<FollowUp> list=followUpService.getMyFollowUps(empEmail,followUpDate);
        return ResponseEntity.ok(list);
    }
}
