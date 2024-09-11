package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.apache.tomcat.util.http.fileupload.MultipartStream.IllegalBoundaryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
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
  private RouteController routeController;
  HashMap<String, Department> mapping = new HashMap<>();

  @BeforeEach
  public void setUp() {
    routeController = new RouteController();
    this.mockMvc = MockMvcBuilders.standaloneSetup(routeController).build();
    // https://docs.spring.io/spring-framework/reference/testing/spring-mvc-test-framework
    // /server-setup-options.html
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
  public void retrieveCourseTest_True() throws Exception {
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
  public void retrieveCourseTest_DeptNotFound() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "XYZ");
    courseParams.add("courseCode", "1004");

    mockMvc.perform(get("/retrieveCourse").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void retrieveCourseTest_CourseNotFound() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "0");

    mockMvc.perform(get("/retrieveCourse").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void isCourseFullTest_CourseNotFull() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "1004");

    mockMvc.perform(get("/isCourseFull").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("false"));
  }
   
  @Test
  public void isCourseFullTest_CourseFull() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "IEOR");
    courseParams.add("courseCode", "2500");

    mockMvc.perform(get("/isCourseFull").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
  }

  @Test
  public void isCourseFullTest_CourseNotFound() throws Exception {
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
  public void getMajorCtFromDeptTest_DeptNotFound() throws Exception {
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
  public void identifyDeptChairTest_DeptNotFound() throws Exception {
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
  public void findCourseLocationTest_CourseNotFound() throws Exception {
    MultiValueMap<String, String> courseParams = new LinkedMultiValueMap<>();
    courseParams.add("deptCode", "COMS");
    courseParams.add("courseCode", "123");

    mockMvc.perform(get("/findCourseLocation").params(courseParams)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

}
