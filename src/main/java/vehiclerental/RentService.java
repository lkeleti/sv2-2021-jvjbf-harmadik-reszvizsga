package vehiclerental;

import java.time.LocalTime;
import java.util.*;

public class RentService {
    private Set<Rentable> rentables = new HashSet<>();
    private Set<User> users = new HashSet<>();
    private Map<Rentable, User> actualRenting = new TreeMap<>();

    public Set<Rentable> getRentables() {
        return new HashSet<>(rentables);
    }

    public Set<User> getUsers() {
        return new HashSet<>(users);
    }

    public Map<Rentable, User> getActualRenting() {
        return new TreeMap<>(actualRenting);
    }

    public void registerUser(User user) {
        if (users.contains(user)) {
            throw new UserNameIsAlreadyTakenException("Username is taken!");
        } else {
            users.add(user);
        }
    }

    public void addRentable(Rentable rentable) {
        rentables.add(rentable);
    }

    public void rent(User user, Rentable rentable, LocalTime time) {
        if (rentable.getRentingTime() != null || rentable.calculateSumPrice(180L) > user.getBalance()) {
            throw new IllegalStateException("The vehicle already rented or not enough balance!");
        } else {
            rentable.rent(time);
            actualRenting.put(rentable, user);
        }
    }

    public void closeRent(Rentable rentable, int minutes) {
        if (!actualRenting.containsKey(rentable)) {
            throw new IllegalStateException("This vehicle has not been rented.");
        }

        if (minutes < 0) {
            throw new IllegalStateException("Invalid renting time.");
        }

        User actualUser = actualRenting.get(rentable);
        actualRenting.remove(rentable);
        rentable.closeRent();
        actualUser.minusBalance(rentable.calculateSumPrice(minutes));
    }
}
