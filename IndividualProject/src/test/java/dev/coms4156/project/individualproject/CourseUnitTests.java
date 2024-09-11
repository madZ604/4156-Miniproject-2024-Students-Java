package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Tests for class Course.java.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }


  @Test
  public void enrollStudentTest() {
    assertFalse(testCourse.enrollStudent());
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 550);
    assertTrue(testCourse.enrollStudent());
  }

  @Test
  public void dropStudentTest() {
    assertTrue(testCourse.dropStudent());
    testCourse.setEnrolledStudentCount(0);
    assertFalse(testCourse.dropStudent());
  }


  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }


  @Test
  public void getCourseLocationTest() {
    String expectedResult = "417 IAB";
    assertEquals(expectedResult, testCourse.getCourseLocation());
  }


  @Test
  public void getInstructorNameTest() {
    String expectedResult = "Griffin Newbold";
    assertEquals(expectedResult, testCourse.getInstructorName());
  }


  @Test
  public void getCourseTimeSlotTest() {
    String expectedResult = "11:40-12:55";
    assertEquals(expectedResult, testCourse.getCourseTimeSlot());
  }

  @Test
  public void isCourseFullTest() {
    assertTrue(testCourse.isCourseFull());
  }


  @Test
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("New Instructor");
    assertEquals("New Instructor", testCourse.getInstructorName());
  }


  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("New Location");
    assertEquals("New Location", testCourse.getCourseLocation());
  }


  @Test
  public void reassignTimeTest() {
    testCourse.reassignTime("New Time");
    assertEquals("New Time", testCourse.getCourseTimeSlot());
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}

