package ui.tools;

import model.places.PlaceManager;

public interface EditPlaceTool {

    // MODIFIES: myPlaces
    // EFFECTS: adds default library or learning centre matching placeType
    void addMyPlace(PlaceManager myPlaces, String placeType);

}
