package model.schedule;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static java.time.DayOfWeek.*;
import static java.time.LocalTime.MIDNIGHT;

public class Schedule {

    private static final LocalTime DEFAULT_START_TIME = MIDNIGHT;
    private static final LocalTime DEFAULT_END_TIME = MIDNIGHT;
    public static final TimeBlock DEFAULT_TIME_BLOCK = new TimeBlock(DEFAULT_START_TIME, DEFAULT_END_TIME);

    private HashMap<DayOfWeek, Set<TimeBlock>> schedule;
    // private Set<TimeBlock>[] schedule;

    // should I make this a list instead?
    // array of sets instead??

    // EFFECTS: creates empty schedule
    public Schedule() {
        schedule = new HashMap<>();
//        schedule = new Set[7]; // FIXME ??????????????????
//        schedule = new Set<TimeBlock>[7];
    }

    // EFFECTS: creates a schedule with default meeting time scheduled on Monday, Wednesday, and Friday
    public static Schedule generateDefaultMWFschedule() {
        Schedule mwf = new Schedule();
        Set<TimeBlock> monSessions = new HashSet<>();
        Set<TimeBlock> wedSessions = new HashSet<>();
        Set<TimeBlock> friSessions = new HashSet<>();
        monSessions.add(DEFAULT_TIME_BLOCK);
        wedSessions.add(DEFAULT_TIME_BLOCK);
        friSessions.add(DEFAULT_TIME_BLOCK);
        mwf.getSchedule().put(MONDAY, monSessions);
        mwf.getSchedule().put(WEDNESDAY, wedSessions);
        mwf.getSchedule().put(FRIDAY, friSessions);
        return mwf;
    }

    // EFFECTS: creates a schedule with default meeting time scheduled on Tuesday and Thursday
    public static Schedule generateDefaultTThSchedule() {
        Schedule tth = new Schedule();
        Set<TimeBlock> tueSessions = new HashSet<>();
        Set<TimeBlock> thuSessions = new HashSet<>();
        tueSessions.add(DEFAULT_TIME_BLOCK);
        thuSessions.add(DEFAULT_TIME_BLOCK);
        tth.getSchedule().put(TUESDAY, tueSessions);
        tth.getSchedule().put(THURSDAY, thuSessions);
        return tth;
    }

    // EFFECTS: creates a schedule with the given times scheduled on Monday, Wednesday, and Friday
    public static Schedule generateMWFschedule(LocalTime start, LocalTime end) {
        Schedule mwf = new Schedule();
        Set<TimeBlock> monSessions = new HashSet<>();
        Set<TimeBlock> wedSessions = new HashSet<>();
        Set<TimeBlock> friSessions = new HashSet<>();
        monSessions.add(new TimeBlock(start, end));
        wedSessions.add(new TimeBlock(start, end));
        friSessions.add(new TimeBlock(start, end));
        mwf.getSchedule().put(MONDAY, monSessions);
        mwf.getSchedule().put(WEDNESDAY, wedSessions);
        mwf.getSchedule().put(FRIDAY, friSessions);
        return mwf;
    }

    // EFFECTS: creates a schedule with the given times scheduled on Tuesday and Thursday
    public static Schedule generateTThSchedule(LocalTime start, LocalTime end) {
        Schedule tth = new Schedule();
        Set<TimeBlock> tueSessions = new HashSet<>();
        Set<TimeBlock> thuSessions = new HashSet<>();
        tueSessions.add(new TimeBlock(start, end));
        thuSessions.add(new TimeBlock(start, end));
        tth.getSchedule().put(TUESDAY, tueSessions);
        tth.getSchedule().put(THURSDAY, thuSessions);
        return tth;
    }

    // EFFECTS: returns schedule
    public HashMap<DayOfWeek, Set<TimeBlock>> getSchedule() {
        return schedule;
    }

    // EFFECTS: returns set of time blocks for given day
    public Set<TimeBlock> getTimeBlocksForDay(DayOfWeek day) {
        return schedule.get(day);
    }

    // EFFECTS: returns time block with given start and end times on given day;
    //          returns null if not found
    public TimeBlock getTimeBlockByDayStartEnd(DayOfWeek day, LocalTime start, LocalTime end) {
        Set<TimeBlock> sessions = getTimeBlocksForDay(day);
        if (sessions == null) {
            return null;
        }
        TimeBlock wantedTB = new TimeBlock(start, end);
        for (TimeBlock t : sessions) {
            if (t.equals(wantedTB)) { //if (t.getStartTime().equals(start) && t.getEndTime().equals(end)) {
                return t;
            }
        }
        return null;
    }

    // REQUIRES: schedule cannot be null
    // MODIFIES: this
    // EFFECTS: sets schedule to given one
    public void setSchedule(HashMap<DayOfWeek, Set<TimeBlock>> schedule) {
        this.schedule = schedule;
    }

    // MODIFIES: this
    // EFFECTS: deletes all meeting times for given day
    public void removeDay(DayOfWeek day) {
        schedule.remove(day);
    }

    // MODIFIES: this
    // EFFECTS: if day is not already there, adds given day and meeting session with given times on that day
    //          and returns true; returns false otherwise
    public boolean addNewDayAndMeetingTime(DayOfWeek day, LocalTime start, LocalTime end) {
        if (this.schedule.containsKey(day)) {
            return false;
        }
        TimeBlock session = new TimeBlock(start, end);
        Set<TimeBlock> sessions = new HashSet<>();
        sessions.add(session);
        this.schedule.put(day, sessions);
        return true;
    }

    // MODIFIES: this
    // EFFECTS: if session is not already under given day but day exists in schedule, adds session with
    //          given times to given day and returns true; returns false otherwise
    public boolean addMeetingTimeUnderExistingDay(DayOfWeek day, LocalTime start, LocalTime end) {
        TimeBlock session = new TimeBlock(start, end);
        if (isSessionUnderDay(day, session) || !schedule.containsKey(day)) {
            return false;
        }
        this.getTimeBlocksForDay(day).add(session);
        return true;
    }

    // MODIFIES: this
    // EFFECTS: sets time block on given day with given times to new given times and returns true;
    //          false if time block was not found
    public boolean setMeetingTime(DayOfWeek day, LocalTime oldStart, LocalTime oldEnd,
                                  LocalTime newStart, LocalTime newEnd) {
        if (!isSessionUnderDay(day, new TimeBlock(oldStart, oldEnd))) { //FIXME - not working for SB call site
            return false;
        } else {
            TimeBlock tb = getTimeBlockByDayStartEnd(day, oldStart, oldEnd);
            tb.setStartTime(newStart);
            tb.setEndTime(newEnd);
            System.out.println("Times on " + day + " changed to " + newStart + "-" + newEnd);
            return true;
        }
    }

    // EFFECTS: if given meeting session is under given day, return true;
    //          false otherwise
    public boolean isSessionUnderDay(DayOfWeek day, TimeBlock tb) {
        Set<TimeBlock> sessions = getTimeBlocksForDay(day);
        if (sessions == null) {
            return false;
        }
        for (TimeBlock t : sessions) {
            if (t.equals(tb)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns true if given time is during any of the day's sessions; false otherwise
    public boolean isTimeDuringASession(DayOfWeek day, LocalTime time) {
        Set<TimeBlock> sessions = getTimeBlocksForDay(day);
        if (sessions == null) {
            return false;
        } else {
            for (TimeBlock t : sessions) {
                boolean there = t.isItBetween(time);
                if (there) {
                    return true;
                }
            }
            return false;
        }
    }

    public String allSessionsForDayToString(DayOfWeek day) {
        StringBuilder sessions = new StringBuilder();
        if (getTimeBlocksForDay(day) == null) {
            return "";
        }
        for (TimeBlock t : getTimeBlocksForDay(day)) {
            sessions.append("\n\t").append(t.getStartTime()).append("-").append(t.getEndTime());
        }
        return sessions.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Schedule)) {
            return false;
        }

        Schedule schedule1 = (Schedule) o;

        return schedule.equals(schedule1.schedule);
    }

    @Override
    public int hashCode() {
        return schedule.hashCode();
    }

    /*
    Schedule mwf = new Schedule();
        Set<TimeBlock> sessions = new HashSet<>();
        sessions.add(DEFAULT_TIME_BLOCK);
        mwf.getSchedule().put(MONDAY, sessions);
        mwf.getSchedule().put(WEDNESDAY, sessions);
        mwf.getSchedule().put(FRIDAY, sessions);
        return mwf;
     */

     /*
    Schedule tth = new Schedule();
        Set<TimeBlock> sessions = new HashSet<>();
        sessions.add(DEFAULT_TIME_BLOCK);
        tth.getSchedule().put(TUESDAY, sessions);
        tth.getSchedule().put(THURSDAY, sessions);
        return tth;
     */

     /*Schedule mwf = new Schedule(); // when running sb, still changes all of them
        Set<TimeBlock> monSessions = new HashSet<>();
        Set<TimeBlock> wedSessions = new HashSet<>(); //
        Set<TimeBlock> friSessions = new HashSet<>();
        TimeBlock tb = new TimeBlock(start, end);
        monSessions.add(tb);
        wedSessions.add(tb);
        friSessions.add(tb);
        mwf.getSchedule().put(MONDAY, monSessions);
        mwf.getSchedule().put(WEDNESDAY, wedSessions);
        mwf.getSchedule().put(FRIDAY, friSessions);
        return mwf;*/

    /*Schedule mwf = new Schedule(); // when running sb, changes all of them
        Set<TimeBlock> sessions = new HashSet<>();
        sessions.add(new TimeBlock(start, end));
        mwf.getSchedule().put(MONDAY, sessions);
        mwf.getSchedule().put(WEDNESDAY, sessions);
        mwf.getSchedule().put(FRIDAY, sessions);
        return mwf;*/

    /*
        Schedule tth = new Schedule();
        Set<TimeBlock> sessions = new HashSet<>();
        sessions.add(new TimeBlock(start, end));
        tth.getSchedule().put(TUESDAY, sessions);
        tth.getSchedule().put(THURSDAY, sessions);
        return tth;
     */

    /*schedule.forEach((k,v) -> {
            System.out.println(k + ": " + allSessionsForDayToString(k));
        });*/

    /* //cant figure out how to access the acc outside of foreach loop
            String acc = "";
            String day = k + ": " + allSessionsForDayToString(k);
            acc = acc + "\n" + day;
            System.out.println(day);
     */

    /*public String printSchedule() {
        String acc = "";
        if (schedule.containsKey(MONDAY)) {
            acc = acc + "MONDAY:" + allSessionsForDayToString(MONDAY);
        }
        if (schedule.containsKey(TUESDAY)) {
            acc = acc + "\nTUESDAY:" + allSessionsForDayToString(TUESDAY);
        }
        if (schedule.containsKey(WEDNESDAY)) {
            acc = acc + "\nWEDNESDAY:" + allSessionsForDayToString(WEDNESDAY);
        }
        if (schedule.containsKey(THURSDAY)) {
            acc = acc + "\nTHURSDAY:" + allSessionsForDayToString(THURSDAY);
        }
        if (schedule.containsKey(FRIDAY)) {
            acc = acc + "\nFRIDAY:" + allSessionsForDayToString(FRIDAY);
        }
        System.out.println(acc);
        return acc;
    }*/
}