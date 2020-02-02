package model.schedule;

import java.time.LocalTime;

import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIDNIGHT;

public class TimeBlock {

    private LocalTime startTime;
    private LocalTime endTime;

    // INVARIANT: endTime is after startTime - for time blocks in which a day passes (eg 6AM to 1AM) the
    //            endTime is the next day; will need to split into 0600-MAX && MIDNIGHT-0100
    // EFFECTS: creates timeblock with given start and end times
    public TimeBlock(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // EFFECTS: returns timeblock's start time
    public LocalTime getStartTime() {
        return startTime;
    }

    // EFFECTS: returns timeblock's end time
    public LocalTime getEndTime() {
        return endTime;
    }

    // MODIFIES: this
    // EFFECTS: sets timeblock's start time to given time
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    // MODIFIES: this
    // EFFECTS: sets timeblock's end time to given time
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /*// EFFECTS: returns true if given timeblock conflicts with this one (either same exact time or have overlap);
    //          false otherwise
    public boolean doesItConflict(TimeBlock tb) {
        if (tb == null) {
            return false;
        } else {
            return (this.equals(tb))
                    || (tb.startTime.isAfter(this.startTime) && tb.startTime.isBefore(this.endTime))
                    || (tb.endTime.isAfter(this.startTime) && tb.endTime.isBefore(this.endTime))
                    || (tb.startTime.isBefore(this.startTime) && tb.endTime.isAfter(this.endTime))
                    || (tb.startTime.isBefore(this.startTime) && tb.endTime.equals(this.endTime))
                    || (tb.startTime.equals(this.startTime) && tb.endTime.isAfter(this.endTime));
        }
    }*/

    // Problem with midnight :(
    public boolean isItBetween(LocalTime time) {
        //return time.isAfter(startTime) && time.isBefore(endTime);
        if (endTime.isBefore(startTime)) {
            return (time.isAfter(startTime) && time.isBefore(MAX))
                    || (time.isAfter(MIDNIGHT) && time.isBefore(endTime))
                    || (time.equals(MIDNIGHT)) || (time.equals(MAX));
        } else {
            return time.isAfter(startTime) && time.isBefore(endTime);
        }
    } //todo: add list method in Schedule that calls this

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeBlock)) {
            return false;
        }

        TimeBlock timeBlock = (TimeBlock) o;

        if (!startTime.equals(timeBlock.startTime)) {
            return false;
        }
        return endTime.equals(timeBlock.endTime);
    }

    @Override
    public int hashCode() {
        int result = startTime.hashCode();
        result = 31 * result + endTime.hashCode();
        return result;
    }
}
