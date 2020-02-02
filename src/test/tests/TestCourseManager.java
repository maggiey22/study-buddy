package tests;

import model.schedule.Course;
import model.schedule.CourseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static java.time.LocalTime.NOON;
import static org.junit.jupiter.api.Assertions.*;
// import static org.junit.Assert.*;

public class TestCourseManager {

    private static final Course CPSC121 = new Course("CPSC121");
    private static final Course PHYS118 = new Course("PHYS118");

    CourseManager courseList;

    @BeforeEach
    public void setUp() {
        courseList = new CourseManager();
    }

    @Test
    public void testLOCConstructor() {
        assertEquals(0, courseList.getSize());
    }

    @Test
    public void testAddCourseNull() {
        courseList.addCourse(null);
        assertFalse(courseList.containsCourse(null));
        assertEquals(0, courseList.getSize());
    }

    @Test
    public void testAddOneCourse() {
        courseList.addCourse(CPSC121);
        assertTrue(courseList.containsCourse(CPSC121));
        assertEquals(1, courseList.getSize());
    }

    @Test
    public void testAddTwoCourses() {
        courseList.addCourse(CPSC121);
        courseList.addCourse(PHYS118);
        assertTrue(courseList.containsCourse(CPSC121));
        assertTrue(courseList.containsCourse(PHYS118));
        assertEquals(2, courseList.getSize());
    }

    @Test
    public void testAddCourseAlreadyThere() {
        courseList.addCourse(PHYS118);
        courseList.addCourse(PHYS118);
        assertTrue(courseList.containsCourse(PHYS118));
        assertEquals(1, courseList.getSize());
    }

    @Test
    public void testAddCourseDuplicateByNameOnly() {
        courseList.addCourse(PHYS118);
        Course physics118 = new Course("PHYS118", NOON, LocalTime.of(13, 0), new HashSet<>());
        courseList.addCourse(physics118);
        assertTrue(courseList.containsCourse(PHYS118)); // should have only the first one we added
        assertEquals(1, courseList.getSize());
    }

    @Test
    public void testAddCourseByStringEmptyString() {
        courseList.addCourse(CPSC121);
        courseList.addCourse(PHYS118);
        courseList.addCourseByName("");
        assertFalse(courseList.getMyCourses().contains(new Course("")));
        assertTrue(courseList.getMyCourses().contains(CPSC121));
        assertTrue(courseList.getMyCourses().contains(PHYS118));
        assertEquals(2, courseList.getMyCourses().size());
    }

    @Test
    public void testAddCourseByName() {
        courseList.addCourseByName("PHYS117");
        assertEquals(1, courseList.getSize());
        assertNotNull(courseList.getCourse("PHYS117"));
    }

    @Test
    public void testAddCourseByNameDuplicate() {
        courseList.addCourseByName("CPSC210");
        courseList.addCourseByName("CPSC210");
        assertEquals(1, courseList.getSize());
        assertNotNull(courseList.getCourse("CPSC210"));
    }

    @Test
    public void testAddMultiple() {
        courseList.addCourseByName("CPSC210");
        courseList.addCourseByName("ENGL112");
        courseList.addCourseByName("CPSC210");
        courseList.addCourseByName("ENGL112");
        assertEquals(2, courseList.getSize());
        assertNotNull(courseList.getCourse("CPSC210"));
        assertNotNull(courseList.getCourse("ENGL112"));
    }

    @Test
    public void testRemoveCourseNotThere() {
        courseList.addCourse(PHYS118);
        courseList.removeCourse("CHIN100");
        assertEquals(1, courseList.getSize());
    }

    @Test
    public void testRemoveCourse() {
        courseList.addCourse(PHYS118);
        courseList.removeCourse("PHYS118");
        assertEquals(0, courseList.getSize());
        assertFalse(courseList.containsCourse(PHYS118));
    }

//    // Test obsolete - CM.printMyCourses no longer exists
//    @Test
//    public void testPrintMyCourses() {
//        fail("");
//        courseList.addCourse(CPSC121);
//        courseList.addCourse(PHYS118);
//        // assertEquals("CPSC121\nPHYS118\n", courseList.printMyCourses());
//    }

//    // Test obsolete - CM.printMyCourses no longer exists
//    @Test
//    public void testPrintMyCoursesEmpty() {
//        fail("");
//        // assertEquals("", courseList.printMyCourses());
//    }

//    // Test obsolete - CM.printScheduleForAllCourses no longer exists
//    @Test
//    public void testPrintSchedule() {
//        fail("");
//        /*courseList.addCourse(CPSC121);
//        courseList.addCourse(PHYS118);
//        assertEquals(CPSC121.getCourseCode() + ": MWF, " + CPSC121.getStartTime() + "-" + CPSC121.getEndTime() + "\n"
//                + PHYS118.getCourseCode() + ": MWF, " + PHYS118.getStartTime() + "-" + PHYS118.getEndTime() + "\n",
//                courseList.printScheduleForAllCourses());*/
//    }

//    // Test obsolete - CM.printScheduleForAllCourses no longer exists
//    @Test
//    public void testPrintScheduleEmpty() {
//        fail("");
//        // assertEquals("", courseList.printScheduleForAllCourses());
//    }

    @Test
    public void testGetCourseNull() {
        courseList.addCourse(CPSC121);
        assertTrue(courseList.getCourse("CHEM121")==null);
    }

    // FIXME is this correctly checking that it's the right one?
    @Test
    public void testGetCourseSuccess() {
        courseList.addCourse(CPSC121);
        assertEquals(CPSC121, courseList.getCourse("CPSC121"));
    }

    @Test
    public void testContainsCourseTrue() {
        courseList.addCourse(CPSC121);
        courseList.addCourse(PHYS118);
        assertTrue(courseList.containsCourse(CPSC121));
        assertTrue(courseList.containsCourse(PHYS118));
    }

    // FIXME: big implementation error: if you make a course with the given string and run containsCourse what happens?
//    @Test
//    public void testContainsCourseTrueTestByStrings() {
//
//    }

    @Test
    public void testContainsCourseFalseNull() {
        courseList.addCourse(PHYS118);
        assertFalse(courseList.containsCourse(null));
    }

    @Test
    public void testContainsCourseFalse() {
        courseList.addCourse(PHYS118);
        assertFalse(courseList.containsCourse(CPSC121));
    }

    @Test
    public void testContainsCourseFalseDiffMeetingTime() {
        courseList.addCourse(new Course("PHYS118", NOON, NOON, null));
        assertFalse(courseList.containsCourse(PHYS118));
    }

    @Test
    public void testContainsCourseByStringTrue() {
        courseList.addCourse(PHYS118);
        assertTrue(courseList.containsCourseByString("PHYS118"));
    }

    @Test
    public void testContainsCourseByStringFalse() {
        courseList.addCourse(PHYS118);
        assertFalse(courseList.containsCourseByString("CPSC110"));
    }

    @Test
    public void testSetMyCourses() {
        List<Course> list = new ArrayList<>();
        list.add(CPSC121);
        list.add(PHYS118);
        courseList.setMyCourses(list);
        assertTrue(courseList.getMyCourses().contains(CPSC121));
        assertTrue(courseList.getMyCourses().contains(PHYS118));
        assertEquals(2, courseList.getMyCourses().size());
    }
}
