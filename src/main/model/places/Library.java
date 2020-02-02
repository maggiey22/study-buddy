package model.places;

import java.time.LocalTime;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

public class Library extends Place {

    // REQUIRES: openTime != closeTime
    // EFFECTS: creates a library with given name and open hours
    public Library(String libraryName, LocalTime openTime, LocalTime closeTime) {
        super(libraryName, openTime, closeTime);
        schedule.addNewDayAndMeetingTime(SATURDAY, openTime, closeTime);
        schedule.addNewDayAndMeetingTime(SUNDAY, openTime, closeTime);
    }

    // EFFECTS: creates a library with given name and default open hours
    public Library(String libraryName) {
        super(libraryName, DEFAULT_OPEN, DEFAULT_CLOSE);
    }

    /*@Override
    public boolean isOpen(LocalTime time) {
        if (closeTime.isBefore(openTime)) {
            return (time.isAfter(openTime) && time.isBefore(MAX))
                    || (time.isAfter(MIDNIGHT) && time.isBefore(closeTime))
                    || (time.equals(MIDNIGHT)) || (time.equals(MAX));
        } else {
            return time.isAfter(openTime) && time.isBefore(closeTime);
        }
    }*/
    // TODO - fix for midnight: session on 1 day goes from 6am to 11:59PM, then next day is 12am-1am

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Library)) {
            return false;
        }

        Place place = (Library) o;

        if (!placeName.equals(place.placeName)) {
            return false;
        }
//        if (!openTime.equals(place.openTime)) {
//            return false;
//        }
//        return closeTime.equals(place.closeTime);
        return schedule.equals(place.schedule);
    }

    // INTELLIJ IS RECOMMENDING I DELETE THIS BECAUSE IT ONLY CALLS SUPER.
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
