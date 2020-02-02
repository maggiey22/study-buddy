package model.places;

import model.schedule.Course;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class LearningCentre extends Place {

    private static final LocalTime DEFAULT_OPEN = LocalTime.of(9, 0);
    private static final LocalTime DEFAULT_CLOSE = LocalTime.of(16, 30);

    private transient Set<Course> courses;

    // EFFECTS: creates a learning centre with given open hours, associated with given courses
    public LearningCentre(String lcName, LocalTime openTime, LocalTime closeTime,
                          Set<Course> courses) {
        super(lcName, openTime, closeTime);
        this.courses = courses;
    }

    // EFFECTS: creates a learning centre with given name and default open hours
    //          and no courses associated
    public LearningCentre(String lcName) {
        super(lcName, DEFAULT_OPEN, DEFAULT_CLOSE);
        this.courses = new HashSet<>();
    }

    // EFFECTS: returns set of courses that are associated with this lc
    //          null if no courses associated
    public Set<Course> getCourses() {
        return this.courses;
    }

    // MODIFIES: this
    // EFFECTS: sets learning centre's set of courses to given one
    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    // MODIFIES: this
    // EFFECTS: adds given course to list if not already there
    public void addCourse(Course c) {
        if (!courses.contains(c)) {
            courses.add(c);
            c.addLC(this);
        }
    } // FIXME: adding a course should add it to the coursemanager (observer pattern)

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LearningCentre)) {
            return false;
        }

        Place place = (LearningCentre) o;

        if (!placeName.equals(place.placeName)) {
            return false;
        }
//        if (!openTime.equals(place.openTime)) {
//            return false;
//        }
        //return closeTime.equals(place.closeTime);
        return schedule.equals(place.schedule);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LearningCentre)) return false;
        if (!super.equals(o)) return false;

        LearningCentre that = (LearningCentre) o;

        return courses.equals(that.courses);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + courses.hashCode();
        return result;
    }*/



/*    @Override
    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof LearningCentre)) return false;
//        if (!super.equals(o)) return false;
//
//        LearningCentre that = (LearningCentre) o;
//
//        return courses.equals(that.courses);
        return super.equals(o);
    }

    @Override
    public int hashCode() {
//        int result = super.hashCode();
//        result = 31 * result + courses.hashCode();
//        return result;
        return super.hashCode();
    }*/
}
