package tests;

import model.places.Library;
import model.places.LibraryManager;
import model.places.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.DayOfWeek.*;
import static java.time.LocalTime.MIDNIGHT;
import static org.junit.jupiter.api.Assertions.*;

// import static tests.TestLibrary.OPEN_HOUR;

// import test.tests.TestPlaceManager;

public class TestLibraryManager extends TestPlaceManager {

    private static final Library ikb = new Library("IKB", TestLibrary.OPEN_HOUR, TestLibrary.CLOSE_HOUR);
    private static final Library asian = new Library("Asian Library", TestLibrary.OPEN_HOUR, MIDNIGHT);
    private static final LocalTime AFTER_MIDNIGHT = LocalTime.of(0, 1);

    @BeforeEach
    public void setUp() {
        lox = new LibraryManager();
    }

    @Test
    public void testAddOnePlace() {
        lox.addPlace(ikb);
        assertTrue(lox.containsPlace(ikb));
        assertEquals(1, lox.getSize());
    }

    @Test
    public void testAddTwoPlaces() {
        addDefaultPlaces();
        assertTrue(lox.containsPlace(ikb));
        assertTrue(lox.containsPlace(asian));
        assertEquals(2, lox.getSize());
    }

    @Test
    public void testAddPlaceAlreadyThere() {
        lox.addPlace(ikb);
        lox.addPlace(ikb);
        assertTrue(lox.containsPlace(ikb));
        assertEquals(1, lox.getSize());
    }

    @Test
    public void testGetOpenPlacesList() {
        addDefaultPlaces();
        assertTrue(lox.getOpenPlacesList(MONDAY, AFTER_MIDNIGHT).contains(ikb));
        assertFalse(lox.getOpenPlacesList(MONDAY, AFTER_MIDNIGHT).contains(asian));
        assertEquals(1, lox.getOpenPlacesList(MONDAY, AFTER_MIDNIGHT).size());
    }

    @Test
    public void testGetOpenPlacesListAll() {
        addDefaultPlaces();
        assertTrue(lox.getOpenPlacesList(TUESDAY, RANDOM_TIME2).contains(ikb));
        assertTrue(lox.getOpenPlacesList(TUESDAY, RANDOM_TIME2).contains(asian));
        assertEquals(2, lox.getOpenPlacesList(TUESDAY, RANDOM_TIME2).size());
    }


    @Test
    public void testPrintOpenPlaces() {
        addDefaultPlaces();
        assertEquals(ikb.getName() + "\n", lox.printOpenPlaces(FRIDAY, AFTER_MIDNIGHT));
    }

    @Test
    public void testPrintOpenPlacesAll() {
        addDefaultPlaces();
        assertEquals(ikb.getName() + "\n" + asian.getName() + "\n", lox.printOpenPlaces(SUNDAY, RANDOM_TIME2));
    }


    @Test
    public void testContainsPlaceTrue() {
        lox.addPlace(ikb);
        assertTrue(lox.containsPlace(ikb));
    }

    @Test
    public void testContainsPlaceFalse() {
        lox.addPlace(ikb);
        assertFalse(lox.containsPlace(asian));
    }

    @Test
    public void testContainsPlaceFalseEmpty() {
        assertFalse(lox.containsPlace(asian));
        assertFalse(lox.containsPlace(ikb));
    }

    @Test
    public void testPrintAllPlaces() {
        lox.addPlace(asian);
        assertEquals("\n" + asian.getName(), lox.printAllPlaces());
        //assertEquals("Libraries:\n" + asian.getName(), lox.printAllPlaces());
    }

    @Test
    public void testGetPlaces() {
        addDefaultPlaces();
        List<Place> list = new ArrayList<>();
        list.add(ikb);
        list.add(asian);
        assertEquals(list, lox.getPlaces());
    }

    @Test
    public void testGetPlaceNotThere() {
        addDefaultPlaces();
        assertNull(lox.getPlace("Koerner Library"));
    }

    @Test
    public void testGetPlaceThere() {
        addDefaultPlaces();
        assertEquals(ikb, lox.getPlace(ikb.getName()));
    }


    @Test
    public void testIterator() {
        List<String> list = new ArrayList<>();
        assertNotNull(lox.iterator());
        assertTrue(lox.iterator().getClass().equals(list.iterator().getClass()));
    }

    @Test
    public void testSetPlacesEmpty() {
        lox.setPlaces(new ArrayList<>());
        assertEquals(0, lox.getSize());
    }

    @Test
    public void testSetPlaces() {
        List<Place> places = new ArrayList<>();
        places.add(ikb);
        places.add(asian);
        lox.setPlaces(places);
        assertEquals(2, lox.getSize());
        assertTrue(lox.containsPlace(ikb));
        assertTrue(lox.containsPlace(asian));
    }

    public void addDefaultPlaces() {
        lox.addPlace(ikb);
        lox.addPlace(asian);
    }
}
