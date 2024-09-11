package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for IndividualProjectApplication.java.
 */
public class IndividualProjectApplicationUnitTests {
  private IndividualProjectApplication mockProj;
  private MyFileDatabase myFileDatabase;

  /**
   * Setting up to not alter data.txt.
   */
  @BeforeEach
  public void setUp() {
    myFileDatabase = new MyFileDatabase(0, "./data.txt");
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    mockProj.myFileDatabase = null;
  }

  @AfterEach
  public void cleanUp() {
    mockProj.myFileDatabase = null;
  }

  @Test
  public void runTest() {
    mockProj = new IndividualProjectApplication();
    String[] setUpArray = {"setup"};
    mockProj.run(setUpArray);
    assertNotNull(mockProj.myFileDatabase);
  }
  
}
