package model.schedule;

import model.places.LearningCentre;
import ui.printer.InfoPrinter;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class Course {

    private String courseCode;
    private Schedule schedule;
    private Set<LearningCentre> learningCentres;

    // FIXME: is it bad to introduce guards within constructors?
    // EFFECTS: creates a course with given code, given start/end times (default MWF), and learning centre
    //          only if given string is not empty
    public Course(String courseCode, LocalTime startTime, LocalTime endTime, Set<LearningCentre> learningCentres) {
        // if (!courseCode.equals("")) {
        this.courseCode = courseCode;
        this.schedule = Schedule.generateMWFschedule(startTime, endTime);
        this.learningCentres = learningCentres;
    }

    // FIXME: is it bad to introduce guards within constructors?
    // EFFECTS: creates a course with given code and default meeting time 0-0 on MWF,
    //          and no learning centres associated only if given string is not empty
    public Course(String courseCode) {
        this.courseCode = courseCode;
        this.schedule = Schedule.generateDefaultMWFschedule();
        this.learningCentres = new HashSet<>();
    }

    // EFFECTS: returns course's code
    public String getCourseCode() {
        return this.courseCode;
    }

    // EFFECTS: returns course's meeting schedule
    public Schedule getSchedule() {
        return schedule;
    }

    // EFFECTS: returns course's learning centre
    public Set<LearningCentre> getLearningCentres() {
        return learningCentres;
    }

    // MODIFIES: this
    // EFFECTS: sets course's schedule to given one
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    // MODIFIES: this
    // EFFECTS: sets course's set of learning centres to given one
    public void setLearningCentres(Set<LearningCentre> learningCentres) {
        this.learningCentres = learningCentres;
    }

    // MODIFIES: this
    // EFFECTS: adds given learning centre to list if not already there
    public void addLC(LearningCentre lc) {
        if (!learningCentres.contains(lc)) {
            learningCentres.add(lc);
            lc.addCourse(this);
        } // FIXME when you add a learning centre it also needs to add it to the placemanager
    } // FIXME need to have a remove LC method - when you remove a course its learning centre should stop showing up
    // TODO FIX THIS--  in the list
    // TODO add equivalent of isOpen method -- isClassInSession

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }

        Course course = (Course) o;

        if (!courseCode.equals(course.courseCode)) {
            return false;
        }
        return schedule.equals(course.schedule);
    }

    @Override
    public int hashCode() {
        int result = courseCode.hashCode();
        result = 31 * result + schedule.hashCode();
        return result;
    }

    // TODO CHECK THIS
    @Override
    public String toString() {
        String returnedString = "Course{" + "courseCode='" + courseCode + "'"
                //+ "\n\t, schedule=" + schedule
                + "\n\t, learningCentres=" + InfoPrinter.printLearningCentres(this)
                + "}";
        InfoPrinter.printSchedule(schedule);
        return returnedString;
    }


    //    private LocalTime startTime;
//    private LocalTime endTime;
    // private Set<ScheduleEntry> meetTimes;

    /*this.schedule = new Schedule();
            Set<TimeBlock> monSessions = new HashSet<>();
            monSessions.add(new TimeBlock(startTime, endTime));
            Set<TimeBlock> wedSessions = new HashSet<>();
            wedSessions.add(new TimeBlock(startTime, endTime));
            Set<TimeBlock> friSessions = new HashSet<>();
            friSessions.add(new TimeBlock(startTime, endTime));
            schedule.getSchedule().put(MONDAY, monSessions);
            schedule.getSchedule().put(WEDNESDAY, wedSessions);
            schedule.getSchedule().put(FRIDAY, friSessions);*/
    //this.meetTimes = ScheduleEntry.generateMwfScheduleEntry(startTime, endTime);
    // this.startTime = startTime;
    // this.endTime = endTime;
    // }

    //        this.startTime = MIDNIGHT;
//        this.endTime = MIDNIGHT;
    //this.meetTimes = ScheduleEntry.generateMwfScheduleEntry(MIDNIGHT, MIDNIGHT);

    /*// EFFECTS: creates a course with given code and empty list of meeting times
    public Course(String courseCode, ScheduleEntry meetTime) {
        this.courseCode = courseCode;
        this.meetTimes = new ArrayList<>();
    }

    // EFFECTS: creates a course with given code and default meeting time 0-0 on Tuesday and Thursday,
    //          and no learning centres associated
    public Course(String courseCode) {
        this.courseCode = courseCode;
        this.meetTimes = new ArrayList<>();
        meetTimes.add(DEFAULT_MEET_TUE);
        meetTimes.add(DEFAULT_MEET_THU);
    }
    */

/*public Set<ScheduleEntry> getMeetTimes() {
        return meetTimes;
    }*/

    /*// EFFECTS: returns course's start time
    public LocalTime getStartTime() {
        return this.startTime;
    }

    // EFFECTS: returns course's end time
    public LocalTime getEndTime() {
        return this.endTime;
    }*/



    /*// MODIFIES: this
    // EFFECTS: sets course start time to newStartTime
    public void setStartTime(LocalTime newStartTime) {
        this.startTime = newStartTime;
    }

    // MODIFIES: this
    // EFFECTS: sets course end time to newEndTime
    public void setEndTime(LocalTime newEndTime) {
        this.endTime = newEndTime;
    }*/


/*// MODIFIES: this
    // EFFECTS: sets course meeting times to given list
    public void setMeetTimes(Set<ScheduleEntry> meetTimes) {
        this.meetTimes = meetTimes;
    }*/

    /*private String printLearningCentres() {
        StringBuilder allLCs = new StringBuilder();
        for (LearningCentre lc : learningCentres) {
            allLCs.append("\n").append(lc.getName());
        }
        return allLCs.toString();
    }*/
/*
    // MODIFIES: this
    // EFFECTS: adds given meeting time to list if not already there and returns true if successful, false otherwise
    public boolean addMeetTime(ScheduleEntry s) {
        if (!meetTimes.contains(s)) {
            meetTimes.add(s);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: changes given meeting time to new start/end times
    public boolean editMeetTime(DayOfWeek day, LocalTime oldStart, LocalTime oldEnd,
                                LocalTime newStart, LocalTime newEnd) {
        ScheduleEntry meetingToEdit = new ScheduleEntry(day, oldStart, oldEnd);
        // meetTimes.g
    }*/



/*@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }

        Course course = (Course) o;

        if (!courseCode.equals(course.courseCode)) {
            return false;
        }
        if (!startTime.equals(course.startTime)) {
            return false;
        }
        return endTime.equals(course.endTime);
    }

    @Override
    public int hashCode() {
        int result = courseCode.hashCode();
        result = 31 * result + startTime.hashCode();
        result = 31 * result + endTime.hashCode();
        return result;
    }*/
}
