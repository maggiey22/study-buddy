package model.places;

import java.time.DayOfWeek;
import java.time.LocalTime;

// TODO: consider making remove (or hide), add methods for PLACE
public class LibraryManager extends PlaceManager {

    // EFFECTS: creates an empty list of libraries
    public LibraryManager() {
        super();
    }

    @Override
    public String printOpenPlaces(DayOfWeek day, LocalTime time) {
        System.out.println("Currently open libraries:");
        return super.printOpenPlaces(day, time);
    }

//    @Override
//    public String printAllPlaces() {
//        return "Libraries:" + super.printAllPlaces();
//    }

}
