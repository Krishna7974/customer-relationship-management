package in.sk.main.repositories;

import in.sk.main.entities.FollowUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowUpRepository extends JpaRepository<FollowUp,Long> {

    Optional<FollowUp> findAllByPhoneno(String phoneno);
    List<FollowUp> findByEmployeeEmailAndFollowUpDate(String employeeEmail, String followUpDate);
}
