package tests;

import model.schedule.TimeBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static java.time.LocalTime.MIDNIGHT;
import static java.time.LocalTime.NOON;
import static org.junit.jupiter.api.Assertions.*;

public class TestTimeBlock {

    private static final LocalTime START = LocalTime.of(8, 0);

    TimeBlock tb;

    @BeforeEach
    public void setUp() {
        tb = new TimeBlock(START, NOON);
    }

    // OBSOLETE TESTS - doesItConflict is no longer needed for
    /*@Test
    public void testDoesItConflictNullFalse() {
        assertFalse(tb.doesItConflict(null));
    }

    @Test
    public void testDoesItConflictSameBlockTrue() {
        assertTrue(tb.doesItConflict(new TimeBlock(START, NOON)));
    }

    @Test
    public void testDoesItConflictBetweenEarlierStartSameEndTrue() {
        TimeBlock tb2 = new TimeBlock(LocalTime.of(7, 0), NOON);
        assertTrue(tb.doesItConflict(tb2));
    }

    @Test
    public void testDoesItConflictBetweenSameStartLaterEndTrue() { //this should cover the last branch?
        TimeBlock tb2 = new TimeBlock(START, LocalTime.of(13, 0));
        boolean isConflicting = tb.doesItConflict(tb2);
        //assertTrue(tb.doesItConflict(tb2));
        assertTrue(isConflicting);
    }

    @Test
    public void testDoesItConflictBetweenSameStartTrue() {
        TimeBlock tb2 = new TimeBlock(START, LocalTime.of(10, 0));
        assertTrue(tb.doesItConflict(tb2));
    }

    @Test
    public void testDoesItConflictBetweenSameEndTrue() {
        TimeBlock tb2 = new TimeBlock(LocalTime.of(10, 0), NOON);
        assertTrue(tb.doesItConflict(tb2));
    }

    @Test
    public void testDoesItConflictCompletelyBetweenTrue() {
        TimeBlock tb2 = new TimeBlock(LocalTime.of(9, 0), LocalTime.of(10, 0));
        assertTrue(tb.doesItConflict(tb2));
    }

    @Test
    public void testDoesItConflictCompletelyOverlapTrue() {
        TimeBlock tb2 = new TimeBlock(LocalTime.of(7, 0), LocalTime.of(13, 0));
        assertTrue(tb.doesItConflict(tb2));
    }

    @Test
    public void testDoesItConflictStartBetweenTrue() {
        TimeBlock tb2 = new TimeBlock(LocalTime.of(9, 0), LocalTime.of(13, 0));
        assertTrue(tb.doesItConflict(tb2));
    }

    @Test
    public void testDoesItConflictEndBetweenTrue() {
        TimeBlock tb2 = new TimeBlock(LocalTime.of(7, 0), LocalTime.of(10, 0));
        assertTrue(tb.doesItConflict(tb2));
    }

    @Test
    public void testDoesItConflictEarlyFalse() {
        TimeBlock tb2 = new TimeBlock(LocalTime.of(6, 0), LocalTime.of(7, 0));
        assertFalse(tb.doesItConflict(tb2));
    }

    @Test
    public void testDoesItConflictLateFalse() {
        TimeBlock tb2 = new TimeBlock(LocalTime.of(13, 0), LocalTime.of(15, 0));
        assertFalse(tb.doesItConflict(tb2));
    }

    @Test
    public void testDoesItConflictEndIsStartFalse() {
        TimeBlock tb2 = new TimeBlock(LocalTime.of(7, 0), START);
        assertFalse(tb.doesItConflict(tb2));
    }

    @Test
    public void testDoesItConflictStartIsEndFalse() {
        TimeBlock tb2 = new TimeBlock(NOON, LocalTime.of(13, 0));
        assertFalse(tb.doesItConflict(tb2));
    }

    @Test
    // missing branch test
    public void testDoesItConflictSameStartGivenEndIsAfterThis() {
        TimeBlock tb2 = new TimeBlock(START, LocalTime.of(14, 0));
        assertTrue(tb.doesItConflict(tb2));
        assertTrue(tb2.doesItConflict(tb));
    }*/

    @Test
    public void testIsItBetweenNormal() {
        assertTrue(tb.isItBetween(LocalTime.of(9, 0)));
    }

    @Test
    public void testIsItBetweenOvernight() {
        TimeBlock tb2 = new TimeBlock(NOON, LocalTime.of(2, 0));
        assertTrue(tb2.isItBetween(MIDNIGHT));
    }

    @Test
    public void testEqualsSameRef() {
        TimeBlock tb2 = tb;
        assertTrue(tb2.equals(tb));
        assertTrue(tb.equals(tb2));
    }

    @Test
    public void testEqualsDiffTypes() {
        String tb2 = "asffadsf";
        assertFalse(tb.equals(tb2));
    }

    @Test
    public void testEqualsDiffStartTime() {
        TimeBlock tb2 = new TimeBlock(LocalTime.of(7, 40), NOON);
        assertFalse(tb.equals(tb2));
        assertFalse(tb2.equals(tb));
    }

    @Test
    public void testEqualsSameEverything() {
        TimeBlock tb2 = new TimeBlock(START, NOON);
        assertTrue(tb.equals(tb2));
        assertTrue(tb2.equals(tb));
    }

    @Test
    public void testHashCode() {
        TimeBlock tb2 = new TimeBlock(START, NOON);
        TimeBlock tb3 = new TimeBlock(MIDNIGHT, NOON);
        assertEquals(tb.hashCode(), tb2.hashCode());
        assertNotEquals(tb.hashCode(), tb3.hashCode());
    }

    @Test
    public void testGetStartTime() {
        assertEquals(START, tb.getStartTime());
    }

    @Test
    public void testGetEndTime() {
        assertEquals(NOON, tb.getEndTime());
    }

    @Test
    public void testSetStartTime() {
        tb.setStartTime(LocalTime.of(11, 0));
        assertEquals(LocalTime.of(11, 0), tb.getStartTime());
        assertEquals(NOON, tb.getEndTime());
    }

    @Test
    public void testSetEndTime() {
        tb.setEndTime(MIDNIGHT);
        assertEquals(START, tb.getStartTime());
        assertEquals(MIDNIGHT, tb.getEndTime());
    }
}
