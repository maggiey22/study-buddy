package tests;

import model.places.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAddress {

    private static final String ST = "1866 Main Mall";
    private static final String NEW_ST = "2175 West Mall";
    private static final String VAN = "Vancouver";
    private static final String NEW_CITY = "Paris";
    private static final String BC = "BC";

    Address ads;

    @BeforeEach
    public void setUp() {
        ads = new Address(ST, VAN, BC);
    }

    @Test
    public void testGetStreet() {
        assertEquals(ST, ads.getStreet());
    }

    @Test
    public void testSetStreet() {
        ads.setStreet(NEW_ST);
        assertEquals(NEW_ST, ads.getStreet());
    }

    @Test
    public void testGetCity() {
        assertEquals(VAN, ads.getCity());
    }

    @Test
    public void testSetCity() {
        ads.setCity(NEW_CITY);
        assertEquals(NEW_CITY, ads.getCity());
    }

    @Test
    public void testGetProvince() {
        assertEquals(BC, ads.getProvince());
    }

    @Test
    public void testSetProvince() {
        ads.setProvince("ON");
        assertEquals("ON", ads.getProvince());
    }

    @Test
    public void testMakeQueryableAddress() {
        String s = ads.makeQueryableAddress();
        assertEquals("1866+Main+Mall,+Vancouver,+BC", s);
    }

    @Test
    public void testEqualsSameRef() {
        Address ads2 = ads;
        assertTrue(ads2.equals(ads));
        assertTrue(ads.equals(ads2));
    }

    @Test
    public void testEqualsNotInstanceOf() {
        String ads2 = "this is not an address object!";
        assertFalse(ads.equals(ads2));
    }

    @Test
    public void testEqualsDiffStreets() {
        Address ads2 = new Address(NEW_ST, VAN, BC);
        assertFalse(ads2.equals(ads));
        assertFalse(ads.equals(ads2));
    }

    @Test
    public void testEqualsDiffCities() {
        Address ads2 = new Address(ST, "Burnaby", BC);
        assertFalse(ads2.equals(ads));
        assertFalse(ads.equals(ads2));
    }

    @Test
    public void testEqualsDiffProvinces() {
        Address ads2 = new Address(ST, VAN, "ON");
        assertFalse(ads2.equals(ads));
        assertFalse(ads.equals(ads2));
    }

    @Test
    public void testEqualsSameEverything() {
        Address ads2 = new Address(ST, VAN, BC);
        assertTrue(ads2.equals(ads));
        assertTrue(ads.equals(ads2));
    }

    @Test
    public void testHashCode() {
        Address ads2 = new Address(ST, VAN, BC);
        assertEquals(ads2.hashCode(), ads.hashCode());

        Address ads3 = new Address(NEW_ST, VAN, BC);
        assertNotEquals(ads2.hashCode(), ads3.hashCode());
        assertNotEquals(ads.hashCode(), ads3.hashCode());
    }
}
