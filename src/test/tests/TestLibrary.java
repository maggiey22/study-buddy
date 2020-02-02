package tests;

import model.places.Address;
import model.places.LearningCentre;
import model.places.Library;
import model.places.Place;
import model.schedule.Course;
import model.schedule.TimeBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;

import static java.time.DayOfWeek.*;
import static java.time.LocalTime.*;
import static model.places.Place.DEFAULT_CLOSE;
import static model.places.Place.DEFAULT_OPEN;
import static org.junit.jupiter.api.Assertions.*;

// import test.tests.TestPlace;

public class TestLibrary extends TestPlace {

    private static final LocalTime NINE30AM = LocalTime.of(9, 30);
    private static final LocalTime TWO_PM = LocalTime.of(14, 0);
    public static final LocalTime OPEN_HOUR = LocalTime.of(6, 0);
    public static final LocalTime CLOSE_HOUR = LocalTime.of(1, 0);
    private static final Library LIBRARY_12AM_CLOSE = new Library("test", LocalTime.of(9, 0),
            MIDNIGHT);
    private static final Library NORMAL_LIBRARY = new Library("normal library",
            LocalTime.of(10, 30), LocalTime.of(18, 30));

    @BeforeEach
    public void setUp() {
        place = new Library("IKB", OPEN_HOUR, CLOSE_HOUR);
    }

    @Test
    public void testConstructor() {
        Library l = new Library("Law", NINE30AM, TWO_PM);
        assertTrue(l.getName().equals("Law"));
        Map<DayOfWeek, Set<TimeBlock>> map = l.getSchedule().getSchedule();

        assertTrue(map.containsKey(MONDAY));
        assertTrue(map.containsKey(TUESDAY));
        assertTrue(map.containsKey(WEDNESDAY));
        assertTrue(map.containsKey(THURSDAY));
        assertTrue(map.containsKey(FRIDAY));
        assertTrue(map.containsKey(SATURDAY));
        assertTrue(map.containsKey(SUNDAY));

        map.forEach((k,v) -> {
            assertEquals(1, v.size());
            assertTrue(v.contains(new TimeBlock(NINE30AM, TWO_PM)));
        });
//        assertEquals(NINE30AM, l.getOpenTime());
//        assertEquals(TWO_PM, l.getCloseTime());
    }

    @Override
    @Test
    public void testGetAddressSetAddress() {
        place.setAddress(new Address("777 Main Mall", "Vancouver", "BC"));
        assertEquals(new Address("777 Main Mall", "Vancouver", "BC"), place.getAddress());
    }

    @Override
    @Test
    public void testGetAddressNull() {
        assertNull(place.getAddress());
    }

    @Override
    @Test
    public void testSetAddressNull() {
        place.setAddress(new Address("777 Main Mall", "Vancouver", "BC"));
        place.setAddress(null);
        assertNull(place.getAddress());
    }

    @Test
    public void testConstructorWithDefaultTimes() {
        Library testLib = new Library("Test Library");
        assertEquals("Test Library", testLib.getName());
        Map<DayOfWeek, Set<TimeBlock>> map = testLib.getSchedule().getSchedule();

        assertTrue(map.containsKey(MONDAY));
        assertTrue(map.containsKey(TUESDAY));
        assertTrue(map.containsKey(WEDNESDAY));
        assertTrue(map.containsKey(THURSDAY));
        assertTrue(map.containsKey(FRIDAY));

        map.forEach((k,v) -> {
            assertEquals(1, v.size());
            assertTrue(v.contains(new TimeBlock(DEFAULT_OPEN, DEFAULT_CLOSE)));
        });
//        assertEquals(DEFAULT_OPEN, testLib.getOpenTime());
//        assertEquals(DEFAULT_CLOSE, testLib.getCloseTime());
    }

    @Test
    public void testIsOpenTrueMidnight() {
        assertTrue(place.isOpen(MONDAY, MIDNIGHT));
    }

    @Test
    public void testIsOpenTrueAfterMidnight() {
        Library newLib = new Library("abc", NOON, LocalTime.of(1, 0));
        LocalTime time = LocalTime.of(0, 20);
        assertTrue(newLib.isOpen(MONDAY, time));
    }

    @Test
    public void testIsOpenTrueJustBeforeMidnight() {
        assertTrue(LIBRARY_12AM_CLOSE.isOpen(TUESDAY, MAX));
    }

    @Test
    public void testIsOpenTrue115959PM() {
        LocalTime time = LocalTime.of(11, 59, 59);
        assertTrue(LIBRARY_12AM_CLOSE.isOpen(THURSDAY, time));
    }

    @Test
    public void testIsOpenFalseEarly() {
        LocalTime time = LocalTime.of(4, 30);
        assertFalse(LIBRARY_12AM_CLOSE.isOpen(WEDNESDAY, time));
    }

    @Test
    public void testIsOpenFalseLate() {
        LocalTime time = LocalTime.of(2, 0);
        assertFalse(LIBRARY_12AM_CLOSE.isOpen(TUESDAY, time));
    }

    @Test
    public void testIsOpenTrue12AMcloseLower() {
        assertTrue(LIBRARY_12AM_CLOSE.isOpen(TUESDAY, MIDNIGHT));
    }

    @Test
    public void testNormalIsOpenTrue() {
        assertTrue(NORMAL_LIBRARY.isOpen(MONDAY, NOON));
    }

    @Test
    public void testNormalIsOpenFalseMidnight() {
        assertFalse(NORMAL_LIBRARY.isOpen(TUESDAY, MIDNIGHT));
    }

    @Test
    public void testNormalIsOpenFalseLate() {
        assertFalse(NORMAL_LIBRARY.isOpen(TUESDAY, LocalTime.of(21, 0)));
    }

    @Test
    public void testIsOpenOpenCloseTimeAreSame() {
        Place p2 = new Library("abc", NOON, NOON);
        assertTrue(p2.getSchedule().getSchedule().containsKey(MONDAY));
        assertFalse(p2.isOpen(MONDAY, NOON));
        assertFalse(p2.isOpen(MONDAY, LocalTime.of(11, 0)));
        assertFalse(p2.isOpen(MONDAY, LocalTime.of(13, 0)));
//        assertFalse(l2.isOpen(NOON));
//        assertFalse(l2.isOpen(LocalTime.of(11, 0)));
//        assertFalse(l2.isOpen(LocalTime.of(13, 0)));
    }

    @Test
    public void testSetName() {
        place.setPlaceName("Irving K. Barber");
        assertEquals("Irving K. Barber", place.getName());
    }

    // FIXME I don't actually need this to run 2x but idk where to put it if not in tests.TestPlace
    @Test
    public void testEqualsSameReferenceTrue() {
        Place p = place;
        assertTrue(p.equals(place));
        assertTrue(place.equals(p));
    }

    @Test
    public void testEqualsDiffTypeNotBothPlaceFalse() {
        Place p1 = new Library("abc");
        Course c2 = new Course("bcd");
        assertFalse(p1.equals(c2));
        assertFalse(c2.equals(p1));
    }

    @Test
    public void testEqualsDiffTypeFalse() {
        Place p1 = new LearningCentre("pLaCe");
        Place p2 = new Library("lib");
        assertFalse(p1.equals(p2));
        assertFalse(p2.equals(p1));
    }

    //
    @Test
    public void testEqualsDiffTypeSameNameApparentTypePlaceFalse() {
        Place p1 = new LearningCentre("lib");
        Place p2 = new Library("lib");
//        LearningCentre p1 = new LearningCentre("lib");
//        Library p2 = new Library("lib");
        assertFalse(p1.equals(p2));
        assertFalse(p2.equals(p1));
    } // FIXME: move equals and hashcode methods into library and lc separately

    @Test
    public void testEqualsDiffTypeSameNameApparentTypeLibLCFalse() {
        LearningCentre p1 = new LearningCentre("lib");
        Library p2 = new Library("lib");
        assertFalse(p1.equals(p2));
        assertFalse(p2.equals(p1));
    }

    // FIXME: IDK if it makes sense
    // but for some reason generate equals/hashcode option doesn't show up under Library class :(

    @Test
    public void testEqualsSameTypeDiffNameFalse() {
        Place p1 = new Library("place1");
        Place p2 = new Library("place2");
        assertFalse(p1.equals(p2));
        assertFalse(p2.equals(p1));
    }

    @Test
    public void testEqualsSameTypeSameNameDiffOpenTimeFalse() {
        Place p1 = new Library("place1", NOON, NOON);
        Place p2 = new Library("place1", MIDNIGHT, NOON);
        assertFalse(p1.equals(p2));
        assertFalse(p2.equals(p1));
    }

    @Test
    public void testEqualsSameTypeSameNameSameOpenTimeDiffCloseTimeFalse() {
        Place p1 = new Library("place1", NOON, NOON);
        Place p2 = new Library("place1", NOON, MIDNIGHT);
        assertFalse(p1.equals(p2));
        assertFalse(p2.equals(p1));
    }

    @Test
    public void testEqualsSameTypeSameNameSameOpenTimeSameCloseTimeDiffInstTrue() {
        Place p1 = new Library("place1", NOON, MIDNIGHT);
        Place p2 = new Library("place1", NOON, MIDNIGHT);
        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));
    }

    @Test
    public void testHashCode() {
        Library l2 = new Library("normal library",
                LocalTime.of(10, 30), LocalTime.of(18, 30));
        assertEquals(NORMAL_LIBRARY.hashCode(), l2.hashCode());
        assertNotEquals(LIBRARY_12AM_CLOSE.hashCode(), NORMAL_LIBRARY.hashCode());
    }

    @Test
    public void testHashCodeOneWithAdsOneWithout() {
        Library l2 = new Library("normal library",
                LocalTime.of(10, 30), LocalTime.of(18, 30));
        l2.setAddress(new Address("2000 Main Mall", "Vancouver", "BC"));
        assertEquals(NORMAL_LIBRARY.hashCode(), l2.hashCode());
    }


}
