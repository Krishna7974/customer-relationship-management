package in.sk.main.repositories;

import in.sk.main.entities.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry,Long> {

    List<Inquiry> findAllByPhoneno(String phoneNo);
}
