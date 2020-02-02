package tests;

import model.schedule.Course;
import model.schedule.CourseManager;
import model.schedule.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.loader.CourseManagerLoader;
import ui.loader.Loader;

import java.time.LocalTime;
import java.util.HashSet;

import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalTime.NOON;
import static org.junit.jupiter.api.Assertions.*;


public class TestLoader {

    private static final String TEST_FILE_NAME = "saved_schedule_TEST.json";
    private static CourseManager loc = new CourseManager();
    Loader loader;

    @BeforeEach
    public void setUp() {
        //loader = new StudyBuddy();
        loader = new CourseManagerLoader();
        loc.addCourse(new Course("PHYS118", LocalTime.of(8, 0), NOON, new HashSet<>()));
//        loc.addCourse(new Course("ENGL112", NOON, LocalTime.of(15, 0), null));

        //try {
//            PrintWriter saver = new PrintWriter(TEST_FILE_NAME, "UTF-8");
//            saver.write("[{\"courseCode\":\"MATH200\",\"startTime\":{\"hour\":10,\"minute\":0,\"second\":0,\"nano\"
// :0},\"endTime\":{\"hour\":12,\"minute\":0,\"second\":0,\"nano\":0}}]");
            //saver.write("[]");
//            saver.write("[{\"courseCode\":\"TEST\",\"startTime\":{\"hour\":9,\"minute\":30,\"second\":0,\"nano\":0},"
//                    + "\"endTime\":{\"hour\":12,\"minute\":0,\"second\":0,\"nano\":0}},{\"courseCode\":\"THE\","
//                    + "\"startTime\":{\"hour\":11,\"minute\":0,\"second\":0,\"nano\":0},\"endTime\":{\"hour\":13,"
//                    + "\"minute\":0,\"second\":0,\"nano\":0}},{\"courseCode\":\"LOADER\",\"startTime\":{\"hour\":8,\""
//                    + "minute\":0,\"second\":0,\"nano\":0},\"endTime\":{\"hour\":10,\"minute\":30,\"second
// \":0,\"nano\""
//                    + ":0}}]");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // [{"courseCode":"CPSC107","schedule":{"schedule":{"MONDAY":[{"startTime":{"hour":12,"minute":0,"second":0,"nano":0},"endTime":{"hour":15,"minute":0,"second":0,"nano":0}}]}},"learningCentres":[]}]
    }

    @Test
    public void testLoadSaveSlot() {
        //fail("");
        loader.loadSaveSlot(loc, TEST_FILE_NAME);
        Schedule expectedSchedule = new Schedule();
        expectedSchedule.addNewDayAndMeetingTime(MONDAY, NOON, LocalTime.of(15, 0));

        assertTrue(loc.containsCourseByString("CPSC107"));

        Course cpsc107 = loc.getCourse("CPSC107");
        assertNotNull(cpsc107);
        assertEquals(expectedSchedule, cpsc107.getSchedule());
        assertEquals(1, expectedSchedule.getSchedule().size());
        assertEquals(0, cpsc107.getLearningCentres().size());
        assertEquals("CPSC107", cpsc107.getCourseCode());

        /*assertEquals(LocalTime.of(10,0), math200.getStartTime());
        assertEquals(NOON, math200.getEndTime());*/
//        assertNotNull(loc.getCourse("TEST"));
//        assertNotNull(loc.getCourse("THE"));
//        assertNotNull(loc.getCourse("LOADER"));

    }
}
