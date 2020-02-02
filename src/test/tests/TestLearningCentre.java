package tests;

import model.places.LearningCentre;
import model.places.Library;
import model.places.Place;
import model.schedule.Course;
import model.schedule.TimeBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.time.DayOfWeek.*;
import static java.time.LocalTime.NOON;
import static org.junit.jupiter.api.Assertions.*;

public class TestLearningCentre { //extends tests.TestPlace {

    // FIXME: no longer really extends tests.TestPlace = duplicate tests
    private static final LocalTime ONE_PM = LocalTime.of(13, 0);

    // public final static LearningCentre DEMCO = new LearningCentre("Demco", MIDNIGHT, MIDNIGHT, cpsc210);
    private LearningCentre place;
    // FIXME: it cannot extend TestPlace anymore because LearningCentre uses a method that Place doesn't have

    public static final Course cpsc210 = new Course("CPSC210",
            LocalTime.of(12, 0),
            LocalTime.of(14, 0), new HashSet<>());
    private static final Course cpsc121 = new Course("CPSC121");

    @BeforeEach
    public void setUp() {
        place = new LearningCentre("demco",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0), new HashSet<>());
    }

    @Test
    public void testConstructor() {
        LearningCentre lc = new LearningCentre("Prof Office Hours", NOON, ONE_PM, new HashSet<>());
        assertTrue(lc.getName().equals("Prof Office Hours"));
        Map<DayOfWeek, Set<TimeBlock>> map = lc.getSchedule().getSchedule();

        assertTrue(map.containsKey(MONDAY));
        assertTrue(map.containsKey(TUESDAY));
        assertTrue(map.containsKey(WEDNESDAY));
        assertTrue(map.containsKey(THURSDAY));
        assertTrue(map.containsKey(FRIDAY));

        map.forEach((k,v) -> {
            assertEquals(1, v.size());
            assertTrue(v.contains(new TimeBlock(NOON, ONE_PM)));
        });
//        assertEquals(NOON, lc.getOpenTime());
//        assertEquals(ONE_PM, lc.getCloseTime());
        assertEquals(0, lc.getCourses().size());
    }

    // ----------------
    @Test
    public void testIsOpenTrue() {
        assertTrue(place.isOpen(MONDAY, NOON));
    }

    @Test
    public void testIsOpenFalse() {
        assertFalse(place.isOpen(FRIDAY, LocalTime.of(5, 0)));
    }

    @Test
    public void testIsOpenFalseOpeningTime() {
        assertFalse(place.isOpen(MONDAY, LocalTime.of(9, 0)));
    }

    @Test
    public void testIsOpenFalseClosingTime() {
        assertFalse(place.isOpen(WEDNESDAY, LocalTime.of(17, 0)));
    }

    @Test
    public void testIsOpenOpenCloseTimeAreSame() {
        Place p2 = new LearningCentre("abc", NOON, NOON, new HashSet<>());
        assertTrue(p2.getSchedule().getSchedule().containsKey(MONDAY));
        assertFalse(p2.isOpen(MONDAY, NOON));
        assertFalse(p2.isOpen(MONDAY, LocalTime.of(11, 0)));
        assertFalse(p2.isOpen(MONDAY, LocalTime.of(13, 0)));
    }

//    // test obsolete - Place.setOpenTime non-existent now
//    @Test
//    public void testSetOpenTime() {
//        LocalTime newOpenHours = LocalTime.of(7, 0);
//        place.setOpenTime(newOpenHours);
//        assertEquals(newOpenHours, place.getOpenTime());
//    }

//    // test obsolete - Place.setCloseTime non-existent now
//    @Test
//    public void testSetCloseTime() {
//        fail("");
//        LocalTime newCloseHours = LocalTime.of(19, 0);
//        place.setCloseTime(newCloseHours);
//        assertEquals(newCloseHours, place.getCloseTime());
//    }

    @Test
    public void testGetCoursesEmpty() {
        assertEquals(0, place.getCourses().size());
    }

    @Test
    public void testSetGetCourses() {
        Set<Course> courses = new HashSet<>();
        courses.add(cpsc210);
        courses.add(cpsc121);
        place.setCourses(courses);
        Set<Course> retrieved = place.getCourses();
        assertTrue(retrieved.contains(cpsc210));
        assertTrue(retrieved.contains(cpsc121));
        assertEquals(2, retrieved.size());
    }

    @Test
    public void testAddCourse() {
        place.addCourse(cpsc210);
        checkCpsc210AndLChaveEachOtherOnce();
    }

    @Test
    public void testAdd2Courses() {
        place.addCourse(cpsc210);
        place.addCourse(cpsc121);
        assertTrue(place.getCourses().contains(cpsc210));
        assertTrue(place.getCourses().contains(cpsc121));
        assertEquals(2, place.getCourses().size());
        assertTrue(cpsc210.getLearningCentres().contains(place));
        assertEquals(1, cpsc210.getLearningCentres().size());
        assertTrue(cpsc121.getLearningCentres().contains(place));
        assertEquals(1, cpsc121.getLearningCentres().size());
    }

    @Test
    public void testAddCourseAlreadyThere() {
        place.addCourse(cpsc210);
        place.addCourse(cpsc210);
        checkCpsc210AndLChaveEachOtherOnce();
    }


    @Test
    public void testEqualsSameRef() {
        Place p2 = place;
        assertTrue(place.equals(p2));
        assertTrue(p2.equals(place));
    }

    @Test
    public void testEqualsDiffTypes() {
        String p2 = "place";
        assertFalse(place.equals(p2));
    }

    @Test
    public void testEqualsLibraryAndLearningCentre() {
        Place p1 = new Library("abc");
        Place p2 = new LearningCentre("abc");
        assertFalse(p1.equals(p2));
        assertFalse(p2.equals(p1));
    }

    @Test
    public void testEqualsLibraryAndLearningCentreActualTypes() {
        Library p1 = new Library("abc");
        LearningCentre p2 = new LearningCentre("abc");
        assertFalse(p1.equals(p2));
        assertFalse(p2.equals(p1));
    }

    @Test
    public void testEqualsDiffName() {
        Place p1 = new LearningCentre("Abc");
        Place p2 = new LearningCentre("abc");
        assertFalse(p1.equals(p2));
        assertFalse(p2.equals(p1));
    }

    @Test
    public void testEqualsSameEverything() {
        Place p1 = new LearningCentre("abc");
        Place p2 = new LearningCentre("abc");
        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));
    }

    private void checkCpsc210AndLChaveEachOtherOnce() {
        assertTrue(place.getCourses().contains(cpsc210));
        assertEquals(1, place.getCourses().size());
        assertTrue(cpsc210.getLearningCentres().contains(place));
        assertEquals(1, cpsc210.getLearningCentres().size());
    }

}
