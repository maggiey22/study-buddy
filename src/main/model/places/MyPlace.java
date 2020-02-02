package model.places;

import java.time.LocalTime;

public class MyPlace extends Place {

    public MyPlace(String placeName, LocalTime openTime, LocalTime closeTime) {
        super(placeName, openTime, closeTime);
    }
}
