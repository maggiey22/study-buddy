package tests;

import model.places.LearningCentre;
import model.places.LearningCentreManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.HashSet;

import static java.time.DayOfWeek.*;
import static org.junit.jupiter.api.Assertions.*;
import static tests.TestLearningCentre.cpsc210;


// import test.tests.TestPlaceManager;

public class TestLearningCentreManager extends TestPlaceManager {

    private static final LearningCentre RANDOM_LC = new LearningCentre("demco",
            LocalTime.of(8, 0), LocalTime.of(16, 0), new HashSet<>());
    private static final LearningCentre RANDOM_LC2 = new LearningCentre("cpsc office hrs",
            LocalTime.of(14, 0), LocalTime.of(15, 0), new HashSet<>());
    private static final LocalTime RANDOM_TIME3 = LocalTime.of(14, 30);

    @BeforeEach
    public void setUp() {
        lox = new LearningCentreManager();
        RANDOM_LC.addCourse(cpsc210);
        RANDOM_LC2.addCourse(cpsc210);
    }

    @Test
    public void testAddOnePlace() {
        lox.addPlace(RANDOM_LC);
        assertTrue(lox.containsPlace(RANDOM_LC));
        assertEquals(1, lox.getSize());
    }

    @Test
    public void testAddTwoPlaces() {
        addDefaultPlaces();
        assertTrue(lox.containsPlace(RANDOM_LC));
        assertTrue(lox.containsPlace(RANDOM_LC2));
        assertEquals(2, lox.getSize());
    }

    @Test
    public void testAddPlaceAlreadyThere() {
        lox.addPlace(RANDOM_LC2);
        lox.addPlace(RANDOM_LC2);
        assertTrue(lox.containsPlace(RANDOM_LC2));
        assertEquals(1, lox.getSize());
    }

    @Test
    public void testGetOpenPlacesList() {
        addDefaultPlaces();
        assertTrue(lox.getOpenPlacesList(MONDAY, LocalTime.of(14, 0)).contains(RANDOM_LC));
        assertFalse(lox.getOpenPlacesList(TUESDAY, LocalTime.of(9, 0)).contains(RANDOM_LC2));
        assertEquals(1, lox.getOpenPlacesList(MONDAY, LocalTime.of(13, 0)).size());
    }

    @Test
    public void testGetOpenPlacesListAll() {
        addDefaultPlaces();
        assertTrue(lox.getOpenPlacesList(MONDAY, RANDOM_TIME3).contains(RANDOM_LC));
        assertTrue(lox.getOpenPlacesList(FRIDAY, RANDOM_TIME3).contains(RANDOM_LC2));
        assertEquals(2, lox.getOpenPlacesList(THURSDAY, RANDOM_TIME3).size());
    }

    @Test
    public void testPrintOpenPlaces() {
        addDefaultPlaces();
        assertEquals(RANDOM_LC.getName() + "\n", lox.printOpenPlaces(MONDAY, RANDOM_TIME2));
    }

    @Test
    public void testPrintOpenPlacesAll() {
        addDefaultPlaces();
        assertEquals(RANDOM_LC.getName() + "\n" + RANDOM_LC2.getName() + "\n",
                lox.printOpenPlaces(TUESDAY, RANDOM_TIME3));
    }

    @Test
    public void testContainsPlaceTrue() {
        lox.addPlace(RANDOM_LC);
        assertTrue(lox.containsPlace(RANDOM_LC));
    }

    @Test
    public void testContainsPlaceFalse() {
        lox.addPlace(RANDOM_LC);
        assertFalse(lox.containsPlace(RANDOM_LC2));
    }

    @Test
    public void testContainsPlaceFalseEmpty() {
        assertFalse(lox.containsPlace(RANDOM_LC));
        assertFalse(lox.containsPlace(RANDOM_LC2));
    }

    @Test
    public void testPrintAllPlaces() {
        lox.addPlace(RANDOM_LC);
        assertEquals("\n" + RANDOM_LC.getName(), lox.printAllPlaces());
        //assertEquals("Learning Centres:\n" + RANDOM_LC.getName(), lox.printAllPlaces());
    }

    public void addDefaultPlaces() {
        lox.addPlace(RANDOM_LC);
        lox.addPlace(RANDOM_LC2);
    }
}
