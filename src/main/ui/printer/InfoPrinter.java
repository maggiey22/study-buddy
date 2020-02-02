package ui.printer;

import model.places.LearningCentre;
import model.schedule.Course;
import model.schedule.CourseManager;
import model.schedule.Schedule;

import static java.time.DayOfWeek.*;
import static ui.printer.MenuPrinter.LINE_DIV2;

public class InfoPrinter {

    // EFFECTS: prints names of all courses in given list
    public static String printMyCourses(CourseManager cm) {
        StringBuilder coursesString = new StringBuilder();

        for (Course c : cm.getMyCourses()) {
            coursesString.append(c.getCourseCode()).append("\n");
        }

        if (coursesString.toString().equals("")) {
            System.out.println("No courses found.\n");
            return "";
        } else {
            System.out.println(coursesString);
            return coursesString.toString();
        }
    }

    // FIXME: return value is a stub rn
    // EFFECTS: prints names of courses and corresponding meeting time for all courses in given course manager
    public static String printScheduleForAllCourses(CourseManager cm) {
        StringBuilder schedule = new StringBuilder();
        for (Course c : cm.getMyCourses()) {
            schedule.append(c.getCourseCode()).append(":\n")
                    .append(printSchedule(c.getSchedule())).append("\n");
            System.out.println(LINE_DIV2);
            System.out.println(c.getCourseCode() + ":");
            InfoPrinter.printSchedule(c.getSchedule());
        }
        System.out.println(LINE_DIV2);
        return schedule.toString();
    }

    // EFFECTS: prints names of all learning centres associated with a given course
    public static String printLearningCentres(Course course) {
        StringBuilder allLCs = new StringBuilder();
        for (LearningCentre lc : course.getLearningCentres()) {
            allLCs.append("\n").append(lc.getName());
        }
        return allLCs.toString();
    }

    // EFFECTS: prints all meeting sessions in given schedule
    public static String printSchedule(Schedule sch) {
        String acc = "";
        if (sch.getSchedule().containsKey(MONDAY)) {
            acc = acc + "Monday:" + sch.allSessionsForDayToString(MONDAY);
        }
        if (sch.getSchedule().containsKey(TUESDAY)) {
            acc = acc + "\n\nTuesday:" + sch.allSessionsForDayToString(TUESDAY);
        }
        if (sch.getSchedule().containsKey(WEDNESDAY)) {
            acc = acc + "\n\nWednesday:" + sch.allSessionsForDayToString(WEDNESDAY);
        }
        if (sch.getSchedule().containsKey(THURSDAY)) {
            acc = acc + "\n\nThursday:" + sch.allSessionsForDayToString(THURSDAY);
        }
        if (sch.getSchedule().containsKey(FRIDAY)) {
            acc = acc + "\n\nFriday:" + sch.allSessionsForDayToString(FRIDAY);
        }
        acc = appendWeekendSessionsToScheduleString(sch, acc);
        //System.out.println(acc);
        return acc;
    }

    private static String appendWeekendSessionsToScheduleString(Schedule sch, String acc) {
        if (sch.getSchedule().containsKey(SATURDAY)) {
            acc = acc + "\n\nSaturday:" + sch.allSessionsForDayToString(SATURDAY);
        }
        if (sch.getSchedule().containsKey(SUNDAY)) {
            acc = acc + "\n\nSunday:" + sch.allSessionsForDayToString(SUNDAY);
        }
        System.out.println(acc);
        return acc;
    }

}

/*public static String allSessionsForDayToString(Schedule sch, DayOfWeek day) {
        StringBuilder sessions = new StringBuilder();
        Set<TimeBlock> timeBlocks = sch.getTimeBlocksForDay(day);
        if (timeBlocks == null) {
            return "";
        }
        for (TimeBlock t : timeBlocks) {
            sessions.append("\n").append(t.getStartTime()).append("-").append(t.getEndTime());
        }
        return sessions.toString();
    }*/
