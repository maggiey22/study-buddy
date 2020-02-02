package model.schedule;

import java.util.ArrayList;
import java.util.List;

public class CourseManager {

    private List<Course> myCourses;

    // EFFECTS: creates new empty list of courses
    public CourseManager() {
        this.myCourses = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: if course is not already in list, adds course to course list
    public void addCourse(Course course) {
        boolean alreadyThere = containsCourse(course);
        // if the course isn't already there, the course to add isn't null
        // AND there isn't already a course with that name
        if (!alreadyThere && course != null && getCourse(course.getCourseCode()) == null) {
            this.myCourses.add(course);
        }
//        boolean alreadyThere = containsCourse(course);
//        if (!alreadyThere && course != null) {
//            this.myCourses.add(course);
//        }
    }

    // EFFECTS: adds course with given name and 0-0 meeting time to list only if name is not
    //          already in list
    public void addCourseByName(String name) {
        if (getCourse(name) == null) {
            Course newCourse = new Course(name.toUpperCase());
            if (!name.equals("")) {
                myCourses.add(newCourse);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: if course is in list, remove course
    public void removeCourse(String name) {
        Course courseToRemove = getCourse(name.toUpperCase());
        if (!(courseToRemove == null)) {
            myCourses.remove(courseToRemove);
        }
    }

    // EFFECTS: return the course with the given name (as uppercase)
    public Course getCourse(String name) {
        Course course = null;
        for (Course c : this.myCourses) {
            if (c.getCourseCode().equals(name.toUpperCase())) {
                course = c;
            }
        }
        return course;
    }

    // EFFECTS: return size of course list
    public int getSize() {
        return this.myCourses.size();
    }

    // EFFECTS: return true if course is in list, false otherwise
    public boolean containsCourse(Course courseUnknown) {
        return myCourses.contains(courseUnknown);
    }

    // EFFECTS: returns list of courses
    public List<Course> getMyCourses() {
        return myCourses;
    }

    // EFFECTS: sets list of courses to given one
    public void setMyCourses(List<Course> myCourses) {
        this.myCourses = myCourses;
    }

    // EFFECTS: returns true if course with given id is in course list
    public boolean containsCourseByString(String id) {
        return !(getCourse(id) == null);
    }
}

/*// EFFECTS: prints list of courses
    public String printMyCourses() {
        StringBuilder coursesString = new StringBuilder();

        for (Course c : this.myCourses) {
            coursesString.append(c.getCourseCode()).append("\n");
        }

        if (coursesString.toString().equals("")) {
            System.out.println("No courses found.\n");
            return "";
        } else {
            System.out.println(coursesString);
            return coursesString.toString();
        }
    }*/

    /*
    // EFFECTS: prints list of courses and corresponding meeting time
    public String printScheduleForAllCourses() {
        for (Course c : myCourses) {
            System.out.println(LINE_DIV2);
            System.out.println(c.getCourseCode() + ":");
            c.getSchedule().printSchedule();
        }
        System.out.println(LINE_DIV2);
        return "";
    }*/

    /*StringBuilder schedule = new StringBuilder();
        for (Course c : this.myCourses) {
            // hardcoding MWF right now but will implement varying times later
            schedule.append(c.getCourseCode()).append(": MWF, ").append(c.getStartTime())
                    .append("-").append(c.getEndTime()).append("\n");
        }

        if (schedule.toString().equals("")) {
            System.out.println("No courses found.\n");
            return "";
        } else {
            System.out.println(schedule);
            return schedule.toString();
        }*/

    /*if (courseUnknown == null) {
            return false;
        } else {
            String name = courseUnknown.getCourseCode();
            Course course = getCourse(name);
            return !(course == null);
        }*/
