package ui.loader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import model.schedule.Course;
import model.schedule.CourseManager;
import ui.printer.MenuPrinter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CourseManagerLoader implements Loader {

    @Override
    public void loadSaveSlot(CourseManager myCourses, Scanner reader) {
        System.out.println("Choose the save file to load:");
        MenuPrinter.printSaveSlots();
        String saveSlot = reader.nextLine();
        if (saveSlot.equals("1") || saveSlot.equals("2") || saveSlot.equals("3")) {
            loadSaveSlot(myCourses, "saved_schedule" + saveSlot + ".json");
        } else {
            System.out.println(saveSlot + " is not a valid save slot.\n");
        }
        MenuPrinter.printSaveLoadMenu();
    }

    @Override
    public void loadSaveSlot(CourseManager myCourses, String filename) {
        Gson gson = new Gson(); //V CREDITSTO ************FROM JIMMY LU (TA)*************
        JsonReader jsonReader;
        try {
            jsonReader = new JsonReader(new FileReader(filename));
            Course[] savedCoursesArray = gson.fromJson(jsonReader, Course[].class);
            // X CREDITSTO Just next line from https://stackoverflow.com/questions/157944/create-arraylist-from-array
            List<Course> savedCourses = new ArrayList<>(Arrays.asList(savedCoursesArray));
            myCourses.setMyCourses(savedCourses);
            if (savedCoursesArray.length == 0) {
                System.out.println("Empty schedule was loaded.\n");
            } else {
                System.out.println("The following information was loaded:");
                for (Course c : savedCoursesArray) {
                    // System.out.println(c.getCourseCode() + ": " + c.getStartTime() + "-" + c.getEndTime() + "\n");
                    // TODO CHeck what is being printed
                    System.out.println(c.toString());
                }
            } //V CREDITSTO ************end FROM JIMMY LU (TA)*************
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
