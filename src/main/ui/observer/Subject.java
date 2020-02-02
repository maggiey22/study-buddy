package ui.observer;

import model.places.Place;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {

    private List<PlaceManagerObserver> observers;

    public Subject() {
        this.observers = new ArrayList<>();
    }

    public void addObserver(PlaceManagerObserver o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    public void removeObserver(PlaceManagerObserver o) {
        //if (observers.contains(o)) {
        // do I need guard? Isn't it redundant bc Java already handles case where o is not there?
        observers.remove(o);
        //}
    }

    public void notifyObservers(Place p) {
        for (PlaceManagerObserver o : observers) {
            o.update(p);
        }
    }

}
