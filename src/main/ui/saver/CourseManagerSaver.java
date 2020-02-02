package ui.saver;

import com.google.gson.Gson;
import model.schedule.CourseManager;
import ui.printer.MenuPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import static ui.printer.MenuPrinter.LINE_DIV2;

public class CourseManagerSaver implements Saver {

    @Override
    public void saveToSaveSlot(CourseManager myCourses, Scanner reader) throws IOException {
        System.out.println(LINE_DIV2 + "\nChoose which slot to save to:");
        MenuPrinter.printSaveSlots();
        String saveSlot = reader.nextLine();
        if (saveSlot.equals("1") || saveSlot.equals("2") || saveSlot.equals("3")) {
            saveToSaveSlot(myCourses, "saved_schedule" + saveSlot + ".json");
        } else {
            System.out.println(saveSlot + " is not a valid save slot.\n");
        }
        MenuPrinter.printSaveLoadMenu();
    }

    @Override
    public void saveToSaveSlot(CourseManager myCourses, String filename) throws IOException {
        // V CREDITSTO ************FROM JIMMY LU (TA)*************
        Gson gson = new Gson();
        String json = gson.toJson(myCourses.getMyCourses());
        FileWriter writer = null;
        writer = new FileWriter(filename);
        writer.write(json);
        writer.close();
        System.out.println("The following data was saved to  " + filename + ": " + json + "\n");
        // V CREDITSTO ************end FROM JIMMY LU (TA)*************
    }

    @Override
    public void wipeSaveSlot(Scanner reader) { //FIXME: should this throw IOExc instead of try-catch?
        System.out.println(LINE_DIV2 + "\nChoose the save file you want to delete:");
        MenuPrinter.printSaveSlots();
        String saveSlot = reader.nextLine();
        if (saveSlot.equals("1") || saveSlot.equals("2") || saveSlot.equals("3")) {
            wipeSaveSlot("saved_schedule" + saveSlot + ".json");
        } else {
            System.out.println(saveSlot + " is not a valid save slot.\n");
        }
        MenuPrinter.printSaveLoadMenu();
    }

    @Override
    public void wipeSaveSlot(String filename) {
        PrintWriter saver;
        try {
            saver = new PrintWriter(filename, "UTF-8");
            saver.write("[]");
            saver.flush();
            System.out.println("\nAll data in " + filename + " was deleted.\n");
        } catch (IOException e) {
            System.out.println("What did you do?!??!?");
            e.printStackTrace();
        }
    }
}
