package ui.saver;

import model.schedule.CourseManager;

import java.io.IOException;
import java.util.Scanner;

public interface Saver {

    // MODIFIES: json files
    // EFFECTS: saves current schedule to user-chosen file
    void saveToSaveSlot(CourseManager myCourses, Scanner reader) throws IOException;

    // helper
    // MODIFIES: json files
    // EFFECTS: saves current schedule to file with given name
    void saveToSaveSlot(CourseManager myCourses, String filename) throws IOException;

    // MODIFIES: json files
    // EFFECTS: overwrites and clears the data saved in the user-chosen file
    void wipeSaveSlot(Scanner reader);

    // helper
    // MODIFIES: json files
    // EFFECTS: overwrites and clears the data saved in the file with the given name
    void wipeSaveSlot(String filename);
}
