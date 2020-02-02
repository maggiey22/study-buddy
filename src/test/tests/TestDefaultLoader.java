package tests;

import model.schedule.CourseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.loader.DefaultLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestDefaultLoader {

    DefaultLoader testsl;

    // UNNEEDED TEST CLASS -- DefaultLoader is now in ui
    @BeforeEach
    public void setUp() {
        testsl = new DefaultLoader();
    }

    @Test
    public void testLoadLibraries() {
        assertEquals(5, testsl.loadLibraries().getSize());
    }

    @Test
    public void testLoadCourses() {
        CourseManager courses = testsl.loadCourses();
        assertEquals(3, courses.getSize());
        assertNotNull(courses.getCourse("MATH200"));
        assertNotNull(courses.getCourse("CPSC210"));
        assertNotNull(courses.getCourse("CHEM123"));
        // TODO finish LOC methods
    }

    @Test
    public void testLoadLearningCentres() {
        CourseManager myCourses = testsl.loadCourses();
        assertEquals(3, testsl.loadLearningCentres(myCourses).getSize());
    }

}
