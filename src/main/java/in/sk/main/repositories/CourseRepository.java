package in.sk.main.repositories;

import in.sk.main.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    public Course findByName(String courseName);
}
