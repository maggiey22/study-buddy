//package tests;
//
//import exceptions.*;
//import model.schedule.Course;
//import model.schedule.CourseManager;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ui.StudyBuddy;
//import ui.tools.EditCourseTool;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.fail;
//
//
//public class TestInvalidTimeException {
//
//    private EditCourseTool ect;
//    private CourseManager myCourses;
//    private Course engl110 = new Course("ENGL110");
//
//    @BeforeEach
//    public void setUp() {
//        ect = new StudyBuddy();
//        myCourses = new CourseManager();
//        myCourses.addCourse(engl110);
//    }
//
//    @Test
//    public void testInvalidTimeExceptionGetCourse() {
//        try {
//            throw new InvalidTimeException("Testing exception thrown with a course", engl110);
//        } catch (InvalidTimeException e) {
//            assertEquals(engl110, e.getCourse());
//        }
//    }
//
//    @Test
//    public void testEditStartNoExceptionsThrown() {
//        fail("");
//        /*try {
//            ect.editStartTime(engl110, "23:59");
//        } catch (InvalidStartFormatException e) {
//            fail(");
//        } catch (InvalidStartHourException e) {
//            fail(");
//        } catch (InvalidStartMinutesException e) {
//            fail(");
//        }
//        // check that only start time was changed
//        assertEquals(LocalTime.of(23, 59), engl110.getStartTime());
//        assertEquals(MIDNIGHT, engl110.getEndTime());*/
//    }
//
//    @Test
//    public void testEditStartInvalidFormatInvalidMin() {
//        // Time not written in ##:## form && invalid minutes
//        try {
//            ect.editStartTime(engl110, "3:60");
//            fail("");
//        } catch (InvalidStartFormatException e) {
//            // do nothing
//        } catch (InvalidStartHourException e) {
//            fail("");
//        } catch (InvalidStartMinutesException e) {
//            fail("");
//        }
//        // check that neither time was changed
//        checkBothTimesUnchanged();
//    }
//
//    @Test
//    public void testEditStartInvalidFormatInvalidHr() {
//        // Time not written in ##:## form && invalid hr
//        try {
//            ect.editStartTime(engl110, "27:5");
//            fail("");
//        } catch (InvalidStartFormatException e) {
//            // do nothing
//        } catch (InvalidStartHourException e) {
//            fail("");
//        } catch (InvalidStartMinutesException e) {
//            fail("");
//        }
//        // check that neither time was changed
//        checkBothTimesUnchanged();
//    }
//
//    @Test
//    public void testEditStartInvalidHrInvalidMin() {
//        // Invalid hr && invalid minutes
//        try {
//            ect.editStartTime(engl110, "24:75");
//            fail("");
//        } catch (InvalidStartFormatException e) {
//            fail("");
//        } catch (InvalidStartHourException e) {
//            // do nothing
//        } catch (InvalidStartMinutesException e) {
//            fail("");
//        }
//        // check that neither time was changed
//        checkBothTimesUnchanged();
//    }
//
//    @Test
//    public void testEditStartInvalidFormatThrown() {
//        // Time not written in ##:## form
//        try {
//            ect.editStartTime(engl110, "2:00");
//            fail("");
//        } catch (InvalidStartFormatException e) {
//            // do nothing
//        } catch (InvalidStartHourException e) {
//            fail("");
//        } catch (InvalidStartMinutesException e) {
//            fail("");
//        }
//        // check that neither time was changed
//        checkBothTimesUnchanged();
//    }
//
//    @Test
//    public void testEditStartInvalidStartHrThrown() {
//        // Hour is not between 0-23
//        try {
//            ect.editStartTime(engl110, "24:59");
//            fail("");
//        } catch (InvalidStartFormatException e) {
//            fail("");
//        } catch (InvalidStartHourException e) {
//            // do nothing
//        } catch (InvalidStartMinutesException e) {
//            fail("");
//        }
//        // check that neither time was changed
//        checkBothTimesUnchanged();
//    }
//
//    @Test
//    public void testEditStartInvalidStartMinThrown() {
//        // Minutes not between 0-59
//        try {
//            ect.editStartTime(engl110, "23:60");
//            fail("");
//        } catch (InvalidStartFormatException e) {
//            fail("");
//        } catch (InvalidStartHourException e) {
//            fail("");
//        } catch (InvalidStartMinutesException e) {
//            // do nothing
//        }
//        // check that neither time was changed
//        checkBothTimesUnchanged();
//    }
//
//    @Test
//    public void testEditEndNoExceptionsThrown() {
//        fail("");
//        /*try {
//            ect.editEndTime(engl110, "23:59");
//        } catch (InvalidEndFormatException e) {
//            fail(");
//        } catch (InvalidEndHourException e) {
//            fail(");
//        } catch (InvalidEndMinutesException e) {
//            fail(");
//        }
//        // check that only start time was changed
//        assertEquals(MIDNIGHT, engl110.getStartTime());
//        assertEquals(LocalTime.of(23, 59), engl110.getEndTime());*/
//    }
//
//    @Test
//    public void testEditEndInvalidFormatInvalidMin() {
//        // Time not written in ##:## form && invalid minutes
//        try {
//            ect.editEndTime(engl110, "3:60");
//            fail("");
//        } catch (InvalidEndFormatException e) {
//            // do nothing
//        } catch (InvalidEndHourException e) {
//            fail("");
//        } catch (InvalidEndMinutesException e) {
//            fail("");
//        }
//        // check that neither time was changed
//        checkBothTimesUnchanged();
//    }
//
//    @Test
//    public void testEditEndInvalidFormatInvalidHr() {
//        // Time not written in ##:## form && invalid hr
//        try {
//            ect.editEndTime(engl110, "27:5");
//            fail("");
//        } catch (InvalidEndFormatException e) {
//            // do nothing
//        } catch (InvalidEndHourException e) {
//            fail("");
//        } catch (InvalidEndMinutesException e) {
//            fail("");
//        }
//        // check that neither time was changed
//        checkBothTimesUnchanged();
//    }
//
//    @Test
//    public void testEditEndInvalidHrInvalidMin() {
//        // Invalid hr && invalid minutes
//        try {
//            ect.editEndTime(engl110, "24:75");
//            fail("");
//        } catch (InvalidEndFormatException e) {
//            fail("");
//        } catch (InvalidEndHourException e) {
//            // do nothing
//        } catch (InvalidEndMinutesException e) {
//            fail("");
//        }
//        // check that neither time was changed
//        checkBothTimesUnchanged();
//    }
//
//    @Test
//    public void testEditEndInvalidFormatThrown() {
//        // Time not written in ##:## form
//        try {
//            ect.editEndTime(engl110, "2:00");
//            fail("");
//        } catch (InvalidEndFormatException e) {
//            // do nothing
//        } catch (InvalidEndHourException e) {
//            fail("");
//        } catch (InvalidEndMinutesException e) {
//            fail("");
//        }
//        // check that neither time was changed
//        checkBothTimesUnchanged();
//    }
//
//    @Test
//    public void testEditEndInvalidEndHrThrown() {
//        // Hour is not between 0-23
//        try {
//            ect.editEndTime(engl110, "24:59");
//            fail("");
//        } catch (InvalidEndFormatException e) {
//            fail("");
//        } catch (InvalidEndHourException e) {
//            // do nothing
//        } catch (InvalidEndMinutesException e) {
//            fail("");
//        }
//        // check that neither time was changed
//        checkBothTimesUnchanged();
//    }
//
//    @Test
//    public void testEditEndInvalidEndMinThrown() {
//        // Minutes not between 0-59
//        try {
//            ect.editEndTime(engl110, "23:60");
//            fail("");
//        } catch (InvalidEndFormatException e) {
//            fail("");
//        } catch (InvalidEndHourException e) {
//            fail("");
//        } catch (InvalidEndMinutesException e) {
//            // do nothing
//        }
//        // check that neither time was changed
//        checkBothTimesUnchanged();
//    }
//
//    private void checkBothTimesUnchanged() {
//        fail("");
////        assertEquals(MIDNIGHT, engl110.getStartTime());
////        assertEquals(MIDNIGHT, engl110.getEndTime());
//    }
//
//}
