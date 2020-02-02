package model.places;

import java.time.DayOfWeek;
import java.time.LocalTime;

// TODO contains
public class LearningCentreManager extends PlaceManager {

    // EFFECTS: creates an empty list of learning centres
    public LearningCentreManager() {
        super();
    }

    @Override
    public String printOpenPlaces(DayOfWeek day, LocalTime time) {
        System.out.println("Currently open learning centres:");
        return super.printOpenPlaces(day, time);
    }

//    @Override
//    public String printAllPlaces() {
//        return "Learning Centres:" + super.printAllPlaces();
//    }

    //        List<Place> openLC = getOpenPlacesList(time);
//        StringBuilder openLCString = new StringBuilder();
//
//        for (Place l : openLC) {
//            openLCString.append(l.getName()).append("\n");
//        }
//
//        if (openLCString.toString().equals("")) {
//            System.out.println("No learning centres are open at this time.\n");
//            return "";
//        } else {
//            System.out.println(openLCString);
//            return openLCString.toString();
//        }

}
