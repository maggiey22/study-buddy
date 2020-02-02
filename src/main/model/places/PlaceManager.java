package model.places;

import ui.observer.Subject;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// FIXME this class doesn't really need to be abstract
// maybe it could be useful if user wants to add their own place (neither library nor learning centre)
public abstract class PlaceManager extends Subject implements Iterable<Place> {
    // implements Iterable<Place>

    protected List<Place> places;

    // EFFECTS: initializes an empty list of places
    public PlaceManager() {
        this.places = new ArrayList<>();
    }

    // EFFECTS: adds place to list if not already there
    public void addPlace(Place p) {
        if (!this.places.contains(p)) {
            this.places.add(p);
            notifyObservers(p);
            // System.out.println(p.getName() + " added successfully.");
        } else {
            System.out.println("Cannot add duplicate place.");
        }
    }

//    // MODIFIES: this
//    // EFFECTS: merges given place manager and this one and returns it (combines their list of places)
//    public PlaceManager mergePlaceManagers(PlaceManager pm) {
//        if (pm == null) {
//            return this;
//        }
//        for (Place p : pm.places) {
//            this.addPlace(p);
//        }
//        return this;
//    }

    /*
    public void removePlace(Place p) {
        if (this.places.contains(p)) {
            this.places.remove(p);
        }
    }

    public void removeAllPlaces() {
        for (Place p : places) {
            removePlace(p);
        }
        places = new ArrayList<>();
    }
    */

    // EFFECTS: returns list of places that are open at the given time
    public List<Place> getOpenPlacesList(DayOfWeek day, LocalTime time) {
        List<Place> openPlaces = new ArrayList<>();

        for (Place p : this.places) {
            if (p.isOpen(day, time)) {
                openPlaces.add(p);
            }
        }
        return openPlaces;
    }

    // EFFECTS: prints list of places that are open at the given time
    public String printOpenPlaces(DayOfWeek day, LocalTime time) {
        List<Place> openPlaces = getOpenPlacesList(day, time);
        StringBuilder openPlacesString = new StringBuilder();

        for (Place l : openPlaces) {
            openPlacesString.append(l.getName()).append("\n");
        }

        if (openPlacesString.toString().equals("")) {
            System.out.println("None are open at this time.\n");
            return "";
        } else {
            System.out.println(openPlacesString);
            return openPlacesString.toString();
        }
    }

    public String printAllPlaces() {
        StringBuilder placesString = new StringBuilder();

        for (Place p : places) {
            placesString.append("\n").append(p.placeName);
        }
        return placesString.toString();
    }

    // EFFECTS: returns number of places in list
    public int getSize() {
        return this.places.size();
    }

    // EFFECTS: returns true if given place is in the list; false otherwise
    public boolean containsPlace(Place p) {
        return this.places.contains(p);
    }

    public List<Place> getPlaces() {
        return places;
    }

    // EFFECTS: returns the place with the given name
    public Place getPlace(String name) {
        Place place = null;
        for (Place p : this.places) {
            if (p.getName().equalsIgnoreCase(name)) {
                place = p;
            }
        }
        return place;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Place> iterator() {
        return places.iterator();
    }
}
