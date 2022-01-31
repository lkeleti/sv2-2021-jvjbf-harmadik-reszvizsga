package vehiclerental;

import java.time.LocalTime;

public class Car implements Rentable{
    private String id;
    private LocalTime rentingTime;
    private int pricePerMinutes;

    public Car(String id, int pricePerMinutes) {
        this.id = id;
        this.pricePerMinutes = pricePerMinutes;
    }

    @Override
    public int calculateSumPrice(long minutes) {
        return (int)(pricePerMinutes*minutes);
    }

    @Override
    public LocalTime getRentingTime() {
        return rentingTime;
    }

    @Override
    public void rent(LocalTime time) {
        rentingTime = time;
    }

    @Override
    public void closeRent() {
        rentingTime = null;
    }
}
