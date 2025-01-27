package in.sk.main.services;

import in.sk.main.entities.FollowUp;
import in.sk.main.repositories.FollowUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowUpService {

    @Autowired
    private FollowUpRepository followUpRepository;

    public void addFollowUps(FollowUp followUp){
        Optional<FollowUp> optional=followUpRepository.findAllByPhoneno(followUp.getPhoneno());
        if(optional.isPresent()){
            FollowUp oldFollowUp=optional.get();
            oldFollowUp.setFollowUpDate(followUp.getFollowUpDate());
            followUpRepository.save(oldFollowUp);
        }
        else followUpRepository.save(followUp);
    }

    public List<FollowUp> getMyFollowUps(String empEmail,String followUpDate){
        return followUpRepository.findByEmployeeEmailAndFollowUpDate(empEmail,followUpDate);
    }

    public void deleteFollowUps(String phoneno){
        Optional<FollowUp> optionalFollowUp=followUpRepository.findAllByPhoneno(phoneno);
        if(optionalFollowUp.isPresent()){
            FollowUp followUp=optionalFollowUp.get();
            followUpRepository.delete(followUp);
        }
    }
}
