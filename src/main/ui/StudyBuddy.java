package ui;

// import exceptions.*;
import model.places.LearningCentre;
import model.places.Library;
import model.places.Place;
import model.places.PlaceManager;
import model.schedule.Course;
import model.schedule.CourseManager;
import ui.loader.DefaultLoader;
import ui.printer.MenuPrinter;
import ui.tools.EditCourseTool;
import ui.tools.EditPlaceTool;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Scanner;

import static ui.printer.MenuPrinter.LINE_DIV2;

//Loader, Saver,
public class StudyBuddy implements EditPlaceTool, EditCourseTool {

    public static void main(String[] args) throws IOException {
        DefaultLoader initLoader = new DefaultLoader();
        PlaceManager libraryManager = initLoader.loadLibraries(); //actual type LibraryManager
        CourseManager courseManager = initLoader.loadCourses();
        PlaceManager learningCentreManager = initLoader.loadLearningCentres(courseManager); //actual type LCManager
        EditPlaceTool placeEditor = new StudyBuddy();
        EditCourseTool courseEditor = new StudyBuddy();
        MenuPrinter.printGreeting();

        Scanner reader = new Scanner(System.in);
        UserInputHandler.displayMainMenu(libraryManager, courseManager, learningCentreManager,
                placeEditor, courseEditor, reader);
    }

    @Override
    public void addMyPlace(PlaceManager myPlaces, String placeType) {
        if (placeType.equals("library")) {
//            Place place = new Library("Default Library", LocalTime.of(23, 0),
//                    MAX);
            Place place = new Library("Default Library", LocalTime.of(8, 0),
                    LocalTime.of(23, 0));
            myPlaces.addPlace(place);
            // System.out.println("Default Library added.\n");
        } else if (placeType.equals("lc")) {
            /*Place place = new LearningCentre("Default LC", LocalTime.of(23, 0),
                    MAX, new HashSet<>());*/
            Place place = new LearningCentre("Default Learning Centre");
            myPlaces.addPlace(place);
            // System.out.println("Default LC added.\n");
        } else {
            MenuPrinter.printCommandUnknown();
        }
    }

    @Override
    public void openScheduleEditor(CourseManager myCourses, Scanner reader) {
        while (true) {
            String myEditTool = reader.nextLine();
            if (myEditTool.equals("remove")) {
                removeMyCourse(myCourses, reader);
            } else if (myEditTool.equals("add")) {
                addMyCourse(myCourses, reader);
            } else if (myEditTool.equals("set time")) {
                while (true) { // FIXME : while loop doesn't loop :(
                    openCourseTimeEditor(myCourses, reader);
                    break; // isUserWrong once user is correct - break
                }
            } else if (myEditTool.equals("R") || myEditTool.equals("r")) {
                MenuPrinter.printMenu();
                break;
            } else {
                MenuPrinter.printCommandUnknown();
            }
        }
    }

    @Override
    public void openCourseTimeEditor(CourseManager myCourses, Scanner reader) {
        try {
            editTime(myCourses, reader);
//        } catch (InvalidStartTimeException e) {
//            Course c = e.getCourse();
//            System.err.println("INVALID START TIME: time must be between 00:00-23:59 and contain 4 digits.");
//            System.err.println("Please try again:");
//            editStartTimeRetry(c, reader);
//        } catch (InvalidEndTimeException e) {
//            Course c = e.getCourse();
//            System.err.println("INVALID END TIME: time must be between 00:00-23:59 and contain 4 digits.");
//            System.err.println("Please try again:");
//            editEndTimeRetry(c, reader);
        } finally {
            MenuPrinter.printEditorMenu();
        } //FIXME i want confirmation msg to be as descriptive as success on first try (confirms new time)
    }

    @Override
    public void addMyCourse(CourseManager myCourses, Scanner reader) {
        System.out.println(LINE_DIV2 + "\nType the course ID of the course to add (ex. CPSC210):");
        String name = reader.nextLine().toUpperCase();
        boolean alreadyThere = myCourses.containsCourseByString(name);
        myCourses.addCourseByName(name);
        if (name.equals("")) {
            System.out.println("Course without an ID cannot be added.\n");
        } else if (!alreadyThere) {
            System.out.println(name + " was added.\n");
        } else {
            System.out.println("Course " + name + " was already added and cannot be added again.\n");
        }
        MenuPrinter.printEditorMenu();
    }

    @Override
    public void removeMyCourse(CourseManager myCourses, Scanner reader) {
        System.out.println(LINE_DIV2 + "\nType the course ID of the course to remove (ex. ECON101):");
        String byeCourse = reader.nextLine().toUpperCase();
        boolean isThere = myCourses.containsCourseByString(byeCourse);
        myCourses.removeCourse(byeCourse);
        if (isThere) {
            System.out.println("Course " + byeCourse + " was removed.\n");
        } else {
            System.out.println("Course " + byeCourse + " was not found.\n");
        }
        MenuPrinter.printEditorMenu();
    }


    @Override
    public void editTime(CourseManager myCourses, Scanner reader) /*throws
            InvalidStartFormatException, InvalidStartHourException, InvalidStartMinutesException,
            InvalidEndFormatException, InvalidEndHourException, InvalidEndMinutesException*/ {
        System.out.println(LINE_DIV2 + "\nType the course ID of the course you want to edit (ex. SPAN101):");
        String id = reader.nextLine();
        Course target = myCourses.getCourse(id);
        if (target == null) {
            System.out.println("Course " + id.toUpperCase() + " was not found.\n");
        } else {
            System.out.println(LINE_DIV2 + "\nEnter the new start time in the form 11:11 "
                    + "(ex. 09:30 for 9:30AM), \nor press enter to keep it the same:");
            String myStart = reader.nextLine();
            editStartTime(target, myStart);
            System.out.println("Enter the new end time in the form 11:11 (ex. 14:00 for 2:00PM), "
                    + "\nor press enter to keep it the same:");
            String myEnd = reader.nextLine();
            editEndTime(target, myEnd);
            // System.out.println("Meeting time for " + id.toUpperCase() + " was changed to "
            //     + target.getStartTime() + "-" + target.getEndTime() + ". \n"); TODO
        }
    }

    @Override
    public void editStartTime(Course target, String myStart) /*throws InvalidStartFormatException,
            InvalidStartHourException, InvalidStartMinutesException*/ {
        if (!myStart.equals("")) {
            if (!myStart.matches("\\d{2}(:\\d{2})")) {
            //    throw new InvalidStartFormatException("Invalid format.", target);
            } else if (!myStart.matches("(((0|1)[0-9])|(2[0-3])):\\d{2}")) {
            //    throw new InvalidStartHourException("Invalid hours.", target);
            } else if (!myStart.matches("\\d{2}:[0-5][0-9]")) {
            //    throw new InvalidStartMinutesException("Invalid minutes.", target);
            } else {
                LocalTime startTime = LocalTime.parse(myStart);
                // target.setStartTime(startTime); TODO
            }
        }
    }

    @Override
    public void editEndTime(Course target, String myEnd) /*throws InvalidEndFormatException,
            InvalidEndHourException, InvalidEndMinutesException*/ {
        if (!myEnd.equals("")) {
            if (!myEnd.matches("\\d{2}(:\\d{2})")) {
                //throw new InvalidEndFormatException("Invalid format.", target);
            } else if (!myEnd.matches("(((0|1)[0-9])|(2[0-3])):\\d{2}")) {
                //throw new InvalidEndHourException("Invalid hours.", target);
            } else if (!myEnd.matches("\\d{2}:[0-5][0-9]")) {
                //throw new InvalidEndMinutesException("Invalid minutes.", target);
            } else {
                LocalTime endTime = LocalTime.parse(myEnd);
                // target.setEndTime(endTime); TODO
            }
        }
    }

    @Override
    public void editStartTimeRetry(Course c, Scanner reader) {
        while (true) {
            String myStart = reader.nextLine();
            try {
                //editStartTime(c, myStart);
                break;
//            } catch (InvalidStartTimeException s) {
//                System.err.println("INVALID START TIME. Please try again:");
//            }
            } finally {
                System.out.println("");
            }
        }
        System.out.println(LINE_DIV2 + "\nEnter the new end time in the form 11:11 "
                + "(ex. 14:00 for 2:00PM), \nor press enter to keep it the same:");
        editEndTimeRetry(c, reader);
    }

    @Override
    public void editEndTimeRetry(Course c, Scanner reader) {
        while (true) {
            String myEnd = reader.nextLine();
            try {
                editEndTime(c, myEnd);
                break;
//            } catch (InvalidEndTimeException s) {
//                System.out.println("INVALID END TIME. Please try again:");
//            }
            } finally {
                System.out.println();
            }
        }
    }
    /*// EFFECTS: opens main menu where user can choose to find study places, view/edit their schedule,
    //          or save/load courses
    private static void displayMainMenu(PlaceManager libraryManager, CourseManager courseManager,
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

    // EFFECTS: displays place finder page where user can choose to add their own study places
    private static void displayPlaceFinder(PlaceManager libraryManager, PlaceManager learningCentreManager,
                                           EditPlaceTool placeEditor, Scanner reader) {
        System.out.println(LINE_DIV + "\nSTUDY LOCATION FINDER\n");
        libraryManager.printOpenPlaces(LocalTime.now());
        learningCentreManager.printOpenPlaces(LocalTime.now());
        while (true) {
            System.out.println(LINE_DIV2 + "\nType Y to add a study place or press any"
                    + " key to return to the main menu.");
            String choice = reader.nextLine();
            if (choice.equals("Y") || choice.equals("y")) {
                displayPlaceEditor(libraryManager, learningCentreManager, placeEditor, reader);
                System.out.println(LINE_DIV2);
                libraryManager.printOpenPlaces(LocalTime.now());
                learningCentreManager.printOpenPlaces(LocalTime.now());
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
        courseManager.printScheduleForAllCourses();
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
    }*/

    /*public static void printGreeting() {
        System.out.println("********************************************");
        System.out.println("Hello there! Welcome to the study companion.");
        MenuPrinter.printMenu();
    }

    public static void printMenu() {
        System.out.println(LINE_DIV + "\nMAIN MENU\n");
        System.out.println("Type 1 to find a study location.");
        System.out.println("Type 2 to view your schedule.");
        System.out.println("Type 3 to edit your schedule.");
        System.out.println("Type 4 to save or load courses.");
        // System.out.println("Type 5 to add your own study location.");
        System.out.println("Type 0 to exit the program.\n\n");
    }

    public static void printEditorMenu() {
        System.out.println("\nType 'remove' to remove a course.");
        System.out.println("Type 'add' to add a course.");
        System.out.println("Type 'set time' to edit course times.\n");
        MenuPrinter.askGoBack();
    }

    public static void printSaveLoadMenu() {
        System.out.println("Type 'save' to save the current schedule.");
        System.out.println("Type 'load' to load a schedule.");
        System.out.println("Type 'delete' to delete saved schedules.\n");
        MenuPrinter.askGoBack();
    }

    public static void printSaveSlots() {
        System.out.println("Save slots: [1] [2] [3]");
    }

    public static void printCommandUnknown() {
        System.out.println("Command not recognized.");
    }

    public static void askGoBack() {
        System.out.println("Type R to go back.");
    }

    public static void exitStudyBuddy() {
        System.out.println(LINE_DIV + "\nThank you for using the study companion.");
        System.out.println("Good luck studying!\nExiting program...");
    }*/

    /*// EFFECTS: asks user if they want to return to main menu; returns user if they type r
    public static void askIfGoBackToMain(Scanner reader) {
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
    }*/


    /*
       throws
            InvalidStartFormatException, InvalidStartHourException, InvalidStartMinutesException,
            InvalidEndFormatException, InvalidEndHourException, InvalidEndMinutesException
    */

    /*public static void chooseDayAndSessionToEdit(CourseManager myCourses, Scanner reader) {
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
    public static void chooseSessionToEdit(Course c, Scanner reader, DayOfWeek day) {
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
    }*/

    /*public void editTimeNEW(CourseManager myCourses, Scanner reader) throws
            InvalidStartFormatException, InvalidStartHourException, InvalidStartMinutesException,
            InvalidEndFormatException, InvalidEndHourException, InvalidEndMinutesException {
        System.out.println(LINE_DIV2 + "\nEnter the new start time in the form 11:11 "
                + "(ex. 09:30 for 9:30AM), \nor press enter to keep it the same:");
        String myStart = reader.nextLine();
        editStartTime(target, myStart);
        System.out.println("Enter the new end time in the form 11:11 (ex. 14:00 for 2:00PM), "
                + "\nor press enter to keep it the same:");
        String myEnd = reader.nextLine();
        editEndTime(target, myEnd);
        // System.out.println("Meeting time for " + id.toUpperCase() + " was changed to "
        //     + target.getStartTime() + "-" + target.getEndTime() + ". \n"); TODO
    }*/


    /*// EFFECTS: opens save/load menu for user to choose save/load actions
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
    }*/

/*    @Override
    public void saveToSaveSlot(CourseManager myCourses, Scanner reader) throws IOException {
        System.out.println(LINE_DIV2 + "\nChoose which slot to save to:");
        printSaveSlots();
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
        // CREDITS TO ************FROM JIMMY LU (TA)*************
        Gson gson = new Gson();
        String json = gson.toJson(myCourses.getMyCourses());
        FileWriter writer = null;
        writer = new FileWriter(filename);
        writer.write(json);
        writer.close();
        System.out.println("The following data was saved to  " + filename + ": " + json + "\n");
        // CREDITS TO ************end FROM JIMMY LU (TA)*************
    }

    @Override
    public void wipeSaveSlot(Scanner reader) { // should this throw IOExc instead of try-catch?
        System.out.println(LINE_DIV2 + "\nChoose the save file you want to delete:");
        printSaveSlots();
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
    }*/
/*
    @Override
    public void loadSaveSlot(CourseManager myCourses, Scanner reader) {
        System.out.println("Choose the save file to load:");
        printSaveSlots();
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
        Gson gson = new Gson(); // CREDITS TO ************FROM JIMMY LU (TA)*************
        JsonReader jsonReader;
        try {
            jsonReader = new JsonReader(new FileReader(filename));
            Course[] savedCoursesArray = gson.fromJson(jsonReader, Course[].class);
            // CREDITS TO Just next line from https://stackoverflow.com/questions/157944/create-arraylist-from-array
            List<Course> savedCourses = new ArrayList<>(Arrays.asList(savedCoursesArray));
            myCourses.setMyCourses(savedCourses);
            if (savedCoursesArray.length == 0) {
                System.out.println("Empty schedule was loaded.\n");
            } else {
                System.out.println("The following schedule was loaded:");
                for (Course c : savedCoursesArray) {
                    // System.out.println(c.getCourseCode() + ": " + c.getStartTime() + "-" + c.getEndTime() + "\n");
                }
            } //CREDITS TO ************end FROM JIMMY LU (TA)*************
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/
}
