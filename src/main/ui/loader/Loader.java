package ui.loader;

import model.schedule.CourseManager;

import java.util.Scanner;

public interface Loader {

    // MODIFIES: myCourses
    // EFFECTS: loads the schedule from the user-chosen file into the app
    void loadSaveSlot(CourseManager myCourses, Scanner reader);

    // helper
    // MODIFIES: myCourses
    // EFFECTS: loads the schedule from the file with the given name into the app
    void loadSaveSlot(CourseManager myCourses, String filename);
}
