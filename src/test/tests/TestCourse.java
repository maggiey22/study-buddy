package tests;

import model.places.LearningCentre;
import model.schedule.Course;
import model.schedule.Schedule;
import model.schedule.TimeBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static java.time.DayOfWeek.*;
import static java.time.LocalTime.NOON;
import static model.schedule.Schedule.DEFAULT_TIME_BLOCK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCourse {

    private static final LearningCentre BIO_LC = new LearningCentre("Biology Learning Centre");
    private static final LearningCentre BIOL_112_OFFICE_HRS = new LearningCentre("Wesbrook 123");
    private static final LocalTime END = LocalTime.of(16, 40);

    Course testCourse;

    @BeforeEach
    // default course
    public void setUp() {
        testCourse = new Course("BIOL112", NOON, END, new HashSet<>());
    }

    @Test
    public void testCourseConstructorGivenTimes() {
        /*testCourse = new Course("BIOL112", LocalTime.of(9, 0),
                LocalTime.of(10, 0));*/
        //assertEquals(LocalTime.of(9, 0), testCourse.getStartTime());
        //assertEquals(LocalTime.of(10, 0), testCourse.getEndTime());
        assertEquals(Schedule.generateMWFschedule(NOON, END), testCourse.getSchedule());
        assertEquals(3, testCourse.getSchedule().getSchedule().size());
        assertEquals(1, testCourse.getSchedule().getTimeBlocksForDay(MONDAY).size());
        assertEquals(1, testCourse.getSchedule().getTimeBlocksForDay(WEDNESDAY).size());
        assertEquals(1, testCourse.getSchedule().getTimeBlocksForDay(FRIDAY).size());
        TimeBlock expected = new TimeBlock(NOON, END);
        assertTrue(testCourse.getSchedule().getTimeBlocksForDay(MONDAY).contains(expected));
        assertTrue(testCourse.getSchedule().getTimeBlocksForDay(WEDNESDAY).contains(expected));
        assertTrue(testCourse.getSchedule().getTimeBlocksForDay(FRIDAY).contains(expected));
        assertEquals(0, testCourse.getLearningCentres().size());
        assertEquals("BIOL112", testCourse.getCourseCode());
    }

    @Test
    public void testConstructorGivenCodeOnly() {
        Course namedCourse = new Course("ENGL110");
        assertEquals("ENGL110", namedCourse.getCourseCode());
        assertEquals(Schedule.generateDefaultMWFschedule(), namedCourse.getSchedule());
        assertEquals(Schedule.generateMWFschedule(NOON, END), testCourse.getSchedule());
        assertEquals(3, namedCourse.getSchedule().getSchedule().size());
        assertEquals(1, namedCourse.getSchedule().getTimeBlocksForDay(MONDAY).size());
        assertEquals(1, namedCourse.getSchedule().getTimeBlocksForDay(WEDNESDAY).size());
        assertEquals(1, namedCourse.getSchedule().getTimeBlocksForDay(FRIDAY).size());
        assertTrue(namedCourse.getSchedule().getTimeBlocksForDay(MONDAY).contains(DEFAULT_TIME_BLOCK));
        assertTrue(namedCourse.getSchedule().getTimeBlocksForDay(WEDNESDAY).contains(DEFAULT_TIME_BLOCK));
        assertTrue(namedCourse.getSchedule().getTimeBlocksForDay(FRIDAY).contains(DEFAULT_TIME_BLOCK));
        assertEquals(0, namedCourse.getLearningCentres().size());
    }

    @Test
    public void testGetCourseCode() {
        assertEquals("BIOL112", testCourse.getCourseCode());
    }

    @Test
    public void testGetSchedule() {
        Schedule expectedSchedule = Schedule.generateMWFschedule(NOON, END);
        assertEquals(expectedSchedule, testCourse.getSchedule());
    }

    @Test
    public void testSetSchedule() {
        testCourse.setSchedule(Schedule.generateTThSchedule(NOON, LocalTime.of(13, 30)));
        assertEquals(Schedule.generateTThSchedule(NOON, LocalTime.of(13, 30)), testCourse.getSchedule());
    }

    @Test
    public void testSetLearningCentres() {
        Set<LearningCentre> lcs = new HashSet<>();
        LearningCentre lc1 = new LearningCentre("BIOL111 Office hrs");
        LearningCentre lc2 = new LearningCentre("demco");
        lcs.add(lc1);
        lcs.add(lc2);
        testCourse.setLearningCentres(lcs);
        assertEquals(2, testCourse.getLearningCentres().size());
        assertTrue(testCourse.getLearningCentres().contains(lc1));
        assertTrue(testCourse.getLearningCentres().contains(lc2));
        assertEquals(lcs, testCourse.getLearningCentres());
    }

//    @Test
//    public void testSetStartTime() {
//        fail("");
//
//        //testCourse.setStartTime(LocalTime.of(9, 0));
//        //assertEquals(LocalTime.of(9, 0), testCourse.getStartTime());
//    }
//
//    @Test
//    public void testSetEndTime() {
//        fail("");
//        //testCourse.setEndTime(LocalTime.of(12, 0));
//        //assertEquals(LocalTime.of(12, 0), testCourse.getEndTime());
//    }

    @Test
    public void testGetLCsEmpty() {
        assertEquals(0, testCourse.getLearningCentres().size());
    }

    @Test
    public void testSetGetLCs() {
        Set<LearningCentre> lcs = new HashSet<>();
        lcs.add(BIO_LC);
        lcs.add(BIOL_112_OFFICE_HRS);
        testCourse.setLearningCentres(lcs);
        Set<LearningCentre> retrieved = testCourse.getLearningCentres();
        assertTrue(retrieved.contains(BIO_LC));
        assertTrue(retrieved.contains(BIOL_112_OFFICE_HRS));
        assertEquals(2, retrieved.size());
    }

    @Test
    public void testAddLC() {
        testCourse.addLC(BIO_LC);
        checkCourseAndBioLCHaveEachOtherOnce();
    }

    @Test
    public void testAdd2LC() {
        testCourse.addLC(BIO_LC);
        testCourse.addLC(BIOL_112_OFFICE_HRS);
        assertTrue(testCourse.getLearningCentres().contains(BIO_LC));
        assertTrue(testCourse.getLearningCentres().contains(BIOL_112_OFFICE_HRS));
        assertEquals(2, testCourse.getLearningCentres().size());
        assertTrue(BIO_LC.getCourses().contains(testCourse));
        assertEquals(1, BIO_LC.getCourses().size());
        assertTrue(BIOL_112_OFFICE_HRS.getCourses().contains(testCourse));
        assertEquals(1, BIOL_112_OFFICE_HRS.getCourses().size());
    }

    @Test
    public void testAddLCAlreadyThere() {
        testCourse.addLC(BIO_LC);
        testCourse.addLC(BIO_LC);
        checkCourseAndBioLCHaveEachOtherOnce();
    }

    @Test
    public void testToString() {
        assertEquals("Course{courseCode='BIOL112'\n" +
                "\t, learningCentres=}", testCourse.toString());
    }

    private void checkCourseAndBioLCHaveEachOtherOnce() {
        assertTrue(testCourse.getLearningCentres().contains(BIO_LC));
        assertEquals(1, testCourse.getLearningCentres().size());
        assertTrue(BIO_LC.getCourses().contains(testCourse));
        assertEquals(1, BIO_LC.getCourses().size());
    }
}
