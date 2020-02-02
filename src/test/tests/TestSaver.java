package tests;

import model.schedule.Course;
import model.schedule.CourseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.saver.CourseManagerSaver;
import ui.saver.Saver;

import java.io.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Scanner;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import static java.time.LocalTime.NOON;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSaver {

    public static final String TEST_SAVE_FILE_NAME = "saved_schedule_SAVE_TEST.json";
    public static final String TEST_CLEAR_FILE_NAME = "saved_schedule_CLEAR_TEST.json";
    private static CourseManager loc = new CourseManager();
    private static final String EXPECTED_JSON_STRING2 = "[{\"courseCode\":\"CPSC107\",\"schedule\":{"
            + "\"schedule\":{\"MONDAY\":[{\"startTime\":{\"hour\":12,\"minute\":0,\"second\":0,\"nano"
            + "\":0},\"endTime\":{\"hour\":15,\"minute\":0,\"second\":0,\"nano\":0}}]}},\"learningCentres\":[]}]";

    private static final String RANDOM_STRING = "alsdfkjalsdfkjasdlfjasdfl";
    private static final Course cpsc107 = new Course("CPSC107", NOON,
            LocalTime.of(15, 0), new HashSet<>());

    Saver saver;

    @BeforeEach
    public void setUp() {
        cpsc107.getSchedule().removeDay(WEDNESDAY);
        cpsc107.getSchedule().removeDay(FRIDAY);
        saver = new CourseManagerSaver();
        loc.addCourse(cpsc107);
    }

    @Test
    public void testSaveToSlot() throws IOException {
        addRandomStringToFile(TEST_SAVE_FILE_NAME);
        assertEquals(RANDOM_STRING, getJsonString(TEST_SAVE_FILE_NAME));

        saver.saveToSaveSlot(loc, TEST_SAVE_FILE_NAME);
        String savedData = getJsonString(TEST_SAVE_FILE_NAME);
        assertEquals(EXPECTED_JSON_STRING2, savedData);
    }

    @Test
    public void testWipeSaveSlot() throws IOException {
        addRandomStringToFile(TEST_CLEAR_FILE_NAME);
        saver.wipeSaveSlot(TEST_CLEAR_FILE_NAME);
        String clearedData = getJsonString(TEST_CLEAR_FILE_NAME);
        assertEquals("[]", clearedData);
    }

    // EFFECTS: returns data in JSON file as a string
    private String getJsonString(String filename) throws FileNotFoundException {
        StringBuilder data = new StringBuilder();
        // V CREDITSTO ****START FROM stackoverflow.com/questions/15695984/java-print-contents-of-text-file-to-screen
        Scanner input = new Scanner(new File(filename));
        while (input.hasNextLine()) {
            data.append(input.nextLine());
        } // V CREDITSTO*** END FROM stackoverflow.com/questions/15695984/java-print-contents-of-text-file-to-screen
        return data.toString();
    }

    // EFFECTS: writes a meaningless string to the given file, overwriting any existing data
    private void addRandomStringToFile(String filename) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        writer.write(RANDOM_STRING);
        writer.flush();
        assertEquals(RANDOM_STRING, getJsonString(filename));
    }
}
