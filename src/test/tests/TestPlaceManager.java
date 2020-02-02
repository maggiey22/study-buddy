package tests;

import model.places.PlaceManager;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.LocalTime.NOON;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class TestPlaceManager {

    private static final LocalTime RANDOM_TIME = LocalTime.of(5, 0);
    public static final LocalTime RANDOM_TIME2 = LocalTime.of(13, 0);

    PlaceManager lox;

    @Test
    public void testConstructor() {
        assertEquals(0, lox.getSize());
    }

    public abstract void testAddOnePlace();

    public abstract void testAddTwoPlaces();

    public abstract void testAddPlaceAlreadyThere();

    @Test
    public void testGetOpenPlacesListEmpty() {
        assertEquals(0, lox.getOpenPlacesList(SUNDAY, NOON).size());
    }

    @Test
    public void testGetOpenPlacesListNoneOpen() {
        addDefaultPlaces();
        assertEquals(0, lox.getOpenPlacesList(SUNDAY, LocalTime.of(2, 30)).size());
        //assertEquals(0, lox.getOpenPlacesList(RANDOM_TIME).size());
    }

    public abstract void testGetOpenPlacesList();

    public abstract void testGetOpenPlacesListAll();

    @Test
    public void testPrintOpenPlacesEmpty() {
        assertEquals("", lox.printOpenPlaces(SATURDAY, NOON));
    }

    public abstract void testPrintOpenPlaces();

    public abstract void testPrintOpenPlacesAll();

    @Test
    public void testGetSizeEmpty() {
        assertEquals(0, lox.getSize());
    }

    @Test
    public void testGetSize() {
        addDefaultPlaces();
        assertEquals(2, lox.getSize());
    }

    public abstract void testContainsPlaceTrue();

    public abstract void testContainsPlaceFalse();

    public abstract void testContainsPlaceFalseEmpty();

    public abstract void addDefaultPlaces();
}
