package tests;

import model.schedule.Schedule;
import model.schedule.TimeBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static java.time.DayOfWeek.*;
import static java.time.LocalTime.*;
import static model.schedule.Schedule.DEFAULT_TIME_BLOCK;
import static org.junit.jupiter.api.Assertions.*;

public class TestSchedule {

    private static final LocalTime START = LocalTime.of(9, 0);

    Schedule sc;

    @BeforeEach
    public void setUp() {
        sc = new Schedule();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, sc.getSchedule().size());
    }

    @Test
    public void testGenerateDefaultMWFschedule() {
        sc = Schedule.generateDefaultMWFschedule();
        assertEquals(3, sc.getSchedule().size());
        Set<TimeBlock> expectedSessions = new HashSet<>();
        expectedSessions.add(DEFAULT_TIME_BLOCK);
        checkMWFhaveExpectedSessions(expectedSessions);
    }

    @Test
    public void testGenerateDefaultTThSchedule() {
        sc = Schedule.generateDefaultTThSchedule();
        assertEquals(2, sc.getSchedule().size());
        Set<TimeBlock> expectedSessions = new HashSet<>();
        expectedSessions.add(DEFAULT_TIME_BLOCK);
        checkTThHaveExpectedSessions(expectedSessions);
    }

    @Test
    public void testGenerateMWFschedule() {
        sc = Schedule.generateMWFschedule(START, NOON);
        assertEquals(3, sc.getSchedule().size());
        Set<TimeBlock> expectedSessions = new HashSet<>();
        TimeBlock tb = new TimeBlock(START, NOON);
        expectedSessions.add(tb);
        checkMWFhaveExpectedSessions(expectedSessions);
    }

    @Test
    public void testGenerateTThSchedule() {
        sc = Schedule.generateTThSchedule(START, NOON);
        assertEquals(2, sc.getSchedule().size());
        Set<TimeBlock> expectedSessions = new HashSet<>();
        TimeBlock tb = new TimeBlock(START, NOON);
        expectedSessions.add(tb);
        checkTThHaveExpectedSessions(expectedSessions);
    }

    @Test
    public void testGetTimeBlockByDayStartEndNull() {
        TimeBlock testTB = sc.getTimeBlockByDayStartEnd(MONDAY, NOON, MIDNIGHT);
        assertNull(testTB);
    }

    @Test
    public void testGetTimeBlockByDayStartEnd() {
        sc.addNewDayAndMeetingTime(FRIDAY, NOON, MIDNIGHT);
        sc.addNewDayAndMeetingTime(WEDNESDAY, NOON, MIDNIGHT);
        TimeBlock testTB = sc.getTimeBlockByDayStartEnd(FRIDAY, NOON, MIDNIGHT);
        assertEquals(new TimeBlock(NOON, MIDNIGHT), testTB);
    }

    @Test
    public void testGetTimeBlockByDayStartEndNotFound() {
        sc.addNewDayAndMeetingTime(FRIDAY, NOON, MIDNIGHT);
        TimeBlock testTB = sc.getTimeBlockByDayStartEnd(FRIDAY, MIDNIGHT, MIDNIGHT);
        assertNull(testTB);
    }

    @Test
    public void testSetSchedule() {
        HashMap<DayOfWeek, Set<TimeBlock>> testS = new HashMap<>();
        Set<TimeBlock> sessions = new HashSet<>();
        sessions.add(new TimeBlock(MIDNIGHT, NOON));
        testS.put(SATURDAY, sessions);
        sc.setSchedule(testS);
        assertEquals(testS, sc.getSchedule());
    }

    @Test
    public void testRemoveDay() {
        sc.addNewDayAndMeetingTime(MONDAY, NOON, MIDNIGHT);
        assertEquals(1, sc.getSchedule().size());
        Set<TimeBlock> expectedSessions = new HashSet<>();
        expectedSessions.add(new TimeBlock(NOON, MIDNIGHT));
        assertEquals(expectedSessions, sc.getTimeBlocksForDay(MONDAY));
        sc.removeDay(MONDAY);
        assertEquals(0, sc.getSchedule().size());
        assertNull(sc.getTimeBlocksForDay(MONDAY));
    }

    @Test
    public void testRemoveDayNotThere() {
        sc.addNewDayAndMeetingTime(MONDAY, NOON, MIDNIGHT);
        assertEquals(1, sc.getSchedule().size());
        Set<TimeBlock> expectedSessions = new HashSet<>();
        expectedSessions.add(new TimeBlock(NOON, MIDNIGHT));
        assertEquals(expectedSessions, sc.getTimeBlocksForDay(MONDAY));
        sc.removeDay(TUESDAY);
        assertEquals(1, sc.getSchedule().size());
        assertEquals(expectedSessions, sc.getTimeBlocksForDay(MONDAY));
    }

    @Test
    public void testAddDayAndMeetingTime() {
        assertTrue(sc.addNewDayAndMeetingTime(MONDAY, NOON, MIDNIGHT));
        assertEquals(1, sc.getSchedule().size());
        assertTrue(sc.getTimeBlocksForDay(MONDAY).contains(new TimeBlock(NOON, MIDNIGHT)));
    }

    @Test
    public void testAddDayAndMeetingTimeDayAlreadyThere() {
        assertTrue(sc.addNewDayAndMeetingTime(MONDAY, NOON, MIDNIGHT));
        assertFalse(sc.addNewDayAndMeetingTime(MONDAY, START, NOON));
        assertEquals(1, sc.getSchedule().size());
        assertTrue(sc.getTimeBlocksForDay(MONDAY).contains(new TimeBlock(NOON, MIDNIGHT)));
    }

    @Test
    public void testAddMeetingTime() {
        sc.addNewDayAndMeetingTime(MONDAY, NOON, MIDNIGHT);
        assertTrue(sc.addMeetingTimeUnderExistingDay(MONDAY, START, NOON));
        assertEquals(1, sc.getSchedule().size());
        assertTrue(sc.getTimeBlocksForDay(MONDAY).contains(new TimeBlock(START, NOON)));
    }

    @Test
    public void testAddMeetingTimeAlreadyThere() {
        sc.addNewDayAndMeetingTime(MONDAY, NOON, MIDNIGHT);
        assertTrue(sc.addMeetingTimeUnderExistingDay(MONDAY, START, NOON));
        assertFalse(sc.addMeetingTimeUnderExistingDay(MONDAY, START, NOON));
        assertEquals(1, sc.getSchedule().size());
        assertTrue(sc.getTimeBlocksForDay(MONDAY).contains(new TimeBlock(START, NOON)));
    }

    @Test
    public void testAddMeetingTimeDayNotThere() {
        assertFalse(sc.addMeetingTimeUnderExistingDay(MONDAY, START, NOON));
        assertEquals(0, sc.getSchedule().size());
    }

    @Test
    public void testSetMeetingTime() {
        sc.addNewDayAndMeetingTime(MONDAY, START, NOON);
        sc.addNewDayAndMeetingTime(TUESDAY, START, NOON);
        sc.addNewDayAndMeetingTime(FRIDAY, START, NOON);
        assertTrue(sc.setMeetingTime(MONDAY, START, NOON,
                LocalTime.of(8, 0), LocalTime.of(10, 0)));
        assertTrue(sc.isSessionUnderDay(MONDAY,
                new TimeBlock(LocalTime.of(8, 0), LocalTime.of(10, 0))));
        assertEquals(1, sc.getTimeBlocksForDay(MONDAY).size());
        assertTrue(sc.isSessionUnderDay(TUESDAY, new TimeBlock(START, NOON)));
        assertEquals(1, sc.getTimeBlocksForDay(TUESDAY).size());
        assertTrue(sc.isSessionUnderDay(FRIDAY, new TimeBlock(START, NOON)));
        assertEquals(1, sc.getTimeBlocksForDay(FRIDAY).size());
    }

    @Test
    public void testSetMeetingTimeMultiple() {
        sc.addNewDayAndMeetingTime(MONDAY, NOON, MIDNIGHT);
        sc.addMeetingTimeUnderExistingDay(MONDAY, START, NOON);
        sc.addNewDayAndMeetingTime(TUESDAY, NOON, MIDNIGHT);
        sc.addMeetingTimeUnderExistingDay(TUESDAY, START, NOON);
        sc.addNewDayAndMeetingTime(FRIDAY, NOON, MIDNIGHT);
        sc.addMeetingTimeUnderExistingDay(FRIDAY, START, NOON);
        assertTrue(sc.setMeetingTime(MONDAY, START, NOON,
                LocalTime.of(8, 0), LocalTime.of(10, 0)));
        assertTrue(sc.isSessionUnderDay(MONDAY, new TimeBlock(NOON, MIDNIGHT)));
        assertTrue(sc.isSessionUnderDay(MONDAY,
                new TimeBlock(LocalTime.of(8, 0), LocalTime.of(10, 0))));
        assertEquals(2, sc.getTimeBlocksForDay(MONDAY).size());
        assertTrue(sc.isSessionUnderDay(TUESDAY, new TimeBlock(START, NOON)));
    }

    @Test
    public void testSetMeetingTimeDayNotThere() {
        assertFalse(sc.setMeetingTime(MONDAY, NOON, MIDNIGHT, LocalTime.of(9, 0), LocalTime.of(13, 0)));
        assertEquals(0, sc.getSchedule().size());
    }

    @Test
    public void testIsSessionUnderDayTrue() {
        sc.addNewDayAndMeetingTime(MONDAY, NOON, MIDNIGHT);
        TimeBlock tb = new TimeBlock(NOON, MIDNIGHT);
        assertTrue(sc.isSessionUnderDay(MONDAY, tb));
    }

    @Test
    public void testIsSessionUnderDayNotThereFalse() {
        sc.addNewDayAndMeetingTime(MONDAY, NOON, MIDNIGHT);
        TimeBlock tb = new TimeBlock(NOON, LocalTime.of(13, 0));
        assertFalse(sc.isSessionUnderDay(MONDAY, tb));
    }

    @Test
    public void testIsSessionUnderDayNoSessionsUnderDayFalse() {
        sc.addNewDayAndMeetingTime(MONDAY, NOON, MIDNIGHT);
        TimeBlock tb = new TimeBlock(NOON, MIDNIGHT);
        assertFalse(sc.isSessionUnderDay(TUESDAY, tb));
    }

    @Test
    public void testIsTimeDuringSessionNoSessionsUnderDay() {
        assertFalse(sc.isTimeDuringASession(MONDAY, NOON));
    }

    @Test
    public void testIsTimeDuringSessionThere() {
        sc.addNewDayAndMeetingTime(MONDAY, NOON, MAX);
        assertTrue(sc.isTimeDuringASession(MONDAY, LocalTime.of(12, 1)));
    }

    @Test
    public void testIsTimeDuringSessionMidnight() {
        sc.addNewDayAndMeetingTime(MONDAY, NOON, LocalTime.of(1, 0));
        assertTrue(sc.isTimeDuringASession(MONDAY, MIDNIGHT));
    }

    @Test
    public void testIsTimeDuringSessionNotThere() {
        sc.addNewDayAndMeetingTime(TUESDAY, NOON, LocalTime.of(15, 0));
        assertFalse(sc.isTimeDuringASession(TUESDAY, LocalTime.of(16, 0)));
    }

//    // printing is now in ui, test not needed
//    @Test
//    public void testPrintScheduleNone() {
//        fail(""); // printing is in ui, test not needed
//        //assertEquals("", sc.printSchedule());
//    }

//    // printing is now in ui, test not needed
//    @Test
//    public void testPrintSchedule() {
//        fail("");
//        sc.addNewDayAndMeetingTime(TUESDAY, NOON, LocalTime.of(15, 0));
//        sc.addNewDayAndMeetingTime(MONDAY, NOON, MIDNIGHT);
//        sc.addMeetingTimeUnderExistingDay(TUESDAY, START, LocalTime.of(23, 59));
//        sc.addMeetingTimeUnderExistingDay(MONDAY, START, NOON);
//        sc.addNewDayAndMeetingTime(WEDNESDAY, NOON, NOON);
//        sc.addNewDayAndMeetingTime(THURSDAY, START, MIDNIGHT);
//        sc.addNewDayAndMeetingTime(FRIDAY, START, NOON);
//        /*assertEquals("MONDAY:\n09:00-12:00\n12:00-00:00\nTUESDAY:\n12:00-15:00\n09:00-23:59"
//                        + "\nWEDNESDAY:\n12:00-12:00\nTHURSDAY:\n09:00-00:00\nFRIDAY:\n09:00-12:00",
//                sc.printSchedule());*/
//    }

    @Test
    public void testAllSessionsForDayToStringNone() {
        assertEquals("", sc.allSessionsForDayToString(MONDAY));
    }

    @Test
    public void testAllSessionsForDayToString() {
        sc.addNewDayAndMeetingTime(MONDAY, NOON, MIDNIGHT);
        sc.addMeetingTimeUnderExistingDay(MONDAY, START, NOON);
        // how would I know what order it's in?
        assertEquals("\n\t09:00-12:00\n\t12:00-00:00", sc.allSessionsForDayToString(MONDAY));
    }

    @Test
    public void testIsEqualsSameRef() {
        sc.addNewDayAndMeetingTime(MONDAY, NOON, MIDNIGHT);
        Schedule sc2 = sc;
        assertTrue(sc.equals(sc2));
        assertTrue(sc2.equals(sc));
    }

    @Test
    public void testIsEqualsDiffTypes() {
        String sc2 = "Asfadsf";
        assertFalse(sc.equals(sc2));
    }

    @Test
    public void testIsEqualsTrue() {
        Schedule sc2 = addSameSessionsTo2Schedules();
        assertTrue(sc.equals(sc2));
        assertTrue(sc2.equals(sc));
    }

    @Test
    public void testIsEqualsSomeAreSameFalse() {
        Schedule sc2 = addSameSessionsTo2Schedules();
        sc2.addNewDayAndMeetingTime(SATURDAY, LocalTime.of(13, 0), LocalTime.of(15, 0));
        assertFalse(sc.equals(sc2));
        assertFalse(sc2.equals(sc));
    }

    @Test
    public void testIsEqualsNoneSameFalse() {
        sc.addNewDayAndMeetingTime(MONDAY, NOON, MIDNIGHT);
        Schedule sc2 = new Schedule();
        sc2.addNewDayAndMeetingTime(TUESDAY, NOON, MIDNIGHT);
        assertFalse(sc.equals(sc2));
        assertFalse(sc2.equals(sc));
    }

    private void checkTThHaveExpectedSessions(Set<TimeBlock> expectedSessions) {
        assertEquals(expectedSessions, sc.getTimeBlocksForDay(TUESDAY));
        assertEquals(expectedSessions, sc.getTimeBlocksForDay(THURSDAY));
    }

    private void checkMWFhaveExpectedSessions(Set<TimeBlock> expectedSessions) {
        assertEquals(expectedSessions, sc.getTimeBlocksForDay(MONDAY));
        assertEquals(expectedSessions, sc.getTimeBlocksForDay(WEDNESDAY));
        assertEquals(expectedSessions, sc.getTimeBlocksForDay(FRIDAY));
    }

    private Schedule addSameSessionsTo2Schedules() {
        sc.addNewDayAndMeetingTime(MONDAY, NOON, MIDNIGHT);
        sc.addNewDayAndMeetingTime(TUESDAY, LocalTime.of(5, 0), NOON);
        Schedule sc2 = new Schedule();
        sc2.addNewDayAndMeetingTime(TUESDAY, LocalTime.of(5, 0), NOON);
        sc2.addNewDayAndMeetingTime(MONDAY, NOON, MIDNIGHT);
        return sc2;
    }

}
