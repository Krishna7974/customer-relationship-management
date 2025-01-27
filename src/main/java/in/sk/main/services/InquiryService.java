package in.sk.main.services;

import in.sk.main.entities.Inquiry;
import in.sk.main.repositories.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InquiryService {

    @Autowired
    private InquiryRepository inquiryRepository;

    public void addNewInquiry(Inquiry inquiry){
        inquiryRepository.save(inquiry);
    }

    public List<Inquiry> findInquiriesByPhoneno(String phoneno){
        return inquiryRepository.findAllByPhoneno(phoneno);
    }
}
