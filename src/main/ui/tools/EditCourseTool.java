package ui.tools;

//import exceptions.*;
import model.schedule.Course;
import model.schedule.CourseManager;

import java.util.Scanner;

public interface EditCourseTool {

    // EFFECTS: opens editor menu for user to choose schedule editing actions
    void openScheduleEditor(CourseManager myCourses, Scanner reader);

    // EFFECTS: opens course time editor for user to edit start/end time of course
    void openCourseTimeEditor(CourseManager myCourses, Scanner reader);

    // MODIFIES: myCourses
    // EFFECTS: adds given course to user's list of courses
    void addMyCourse(CourseManager myCourses, Scanner reader);

    // MODIFIES: myCourses
    // EFFECTS: removes given course from user's list of courses
    void removeMyCourse(CourseManager myCourses, Scanner reader);


    // MODIFIES: myCourses
    // EFFECTS: changes given course's meeting times to user input times;
    //          if invalid time inputted, throws InvalidStartMinutesException or InvalidStartHourException
    //          if wrong format inputted, throws InvalidStartFormatException
    void editTime(CourseManager myCourses, Scanner reader)
            /*throws
            InvalidStartFormatException, InvalidStartHourException, InvalidStartMinutesException,
            InvalidEndFormatException, InvalidEndHourException, InvalidEndMinutesException*/;

    // MODIFIES: target
    // EFFECTS: if user-entered time does not match ##:## format, throws InvalidStartFormatException;
    //          if user-entered hour is not between 0-23, throws InvalidStartHourException;
    //          if user-entered minute is not between 0-59, throws InvalidStartMinutesException;
    //          otherwise, sets new start time to user-entered time.
    void editStartTime(Course target, String myStart) /*throws InvalidStartFormatException,
            InvalidStartHourException, InvalidStartMinutesException*/;

    // MODIFIES: target
    // EFFECTS: if user-entered time does not match ##:## format, throws InvalidEndFormatException;
    //          if user-entered hour is not between 0-23, throws InvalidEndHourException;
    //          if user-entered minute is not between 0-59, throws InvalidEndMinutesException;
    //          otherwise, sets new end time to user-entered time.
    void editEndTime(Course target, String myEnd) /*throws InvalidEndFormatException,
            InvalidEndHourException, InvalidEndMinutesException*/;

    // MODIFIES: c
    // EFFECTS: changes given course's start time and if incorrect format is entered, continues to prompt user for
    //          correctly formatted time, then gives user option to change end time
    void editStartTimeRetry(Course c, Scanner reader);

    // MODIFIES: c
    // EFFECTS: changes given course's end time and if incorrect format is entered, continues to prompt user for
    //          correctly formatted time
    void editEndTimeRetry(Course c, Scanner reader);
}
