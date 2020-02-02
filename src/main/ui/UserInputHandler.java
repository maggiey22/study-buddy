package ui;

import model.places.PlaceManager;
import model.schedule.Course;
import model.schedule.CourseManager;
import ui.loader.CourseManagerLoader;
import ui.loader.Loader;
import ui.printer.InfoPrinter;
import ui.printer.MenuPrinter;
import ui.saver.CourseManagerSaver;
import ui.saver.Saver;
import ui.tools.EditCourseTool;
import ui.tools.EditPlaceTool;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Scanner;

import static java.time.DayOfWeek.*;
import static ui.printer.MenuPrinter.LINE_DIV;
import static ui.printer.MenuPrinter.LINE_DIV2;

public class UserInputHandler {

    // EFFECTS: opens main menu where user can choose to find study places, view/edit their schedule,
    //          or save/load courses
    public static void displayMainMenu(PlaceManager libraryManager, CourseManager courseManager,
                                        PlaceManager learningCentreManager, EditPlaceTool placeEditor,
                                        EditCourseTool courseEditor, Scanner reader) throws IOException {
        while (true) {
            String command = reader.nextLine();
            if (handleMainMenuInput(libraryManager, courseManager, learningCentreManager, placeEditor,
                    courseEditor, reader, command)) {
                break;
            }
        }
    }

    private static boolean handleMainMenuInput(PlaceManager libraryManager, CourseManager courseManager,
                                               PlaceManager learningCentreManager, EditPlaceTool placeEditor,
                                               EditCourseTool courseEditor, Scanner reader, String command)
            throws IOException {
        if (command.equals("1")) {
            displayPlaceFinder(libraryManager, learningCentreManager, placeEditor, reader);
        } else if (command.equals("2")) {
            displayScheduleViewer(courseManager, reader);
        } else if (command.equals("3")) {
            displayScheduleEditor(courseManager, courseEditor, reader);
        } else if (command.equals("4")) {
            displaySaveLoad(courseManager, reader);
        } else if (command.equals("5")) {
            chooseDayAndSessionToEdit(courseManager, reader);
        } else if (command.equals("0")) {
            MenuPrinter.exitStudyBuddy();
            return true;
        } else {
            MenuPrinter.printCommandUnknown();
        }
        return false;
    }

    // TODO: phase out, too awkward to use
    // EFFECTS: asks user if they want to return to main menu; returns user if they type r
    private static void askIfGoBackToMain(Scanner reader) {
        MenuPrinter.askGoBack();
        while (true) {
            String goBack = reader.nextLine();
            if (goBack.equals("R") || goBack.equals("r")) {
                break;
            } else {
                MenuPrinter.printCommandUnknown();
            }
        }
        MenuPrinter.printMenu();
    }

    // EFFECTS: displays place finder page where user can choose to add their own study places
    private static void displayPlaceFinder(PlaceManager libraryManager, PlaceManager learningCentreManager,
                                           EditPlaceTool placeEditor, Scanner reader) {
        System.out.println(LINE_DIV + "\nSTUDY LOCATION FINDER\n");
        // Calendar.getInstance().get(Calendar.DAY_OF_WEEK) this gives an int as the day
        // FIXME !!!!!!!!!!! day of week is hardcoded for now
        libraryManager.printOpenPlaces(FRIDAY, LocalTime.now());
        learningCentreManager.printOpenPlaces(FRIDAY, LocalTime.now());
        while (true) {
            System.out.println(LINE_DIV2 + "\nType Y to add a study place or press any"
                    + " key to return to the main menu.");
            String choice = reader.nextLine();
            if (choice.equals("Y") || choice.equals("y")) {
                displayPlaceEditor(libraryManager, learningCentreManager, placeEditor, reader);
                System.out.println(LINE_DIV2);
                libraryManager.printOpenPlaces(FRIDAY, LocalTime.now());
                learningCentreManager.printOpenPlaces(FRIDAY, LocalTime.now());
            } else {
                MenuPrinter.printMenu();
                break;
            }
        }
    }

    // EFFECTS: adds a default library or learning centre depending on user's choice
    private static void displayPlaceEditor(PlaceManager libraryManager, PlaceManager learningCentreManager,
                                           EditPlaceTool placeEditor, Scanner reader) {
        System.out.println("ADD PLACE");
        System.out.println("Pick a place type: library or lc (learning centre)?");
        String placeType = reader.nextLine();
        if (placeType.equals("library")) {
            placeEditor.addMyPlace(libraryManager, "library");
        } else if (placeType.equals("lc")) {
            placeEditor.addMyPlace(learningCentreManager, "lc");
        } else {
            MenuPrinter.printCommandUnknown();
        }
    }

    // EFFECTS: displays schedule page
    private static void displayScheduleViewer(CourseManager courseManager, Scanner reader) {
        System.out.println(LINE_DIV + "\nOpening your schedule...\n\nYour schedule:");
        InfoPrinter.printScheduleForAllCourses(courseManager);
        askIfGoBackToMain(reader);
    }

    // EFFECTS: displays schedule editor page
    private static void displayScheduleEditor(CourseManager courseManager, EditCourseTool courseEditor,
                                              Scanner reader) {
        System.out.println(LINE_DIV + "\nOpening schedule editor...\n\nEDITOR OPTIONS");
        MenuPrinter.printEditorMenu();
        courseEditor.openScheduleEditor(courseManager, reader);
    }

    // EFFECTS: displays save/load page
    private static void displaySaveLoad(CourseManager courseManager, Scanner reader) throws IOException {
        System.out.println(LINE_DIV + "\nSAVE & LOAD OPTIONS\n");
        openSaveLoad(courseManager, reader);
    }

    // EFFECTS: opens save/load menu for user to choose save/load actions
    private static void openSaveLoad(CourseManager myCourses, Scanner reader) throws IOException {
        MenuPrinter.printSaveLoadMenu();
        Saver s = new CourseManagerSaver();
        Loader l = new CourseManagerLoader();
        // Saver s = new StudyBuddy();
        // Loader l = new StudyBuddy();
        while (true) {
            String choice = reader.nextLine();
            if (choice.equals("save")) {
                s.saveToSaveSlot(myCourses, reader);
            } else if (choice.equals("load")) {
                l.loadSaveSlot(myCourses, reader);
            } else if (choice.equals("delete")) {
                s.wipeSaveSlot(reader);
            } else if (choice.equals("R") || choice.equals("r")) {
                MenuPrinter.printMenu();
                break;
            } else {
                MenuPrinter.printCommandUnknown();
            }
        }
    }

    private static void chooseDayAndSessionToEdit(CourseManager myCourses, Scanner reader) {
        System.out.println(LINE_DIV2 + "\nType the course ID of the course you want to edit (ex. SPAN101):");
        String id = reader.nextLine();
        Course target = myCourses.getCourse(id);
        if (target == null) {
            System.out.println("Course " + id.toUpperCase() + " was not found.\n");
        } else {
            System.out.println("Which day would you like to edit?\n[M] Monday\n[T] Tuesday\n[W] Wednesday\n"
                    + "[Th] Thursday\n[F] Friday\n");
            String dayChoice = reader.nextLine();
            handleDayAndSessionEditorMenu(reader, target, dayChoice);
        }
        MenuPrinter.printMenu();
    }

    private static void handleDayAndSessionEditorMenu(Scanner reader, Course target, String dayChoice) {
        if (dayChoice.equals("M") || dayChoice.equals("m")) {
            displayChosenDayAndSessionPicker(reader, target, MONDAY);
        } else if (dayChoice.equals("T") || dayChoice.equals("t")) {
            displayChosenDayAndSessionPicker(reader, target, TUESDAY);
        } else if (dayChoice.equals("W") || dayChoice.equals("w")) {
            displayChosenDayAndSessionPicker(reader, target, WEDNESDAY);
        } else if (dayChoice.equals("Th") || dayChoice.equals("th")) {
            displayChosenDayAndSessionPicker(reader, target, THURSDAY);
        } else if (dayChoice.equals("F") || dayChoice.equals("f")) {
            displayChosenDayAndSessionPicker(reader, target, FRIDAY);
        } else {
            System.out.println("Invalid day.");
        }
    }

    private static void displayChosenDayAndSessionPicker(Scanner reader, Course target, DayOfWeek day) {
        if (confirmDayExistsInSchedule(target, day)) {
            System.out.println("All sessions found:");
            System.out.println(target.getSchedule().allSessionsForDayToString(day));
            chooseSessionToEdit(target, reader, day);
        } else {
            System.out.println("Add " + day + " to schedule? [Y] [N]");
            String choice = reader.nextLine();
            if (choice.equals("Y") || choice.equals("y")) {
                System.out.println("Start time of session you wish to add:");
                String start = reader.nextLine();
                System.out.println("Start time of session you wish to add:");
                String end = reader.nextLine();
                target.getSchedule().addNewDayAndMeetingTime(day, LocalTime.parse(start), LocalTime.parse(end));
                System.out.println(day + " " + start + "-" + end + " added to schedule.");
            }
        }
    }


    // make a thing to return localtime
    private static void chooseSessionToEdit(Course c, Scanner reader, DayOfWeek day) {
        System.out.println("\n[D] Delete all sessions under " + day + "\n[E] Edit times of a single session"
                + "\n[A] Add session under " + day);
        String choice = reader.nextLine();
        if (choice.equals("D") || choice.equals("d")) {
            c.getSchedule().removeDay(day);
            System.out.println("All sessions under " + day + " were removed.");
        } else if (choice.equals("E") || choice.equals("e")) {
            handleEditSingleSession(c, reader, day);
        } else if (choice.equals("A") || choice.equals("a")) {
            handleAddDay(c, reader, day);
        } else {
            MenuPrinter.printCommandUnknown();
        }
    }

    private static void handleAddDay(Course c, Scanner reader, DayOfWeek day) {
        System.out.println("Enter the start time of the session to add:");
        String start = reader.nextLine();
        System.out.println("Enter the end time of the session to add:");
        String end = reader.nextLine();
        c.getSchedule().addMeetingTimeUnderExistingDay(day, LocalTime.parse(start), LocalTime.parse(end));
        System.out.println("Added session under " + day + " from " + start + "-" + end);
    }

    private static void handleEditSingleSession(Course c, Scanner reader, DayOfWeek day) {
        System.out.println("Start time of the session you wish to edit:");
        String oldStart = reader.nextLine();
        System.out.println("End time of the session you wish to edit:");
        String oldEnd = reader.nextLine();
        // TimeBlock tb = c.getSchedule().getTimeBlockByDayStartEnd(day, LocalTime.parse(start),
        // LocalTime.parse(end));
        // System.out.println("you chose " + day + tb.getStartTime() + tb.getEndTime());
        System.out.println("New start time:");
        String newStart = reader.nextLine();
        //tb.setStartTime(LocalTime.parse(newStart));
        System.out.println("New end time:");
        String newEnd = reader.nextLine();
        c.getSchedule().setMeetingTime(day, LocalTime.parse(oldStart), LocalTime.parse(oldEnd),
                LocalTime.parse(newStart), LocalTime.parse(newEnd));
        //tb.setEndTime(LocalTime.parse(newEnd));
    }

    private static boolean confirmDayExistsInSchedule(Course target, DayOfWeek day) {
        if (target.getSchedule().getSchedule().containsKey(day)) {
            System.out.println("Selected: " + day);
            return true;
        } else {
            System.out.println("Nothing found under " + day + ".");
            return false;
        }
    }
}
