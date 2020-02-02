package ui.printer;

public class MenuPrinter {

    public static final String LINE_DIV = "============================================";
    public static final String LINE_DIV2 = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

    public static void printGreeting() {
        System.out.println("********************************************");
        System.out.println("Hello there! Welcome to the study companion.");
        printMenu();
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
        askGoBack();
    }

    public static void printSaveLoadMenu() {
        System.out.println("Type 'save' to save the current schedule.");
        System.out.println("Type 'load' to load a schedule.");
        System.out.println("Type 'delete' to delete saved schedules.\n");
        askGoBack();
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
    }
}
