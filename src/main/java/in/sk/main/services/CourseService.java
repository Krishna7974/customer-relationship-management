package in.sk.main.services;

import in.sk.main.entities.Course;
import in.sk.main.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    private String UPLOAD_DIR="src/main/resources/static/uploads/";
    private String IMAGE_URL="https://customer-relationship-management-l91f.onrender.com/uploads/";

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourse(){
        return courseRepository.findAll();
    }

    public Page<Course> getAllCourseByPagination(Pageable pageable){
        return courseRepository.findAll(pageable);
    }

    public void addCourse(Course course, MultipartFile imgFile) throws IOException {

        String imgName=imgFile.getOriginalFilename();
        Path imgPath= Paths.get(UPLOAD_DIR+imgName);
        Files.write(imgPath,imgFile.getBytes());

        String imgUrl=IMAGE_URL+imgName;
        course.setImageUrl(imgUrl);

        courseRepository.save(course);
    }

    public Course findCourseByName(String courseName){
        return courseRepository.findByName(courseName);
    }

    public void updateCourseDetails(Course course){
        courseRepository.save(course);
    }

    public void deleteCourseDetails(String courseName){
        Course course=courseRepository.findByName(courseName);
        if(course!=null){
            courseRepository.delete(course);
        }else {
            throw new RuntimeException("Course not found for this name "+courseName);
        }
    }

    public List<String> getAllCourseName(){
        List<Course> courseList=courseRepository.findAll();
        List<String> courseNameList=new ArrayList<>();

        for (Course course:courseList){
            courseNameList.add(course.getName());
        }
        return courseNameList;
    }
}
