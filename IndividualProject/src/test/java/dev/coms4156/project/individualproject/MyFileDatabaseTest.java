package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Tests for class MyFileDatabase.java.
 */
@SpringBootTest
@ContextConfiguration
public class MyFileDatabaseTest {
  public static String filePath = "./data.txt";
  public static HashMap<String, Department> departmentMapping = new HashMap<>();
  public MyFileDatabase myFileDatabase;

  /**
   * Set up to be run before the tests.
   */
  @BeforeAll
  public static void setup() {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

    //data for coms dept
    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);
    Course coms3134 = new Course("Brian Borowski", locations[2], times[1], 250);
    coms3134.setEnrolledStudentCount(242);
    Course coms3157 = new Course("Jae Lee", locations[0], times[1], 400);
    coms3157.setEnrolledStudentCount(311);
    Course coms3203 = new Course("Ansaf Salleb-Aouissi", locations[2], times[2], 250);
    coms3203.setEnrolledStudentCount(215);
    Course coms3261 = new Course("Josh Alman", locations[0], times[3], 150);
    coms3261.setEnrolledStudentCount(140);
    Course coms3251 = new Course("Tony Dear", "402 CHANDLER", "1:10-3:40", 125);
    coms3251.setEnrolledStudentCount(99);
    Course coms3827 = new Course("Daniel Rubenstein", "207 Math", times[2], 300);
    coms3827.setEnrolledStudentCount(283);
    Course coms4156 = new Course("Gail Kaiser", "501 NWC", times[2], 120);
    coms4156.setEnrolledStudentCount(109);
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);
    courses.put("3134", coms3134);
    courses.put("3157", coms3157);
    courses.put("3203", coms3203);
    courses.put("3261", coms3261);
    courses.put("3251", coms3251);
    courses.put("3827", coms3827);
    courses.put("4156", coms4156);
    Department compSci = new Department("COMS", courses, "Luca Carloni", 2700);
    HashMap<String, Department> mapping = new HashMap<>();
    departmentMapping.put("COMS", compSci);

    //data for econ dept
    Course econ1105 = new Course("Waseem Noor", locations[1], times[3], 210);
    econ1105.setEnrolledStudentCount(187);
    Course econ2257 = new Course("Tamrat Gashaw", "428 PUP", times[2], 125);
    econ2257.setEnrolledStudentCount(63);
    Course econ3211 = new Course("Murat Yilmaz", "310 FAY", times[1], 96);
    econ3211.setEnrolledStudentCount(81);
    Course econ3213 = new Course("Miles Leahey", "702 HAM", times[1], 86);
    econ3213.setEnrolledStudentCount(77);
    Course econ3412 = new Course("Thomas Piskula", "702 HAM", times[0], 86);
    econ3412.setEnrolledStudentCount(81);
    Course econ4415 = new Course("Evan D Sadler", locations[1], times[2], 110);
    econ4415.setEnrolledStudentCount(63);
    Course econ4710 = new Course("Matthieu Gomez", "517 HAM", "8:40-9:55", 86);
    econ4710.setEnrolledStudentCount(37);
    Course econ4840 = new Course("Mark Dean", "142 URIS", times[3], 108);
    econ4840.setEnrolledStudentCount(67);

    courses = new HashMap<>();
    courses.put("1105", econ1105);
    courses.put("2257", econ2257);
    courses.put("3211", econ3211);
    courses.put("3213", econ3213);
    courses.put("3412", econ3412);
    courses.put("4415", econ4415);
    courses.put("4710", econ4710);
    courses.put("4840", econ4840);

    Department econ = new Department("ECON", courses, "Michael Woodford", 2345);
    departmentMapping.put("ECON", econ);
  }

  @Test
  public void setMappingAndToStringTest() {
    myFileDatabase = new MyFileDatabase(1, filePath);
    myFileDatabase.setMapping(departmentMapping);
    String expectedString = 
        "For the COMS department: " + "\n"
        + "COMS 3827: " + "\n"
        + "Instructor: Daniel Rubenstein; Location: 207 Math; Time: 10:10-11:25" + "\n"
        + "COMS 1004: " + "\n"
        + "Instructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55" + "\n"
        + "COMS 3203: " + "\n"
        + "Instructor: Ansaf Salleb-Aouissi; Location: 301 URIS; Time: 10:10-11:25" + "\n"
        + "COMS 4156: " + "\n"
        + "Instructor: Gail Kaiser; Location: 501 NWC; Time: 10:10-11:25" + "\n"
        + "COMS 3157: " + "\n"
        + "Instructor: Jae Lee; Location: 417 IAB; Time: 4:10-5:25" + "\n"
        + "COMS 3134: " + "\n"
        + "Instructor: Brian Borowski; Location: 301 URIS; Time: 4:10-5:25" + "\n"
        + "COMS 3251: " + "\n"
        + "Instructor: Tony Dear; Location: 402 CHANDLER; Time: 1:10-3:40" + "\n"
        + "COMS 3261: " + "\n"
        + "Instructor: Josh Alman; Location: 417 IAB; Time: 2:40-3:55" + "\n"
        + "For the ECON department: " + "\n"
        + "ECON 1105: " + "\n"
        + "Instructor: Waseem Noor; Location: 309 HAV; Time: 2:40-3:55" + "\n"
        + "ECON 2257: " + "\n"
        + "Instructor: Tamrat Gashaw; Location: 428 PUP; Time: 10:10-11:25" + "\n"
        + "ECON 3412: " + "\n"
        + "Instructor: Thomas Piskula; Location: 702 HAM; Time: 11:40-12:55" + "\n"
        + "ECON 3213: " + "\n"
        + "Instructor: Miles Leahey; Location: 702 HAM; Time: 4:10-5:25" + "\n"
        + "ECON 3211: " + "\n"
        + "Instructor: Murat Yilmaz; Location: 310 FAY; Time: 4:10-5:25" + "\n"
        + "ECON 4840: " + "\n"
        + "Instructor: Mark Dean; Location: 142 URIS; Time: 2:40-3:55" + "\n"
        + "ECON 4710: " + "\n"
        + "Instructor: Matthieu Gomez; Location: 517 HAM; Time: 8:40-9:55" + "\n"
        + "ECON 4415: " + "\n"
        + "Instructor: Evan D Sadler; Location: 309 HAV; Time: 10:10-11:25" + "\n";
    assertEquals(expectedString, myFileDatabase.toString());
    assertTrue(departmentMapping.equals(myFileDatabase.getDepartmentMapping()));
  }



}
