package tests;

import model.places.Place;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import static java.time.LocalTime.NOON;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class TestPlace {

    Place place;

    public abstract void testConstructor();

    @Test
    public void testIsOpenTrue() {
        assertTrue(place.isOpen(TUESDAY, NOON));
    }

    @Test
    public void testIsOpenFalse() {
        assertFalse(place.isOpen(WEDNESDAY, LocalTime.of(5, 0)));
    }

    public abstract void testGetAddressSetAddress();

    public abstract void testGetAddressNull();

    public abstract void testSetAddressNull();

//    public abstract void testEqualsSameRef();
//
//    public abstract void testEqualsDiffTypes();
//
//    public abstract void testEqualsDiffName();
//
//    public abstract void testEqualsSameEverything();


//    @Test
//    public void testIsOpenFalseOpeningTime() {
//        assertFalse(place.isOpen(place.getOpenTime()));
//    }

//    @Test
//    public void testIsOpenFalseClosingTime() {
//        fail("");
//        //assertFalse(place.isOpen(place.getCloseTime()));
//    }

    @Test
    public abstract void testIsOpenOpenCloseTimeAreSame();

    // Test obsolete - Place.setOpenTime / setCloseTime no longer exist
//    @Test
//    public void testSetOpenTime() {
//        fail("");
//        LocalTime newOpenHours = LocalTime.of(7, 0);
//        //place.setOpenTime(newOpenHours);
//        //assertEquals(newOpenHours, place.getOpenTime());
//    }
//
//    @Test
//    public void testSetCloseTime() {
//        fail("");
//        LocalTime newCloseHours = LocalTime.of(19, 0);
//        //place.setCloseTime(newCloseHours);
//        //assertEquals(newCloseHours, place.getCloseTime());
//    }
}
