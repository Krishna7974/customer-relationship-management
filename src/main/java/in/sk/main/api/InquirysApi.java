package in.sk.main.api;

import in.sk.main.entities.Inquiry;
import in.sk.main.services.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InquirysApi {

    @Autowired
    private InquiryService inquiryService;

    @GetMapping("/findInquiry")
    public List<Inquiry> searchInquirys(@RequestParam("phoneNo") String phoneno){
        return inquiryService.findInquiriesByPhoneno(phoneno);
    }
}
