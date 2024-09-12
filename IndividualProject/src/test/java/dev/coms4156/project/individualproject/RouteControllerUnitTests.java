package dev.coms4156.project.individualproject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Tests for class RouteController.java.
 */
@SpringBootTest
@ContextConfiguration
public class RouteControllerUnitTests {

  private MockMvc mockMvc;
  Map<String, Department> mapping = new HashMap<>();

  /**
   * Tests for RouteController.java.
   */
  @BeforeEach
  public void setUp() {
    RouteController routeController = new RouteController();
    this.mockMvc = MockMvcBuilders.standaloneSetup(routeController).build();
    MyFileDatabase myFileDatabase = new MyFileDatabase(0, "./data.txt");
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
  }

  @Test
  public void indexTest() throws Exception {
    String expectedString = "Welcome, in order to make an API call direct your "
        + "browser or Postman to an endpoint "
        + "\n\n This can be done using the following format: \n\n http:127.0.0"
        + ".1:8080/endpoint?arg=value";

    mockMvc.perform(get("/home"))
        .andExpect(status().isOk())
        .andExpect(content().string(expectedString));
  }

  @Test
  public void retrieveCourseTestReturnsTrue() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "1004");

    mockMvc.perform(get("/retrieveCourse").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(
        "\nInstructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55"));
  }

  @Test
  public void retrieveCourseTestDeptNotFound() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "XYZ");
    courseParams.add("courseCode", "1004");

    mockMvc.perform(get("/retrieveCourse").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void retrieveCourseTestCourseNotFound() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "0");

    mockMvc.perform(get("/retrieveCourse").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void isCourseFullTestCourseNotFull() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "1004");

    mockMvc.perform(get("/isCourseFull").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("false"));
  }
   
  @Test
  public void isCourseFullTestCourseFull() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "IEOR");
    courseParams.add("courseCode", "2500");

    mockMvc.perform(get("/isCourseFull").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
  }

  @Test
  public void isCourseFullTestCourseNotFound() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "IEOR");
    courseParams.add("courseCode", "123");

    mockMvc.perform(get("/isCourseFull").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void getMajorCtFromDeptTest() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept").param("deptCode", "COMS")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("There are: 2700 majors in the department"));
  }

  @Test
  public void getMajorCtFromDeptTestDeptNotFound() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept").param("deptCode", "XYZ")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void identifyDeptChairTest() throws Exception {
    mockMvc.perform(get("/idDeptChair").param("deptCode", "COMS")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Luca Carloni is the department chair."));
  }

  @Test
  public void identifyDeptChairTestDeptNotFound() throws Exception {
    mockMvc.perform(get("/idDeptChair").param("deptCode", "XYZ")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void findCourseLocationTest() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "1004");

    mockMvc.perform(get("/findCourseLocation").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("417 IAB is where the course is located."));
  }

  @Test
  public void findCourseLocationTestCourseNotFound() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "123");

    mockMvc.perform(get("/findCourseLocation").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void findCourseInstructorTest() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "1004");

    mockMvc.perform(get("/findCourseInstructor").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Adam Cannon is the instructor for the course."));
  }

  @Test
  public void findCourseInstructorTestCourseNotFound() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "1");

    mockMvc.perform(get("/findCourseInstructor").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void findCourseTimeTest() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "1004");

    mockMvc.perform(get("/findCourseTime").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("The course meets at: 11:40-12:55 some time."));
  }

  @Test
  public void findCourseTimeTestNotFound() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "1");

    mockMvc.perform(get("/findCourseTime").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void addAndRemoveMajorToDeptTestSuccess() throws Exception {
    mockMvc.perform(patch("/addMajorToDept").param("deptCode", "COMS")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated successfully"));

    mockMvc.perform(patch("/removeMajorFromDept").param("deptCode", "COMS")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated or is at minimum"));
  }

  @Test
  public void addMajorToDeptTestDeptNotFound() throws Exception {
    mockMvc.perform(patch("/addMajorToDept").param("deptCode", "C")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void removeMajorToDeptTestDeptNotFound() throws Exception {
    mockMvc.perform(patch("/removeMajorFromDept").param("deptCode", "C")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void dropStudentTest() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "1004");

    mockMvc.perform(patch("/dropStudentFromCourse").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Student has been dropped."));
  }

  @Test
  public void dropStudentTestDeptNotFound() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "12");

    mockMvc.perform(patch("/dropStudentFromCourse").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void setEnrollmentCountTest() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "1004");
    courseParams.add("count", "200");

    mockMvc.perform(patch("/setEnrollmentCount").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  public void setEnrollmentCountTestDeptNotFound() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "12");
    courseParams.add("count", "200");

    mockMvc.perform(patch("/setEnrollmentCount").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void changeCourseTimeTest() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "1004");
    courseParams.add("time", "10:10-11:25");

    mockMvc.perform(patch("/changeCourseTime").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  public void changeCourseTimeTestDeptNotFound() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "12");
    courseParams.add("time", "10:10-11:25");

    mockMvc.perform(patch("/changeCourseTime").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void changeCourseTeacherTest() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "1004");
    courseParams.add("teacher", "New teacher");

    mockMvc.perform(patch("/changeCourseTeacher").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  public void changeCourseTeacherTestDeptNotFound() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "12");
    courseParams.add("teacher", "New teacher");

    mockMvc.perform(patch("/changeCourseTeacher").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void changeCourseLocationTest() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "1004");
    courseParams.add("location", "New Location");

    mockMvc.perform(patch("/changeCourseLocation").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  public void changeCourseLocationTestNotFound() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "12");
    courseParams.add("location", "New Location");

    mockMvc.perform(patch("/changeCourseLocation").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }
}
