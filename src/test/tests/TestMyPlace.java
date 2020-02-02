package tests;

import model.places.MyPlace;
import model.places.Place;
import model.schedule.TimeBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;

import static java.time.DayOfWeek.*;
import static java.time.LocalTime.NOON;
import static org.junit.jupiter.api.Assertions.*;

public class TestMyPlace {

    private static final LocalTime END = LocalTime.of(14, 0);

    Place place;

    @BeforeEach
    public void setUp() {
        place = new MyPlace("my place", NOON, END);
    }

    @Test
    public void testConstructor() {
        assertEquals("my place", place.getName());
        Map<DayOfWeek, Set<TimeBlock>> map = place.getSchedule().getSchedule();
        assertTrue(map.containsKey(MONDAY));
        assertTrue(map.containsKey(TUESDAY));
        assertTrue(map.containsKey(WEDNESDAY));
        assertTrue(map.containsKey(THURSDAY));
        assertTrue(map.containsKey(FRIDAY));

        map.forEach((k,v) -> {
            assertEquals(1, v.size());
            assertTrue(v.contains(new TimeBlock(NOON, END)));
        });
    }

    @Test
    public void testEqualsSameRef() {
        Place p2 = place;
        assertTrue(p2.equals(place));
        assertTrue(place.equals(p2));
    }

    @Test
    public void testEqualsDiffTypes() {
        String p2 = "aasdfasfsf";
        assertFalse(place.equals(p2));
    }

    @Test
    public void testEqualsDiffName() {
        Place p2 = new MyPlace("MY place", NOON, END);
        assertFalse(p2.equals(place));
        assertFalse(place.equals(p2));
    }

    @Test
    public void testEqualsSameScheduleSameName() {
        Place p2 = new MyPlace("my place", NOON, END);
        assertTrue(p2.equals(place));
        assertTrue(place.equals(p2));
    }
}
