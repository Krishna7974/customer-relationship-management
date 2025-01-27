package in.sk.main.controller;

import in.sk.main.entities.Course;
import in.sk.main.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class CourseController {

    private String UPLOAD_DIR="src/main/resources/static/uploads/";
    private String IMAGE_URL="http://localhost:8080/uploads/";

    @Autowired
    private CourseService courseService;

    @GetMapping("/manageCourse")
    public String handleManageCourse(Model model,
                                     @RequestParam(name = "page",defaultValue = "0") int page,
                                     @RequestParam(name = "size",defaultValue = "4") int size )
    {

        Pageable pageable= PageRequest.of(page,size);
        Page<Course> list=courseService.getAllCourseByPagination(pageable);
        model.addAttribute("coursePage",list);
        return "manage-course";
    }

//   ----------------Add Course start--------------------------------

    @GetMapping("/addCourse")
    public String openAddCoursePage(Model model){
        model.addAttribute("course",new Course());
        return "add-course";
    }

    @PostMapping("/addCourseForm")
    public String handleAddCourseForm(@ModelAttribute("course") Course course, @RequestParam("courseImg") MultipartFile courseImage, Model model){

        try {
            courseService.addCourse(course,courseImage);
            model.addAttribute("successMsg","Course added successfully");
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMsg","Course not added due to some error");
        }
        return "add-course";
    }

    //   ----------------Add Course ends--------------------------------

    @GetMapping("/editCourse")
    public String handleEditCourse(@RequestParam("courseName") String courseName,Model model){
        Course course=courseService.findCourseByName(courseName);
        model.addAttribute("course",course);
        model.addAttribute("newCourseObj",new Course());
        return "edit-course";
    }

    @PostMapping("/editCourseForm")
    public String editCourseForm(@ModelAttribute("newCourseObj") Course newCourseObj, @RequestParam("courseImg") MultipartFile courseImg, RedirectAttributes redirectAttributes){

        try{
            Course oldCourseObj=courseService.findCourseByName(newCourseObj.getName());
            newCourseObj.setId(oldCourseObj.getId());


            if(!courseImg.isEmpty()){
                String imgName=courseImg.getOriginalFilename();
                Path imgPath= Paths.get(UPLOAD_DIR+imgName);
                Files.write(imgPath,courseImg.getBytes());

                String imgUrl=IMAGE_URL+imgName;
                newCourseObj.setImageUrl(imgUrl);

            }else{
                newCourseObj.setImageUrl(oldCourseObj.getImageUrl());
            }
            courseService.updateCourseDetails(newCourseObj);
            redirectAttributes.addFlashAttribute("successMsg","Course updated successfully");

        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMsg","Course not updated due to some error");
            e.printStackTrace();
        }

        return "redirect:/manageCourse";
    }

    @GetMapping("/deleteCourseDetails")
    public String handleDeleteCourseDetails(@RequestParam("courseName") String courseName,RedirectAttributes redirectAttributes){
        try {
            courseService.deleteCourseDetails(courseName);
            redirectAttributes.addFlashAttribute("successMsg","Course deleted successfully");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMsg","Course not deleted due to some error");
            e.printStackTrace();
        }
        return "redirect:/manageCourse";
    }

}
