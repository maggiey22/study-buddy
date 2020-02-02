package ui.loader;

import model.places.*;
import model.schedule.Course;
import model.schedule.CourseManager;

import java.time.LocalTime;
import java.util.HashSet;

import static java.time.DayOfWeek.*;

public class DefaultLoader {

    private static final String KOERNER_NAME = "Koerner Library";
    private static final String IKB_NAME = "Irving K. Barber Library";
    private static final String WOODWARD_NAME = "Woodward Library";
    private static final String ASIAN_NAME = "Asian Library";
    private static final String LAW_NAME = "Law Library";
    private static final String MLC_NAME = "Math Learning Centre";
    private static final String DEMCO_NAME = "Demco Learning Centre";
    private static final String CRC_NAME = "Chemistry Resource Centre";

    private static final String VANCOUVER = "Vancouver";
    private static final String BC = "BC";
    private static final String KOERNER_ST_ADDRESS = "1958 Main Mall";
    private static final String IKB_ST_ADDRESS = "1961 East Mall";
    private static final String WOODWARD_ST_ADDRESS = "2198 Health Sciences Mall";
    private static final String ASIAN_ST_ADDRESS = "1871 West Mall";
    private static final String LAW_ST_ADDRESS = "1822 East Mall";
    private static final String MLC_ST_ADDRESS = "6356 Agricultural Road";
    private static final String DEMCO_ST_ADDRESS = "2366 Main Mall";
    private static final String CRC_ST_ADDRESS = "2036 Main Mall";

    /*private static final String KOERNER_ADDRESS = "1958 Main Mall, Vancouver, BC";
    private static final String IKB_ADDRESS = "1961 East Mall, Vancouver, BC";
    private static final String WOODWARD_ADDRESS = "2198 Health Sciences Mall, Vancouver, BC";
    private static final String ASIAN_ADDRESS = "1871 West Mall, Vancouver, BC";
    private static final String LAW_ADDRESS = "1822 East Mall, Vancouver, BC";
    private static final String MLC_ADDRESS = "6356 Agricultural Road, Vancouver, BC";
    private static final String DEMCO_ADDRESS = "2366 Main Mall, Vancouver, BC";
    private static final String CRC_ADDRESS = "2036 Main Mall, Vancouver, BC";*/


    // EFFECTS: returns list of default libraries
    public LibraryManager loadLibraries() {
        Library koerner = new Library(KOERNER_NAME, LocalTime.of(7, 30), LocalTime.of(20, 0));
        correctKoernerSchedule(koerner);
//        koerner.getSchedule().removeDay(FRIDAY);
//        koerner.getSchedule().addNewDayAndMeetingTime(FRIDAY, LocalTime.of(7, 30), LocalTime.of(17, 0));
//        koerner.getSchedule().removeDay(SATURDAY);
//        koerner.getSchedule().addNewDayAndMeetingTime(SATURDAY, LocalTime.of(10, 0), LocalTime.of(17, 0));

        Library ikb = new Library(IKB_NAME, LocalTime.of(6, 0), LocalTime.of(1, 0));

        Library woodward = new Library(WOODWARD_NAME, LocalTime.of(9, 0),
                LocalTime.of(17, 0));
        correctWoodwardSchedule(woodward);

        Library asian = new Library(ASIAN_NAME, LocalTime.of(11, 0), LocalTime.of(17, 0));
        removeWeekendSessions(asian);

        Library law = new Library(LAW_NAME, LocalTime.of(9, 0), LocalTime.of(17, 0));
        removeWeekendSessions(law);

        setLibAddresses(koerner, ikb, woodward, asian, law);

        LibraryManager targetLibraries = new LibraryManager();
        targetLibraries.addPlace(koerner);
        targetLibraries.addPlace(ikb);
        targetLibraries.addPlace(woodward);
        targetLibraries.addPlace(asian);
        targetLibraries.addPlace(law);

        return targetLibraries;
    }

    private void removeWeekendSessions(Library library) {
        library.getSchedule().removeDay(SATURDAY);
        library.getSchedule().removeDay(SUNDAY);
    }

    private void correctWoodwardSchedule(Library woodward) {
        woodward.getSchedule().removeDay(SUNDAY);
        woodward.getSchedule().setMeetingTime(TUESDAY, LocalTime.of(9, 0),
                LocalTime.of(17, 0), LocalTime.of(9, 0), LocalTime.of(21, 0));
        woodward.getSchedule().setMeetingTime(WEDNESDAY, LocalTime.of(9, 0),
                LocalTime.of(17, 0), LocalTime.of(9, 0), LocalTime.of(21, 0));
        woodward.getSchedule().setMeetingTime(SATURDAY, LocalTime.of(9, 0),
                LocalTime.of(17, 0), LocalTime.of(10, 0), LocalTime.of(17, 0));
    }

    private void correctKoernerSchedule(Library koerner) {
        koerner.getSchedule().setMeetingTime(FRIDAY, LocalTime.of(7, 30),
                LocalTime.of(20, 0), LocalTime.of(7, 30), LocalTime.of(17, 0));
        koerner.getSchedule().setMeetingTime(SATURDAY, LocalTime.of(7, 30),
                LocalTime.of(20, 0), LocalTime.of(10, 0), LocalTime.of(17, 0));
        koerner.getSchedule().removeDay(SUNDAY);
    }
    /*// EFFECTS: returns list of default libraries
    public LibraryManager loadLibraries() {
        Library koerner = new Library(KOERNER_NAME, LocalTime.of(7, 30), LocalTime.of(20, 0));
        Library ikb = new Library(IKB_NAME, LocalTime.of(6, 0), LocalTime.of(1, 0));
        Library woodward = new Library(WOODWARD_NAME, LocalTime.of(9, 0),
                LocalTime.of(21, 0));
        Library asian = new Library(ASIAN_NAME, LocalTime.of(11, 0), LocalTime.of(17, 0));
        Library law = new Library(LAW_NAME, LocalTime.of(9, 0), LocalTime.of(17, 0));

        setLibAddresses(koerner, ikb, woodward, asian, law);

        LibraryManager targetLibraries = new LibraryManager();
        targetLibraries.addPlace(koerner);
        targetLibraries.addPlace(ikb);
        targetLibraries.addPlace(woodward);
        targetLibraries.addPlace(asian);
        targetLibraries.addPlace(law);

        return targetLibraries;
    }*/

    // EFFECTS: returns list of default courses
    public CourseManager loadCourses() {
        Course math200 = new Course("MATH200",
                LocalTime.of(10, 0),
                LocalTime.of(12, 0), new HashSet<>());
        Course cpsc210 = new Course("CPSC210",
                LocalTime.of(9, 0),
                LocalTime.of(12, 30), new HashSet<>());
        Course chem123 = new Course("CHEM123",
                LocalTime.of(15, 0),
                LocalTime.of(17, 0), new HashSet<>());

        CourseManager myCourses = new CourseManager();
        myCourses.addCourse(math200);
        myCourses.addCourse(cpsc210);
        myCourses.addCourse(chem123);

        return myCourses;
    }

    // REQUIRES: myCourses must contain the courses associated with the learning centres
    // EFFECTS: returns list of default learning centres
    public LearningCentreManager loadLearningCentres(CourseManager myCourses) {
        Course math200 = myCourses.getCourse("MATH200");
        Course cpsc210 = myCourses.getCourse("CPSC210");
        Course chem123 = myCourses.getCourse("CHEM123");

        LearningCentre mlc = new LearningCentre(MLC_NAME, LocalTime.of(10, 0),
                LocalTime.of(16, 0), new HashSet<>());
        LearningCentre demco = new LearningCentre(DEMCO_NAME, LocalTime.of(12, 0),
                LocalTime.of(15, 0), new HashSet<>());
        correctDemcoSchedule(demco);
        LearningCentre crc = new LearningCentre(CRC_NAME, LocalTime.of(9, 0),
                LocalTime.of(16, 0), new HashSet<>());

        setLCaddresses(mlc, demco, crc);

        mlc.addCourse(math200);
        demco.addCourse(cpsc210);
        crc.addCourse(chem123);

        LearningCentreManager myLCs = new LearningCentreManager();
        addAllLCs(mlc, demco, crc, myLCs);

        return myLCs;
    }

    private void addAllLCs(LearningCentre mlc, LearningCentre demco, LearningCentre crc, LearningCentreManager myLCs) {
        myLCs.addPlace(mlc);
        myLCs.addPlace(demco);
        myLCs.addPlace(crc);
    }

    private void correctDemcoSchedule(LearningCentre demco) {
        demco.getSchedule().setMeetingTime(THURSDAY, LocalTime.of(12, 0),
                LocalTime.of(15, 0), LocalTime.of(14, 30), LocalTime.of(16, 30));
        demco.getSchedule().setMeetingTime(FRIDAY, LocalTime.of(12, 0),
                LocalTime.of(15, 0), LocalTime.of(14, 0), LocalTime.of(18, 0));
        //TESTING
        demco.getSchedule().addMeetingTimeUnderExistingDay(FRIDAY, LocalTime.of(14, 30), LocalTime.of(16,30));
    }

    private void setLCaddresses(LearningCentre mlc, LearningCentre demco, LearningCentre crc) {
//        mlc.setAddress(MLC_ADDRESS);
//        demco.setAddress(DEMCO_ADDRESS);
//        crc.setAddress(CRC_ADDRESS);
        mlc.setAddress(new Address(MLC_ST_ADDRESS, VANCOUVER, BC));
        demco.setAddress(new Address(DEMCO_ST_ADDRESS, VANCOUVER, BC));
        crc.setAddress(new Address(CRC_ST_ADDRESS, VANCOUVER, BC));
    }

    private void setLibAddresses(Library koerner, Library ikb, Library woodward, Library asian, Library law) {
        koerner.setAddress(new Address(KOERNER_ST_ADDRESS, VANCOUVER, BC));
        ikb.setAddress(new Address(IKB_ST_ADDRESS, VANCOUVER, BC));
        woodward.setAddress(new Address(WOODWARD_ST_ADDRESS, VANCOUVER, BC));
        asian.setAddress(new Address(ASIAN_ST_ADDRESS, VANCOUVER, BC));
        law.setAddress(new Address(LAW_ST_ADDRESS, VANCOUVER, BC));
//        koerner.setAddress(KOERNER_ADDRESS);
//        ikb.setAddress(IKB_ADDRESS);
//        woodward.setAddress(WOODWARD_ADDRESS);
//        asian.setAddress(ASIAN_ADDRESS);
//        law.setAddress(LAW_ADDRESS);
    }
}
