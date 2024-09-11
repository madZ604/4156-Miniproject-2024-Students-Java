package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Tests for class Department.java.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {
    
  public Department compSci;
  public HashMap<String, Course> courses = new HashMap<>();

  /**
   * Initial set up before each test.
   */
  @BeforeEach
  public void setupDepartmentForTesting() {
    Course coms1004 = new Course("Adam Cannon",
        "417 IAB", "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);
    Course coms3134 = new Course("Brian Borowski",
        "301 URIS", "4:10-5:25", 250);
    coms3134.setEnrolledStudentCount(242);

    courses.put("1004", coms1004);
    courses.put("3134", coms3134);

    compSci = new Department("COMS", courses,
        "Luca Carloni", 2700);
  }

  @Test
  public void createCourseTest() {
    compSci.createCourse("3157", "Jae Lee",
        "417 IAB", "4:10-5:25", 400);
    assertTrue(compSci.getCourseSelection().containsKey("3157"));
  }

  @Test
  public void toStringTest() {
    String expected = "COMS 1004: " + "\n"
        + "Instructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55"
        + "\n" + "COMS 3134: " + "\n"
        + "Instructor: Brian Borowski; Location: 301 URIS; Time: 4:10-5:25"
        + "\n";
    assertEquals(expected, compSci.toString());
  }

  @Test
  public void addPersonToMajorTest() {
    compSci.addPersonToMajor();
    assertEquals(2701, compSci.getNumberOfMajors());
  }

  @Test
  public void getDepartmentChairTest() {
    assertEquals("Luca Carloni", compSci.getDepartmentChair());
  }

  @Test
  public void dropPersonFromMajorTest() {
    compSci.dropPersonFromMajor();
    assertEquals(2699, compSci.getNumberOfMajors());
  }

}
