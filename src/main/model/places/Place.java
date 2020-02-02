package model.places;

import model.schedule.Schedule;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;

//public abstract class Place {
public class Place {

    //    public static final LocalTime DEFAULT_OPEN = LocalTime.of(8, 0);
//    public static final LocalTime DEFAULT_CLOSE = LocalTime.of(23, 59);
    public static final LocalTime DEFAULT_OPEN = LocalTime.of(8, 0);
    public static final LocalTime DEFAULT_CLOSE = LocalTime.of(23, 0);

    // private static final TimeBlock DEFAULT_PLACE_TIMEBLOCK = new TimeBlock(DEFAULT_OPEN, DEFAULT_CLOSE);

    protected String placeName;
    //    protected LocalTime openTime;
//    protected LocalTime closeTime;
    protected Schedule schedule;
    //protected String address;
    protected Address address;

    // EFFECTS: creates a place with the given name and schedule with open times
    //          and no address associated
    public Place(String placeName, LocalTime openTime, LocalTime closeTime) {
        this.placeName = placeName;
        this.schedule = Schedule.generateMWFschedule(openTime, closeTime);
        schedule.addNewDayAndMeetingTime(TUESDAY, openTime, closeTime);
        schedule.addNewDayAndMeetingTime(THURSDAY, openTime, closeTime);
        //this.address = "";
        this.address = null;
//        this.openTime = openTime;
//        this.closeTime = closeTime;
    }

    // EFFECTS: returns place's name
    public String getName() {
        return this.placeName;
    }

    // EFFECTS: returns place's schedule of open hours
    public Schedule getSchedule() {
        return schedule;
    }

//    // EFFECTS: returns place's address; empty string if no address assigned
//    public String getAddress() {
//        return address;
//    }

    // EFFECTS: return's place's address; returns null if there isn't one associated.
    public Address getAddress() {
        return address;
    }

    // EFFECTS: returns true if place is open at given time on given day, false otherwise
    public boolean isOpen(DayOfWeek day, LocalTime time) {
        // return time.isAfter(openTime) && time.isBefore(closeTime);
        return schedule.isTimeDuringASession(day, time);
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
        System.out.println("Name changed to " + placeName);

    }

//    public void setAddress(String address) {
//        this.address = address;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Place)) {
            return false;
        }

        Place place = (Place) o;

        if (!placeName.equals(place.placeName)) {
            return false;
        }
        return schedule.equals(place.schedule);
    }

    @Override
    public int hashCode() {
        int result = placeName.hashCode();
        result = 31 * result + schedule.hashCode();
        return result;
    }



/*
    // EFFECTS: returns place's opening time
    public LocalTime getOpenTime() {
        return this.openTime;
    }

    // EFFECTS: returns place's closing time
    public LocalTime getCloseTime() {
        return this.closeTime;
    }

    // EFFECTS: returns true if place is open at given time, false otherwise
    public boolean isOpen(LocalTime time) {
        return time.isAfter(openTime) && time.isBefore(closeTime);
    }

    // REQUIRES: openTime != closeTime
    // EFFECTS: changes library's opening time to newOpenTime
    public void setOpenTime(LocalTime newOpenTime) {
        this.openTime = newOpenTime;
    }

    // REQUIRES: openTime != closeTime
    // EFFECTS: changes library's closing time to newCloseTime
    public void setCloseTime(LocalTime newCloseTime) {
        this.closeTime = newCloseTime;
    }*/

    /*@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Place)) {
            return false;
        }

        Place place = (Place) o;

        if (!placeName.equals(place.placeName)) {
            return false;
        }
        if (!openTime.equals(place.openTime)) {
            return false;
        }
        return closeTime.equals(place.closeTime);
    }

    @Override
    public int hashCode() {
        int result = placeName.hashCode();
        result = 31 * result + openTime.hashCode();
        result = 31 * result + closeTime.hashCode();
        return result;
    }*/

}
