package ui.loader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import model.places.Place;
import model.places.PlaceManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlaceManagerLoader {

    public void loadSaveSlot(PlaceManager places, String filename) {
        Gson gson = new Gson(); //V CREDITSTO ************FROM JIMMY LU (TA)*************
        JsonReader jsonReader;
        try {
            jsonReader = new JsonReader(new FileReader(filename));
            Place[] savedPlacesArray = gson.fromJson(jsonReader, Place[].class);
            // X CREDITSTO Just next line from https://stackoverflow.com/questions/157944/create-arraylist-from-array
            List<Place> savedPlaces = new ArrayList<>(Arrays.asList(savedPlacesArray));
            places.setPlaces(savedPlaces);
            if (savedPlacesArray.length == 0) {
                System.out.println("Empty places was loaded.\n");
            } else {
                System.out.println("The following information was loaded:");
                for (Place p : savedPlacesArray) {
                    // System.out.println(c.getCourseCode() + ": " + c.getStartTime() + "-" + c.getEndTime() + "\n");
                    // TODO CHeck what is being printed
                    System.out.println(p.getName());
                }
            } //V CREDITSTO ************end FROM JIMMY LU (TA)*************
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
