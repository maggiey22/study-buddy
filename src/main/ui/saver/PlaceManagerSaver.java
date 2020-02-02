package ui.saver;

import com.google.gson.Gson;
import model.places.PlaceManager;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PlaceManagerSaver {

    public void saveToSaveSlot(PlaceManager myPlaces, String filename) throws IOException {
        // V CREDITSTO ************FROM JIMMY LU (TA)*************
        Gson gson = new Gson();
        String json = gson.toJson(myPlaces.getPlaces());
        FileWriter writer = null;
        writer = new FileWriter(filename);
        writer.write(json);
        writer.close();
        System.out.println("The following data was saved to  " + filename + ": " + json + "\n");
        // V CREDITSTO ************end FROM JIMMY LU (TA)*************
    }

    public void wipeSaveSlot(String filename) {
        PrintWriter saver;
        try {
            saver = new PrintWriter(filename, "UTF-8");
            saver.write("[]");
            saver.flush();
            System.out.println("\nAll data in " + filename + " was deleted.\n");
        } catch (IOException e) {
            System.out.println("AN ERROR OCCURRED!");
            e.printStackTrace();
        }
    }
}
