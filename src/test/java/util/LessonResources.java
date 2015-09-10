package util;

import rx.Observable;

import java.util.ArrayList;
import java.util.List;

public class LessonResources {

    public static class ElevatorPassenger {
        private String mName;

        public int getWeightInPounds() {
            return mWeightInPounds;
        }

        public String getName() {
            return mName;
        }

        public int mWeightInPounds;

        public ElevatorPassenger(String name, int weightInPounds) {
            mName = name;
            mWeightInPounds = weightInPounds;
        }

        public String toString() {
            return "ElevatorPassenger{" +
                    "mName='" + mName + '\'' +
                    ", mWeightInPounds=" + mWeightInPounds +
                    '}';
        }
    }


    public static class ComcastNetworkAdapter {
        private int mAttempts;

        public List<String> getData() {
            if (mAttempts < 42) {
                mAttempts++;
                System.out.println("network issues!! please reboot your computer!");
                return null;
            }
            ArrayList<String> data = new ArrayList<>();
            data.add("extremely important data");
            System.out.println("transmitting data!");
            return data;
        }
    }


    public static class Elevator {
        public static final int MAX_CAPACITY_POUNDS = 500;
        List<ElevatorPassenger> mPassengers = new ArrayList<>();

        public void addPassenger(ElevatorPassenger passenger) {
            mPassengers.add(passenger);
        }

        public int getTotalWeightInPounds() {
            return Observable.from(mPassengers).reduce(0, (accumulatedWeight, elevatorPassenger) ->
                    elevatorPassenger.mWeightInPounds + accumulatedWeight)
                    .toBlocking().last();
        }

        public List<ElevatorPassenger> getPassengers() {
            return mPassengers;
        }

        public int getPassengerCount() {
            return mPassengers.size();
        }

        @Override
        public String toString() {
            return "Elevator{" +
                    "mPassengers=" + mPassengers + "\n" +
                    "totalWeight=" + getTotalWeightInPounds() +
                    '}';
        }

        public void unload() {
            mPassengers = new ArrayList<>();
        }
    }

    //A Carnival Food Object...
    public static class CarnivalFood {
        private String mName;
        public Double mPrice;

        public CarnivalFood(String name, Double price) {
            mName = name;
            mPrice = price;
        }

        @Override
        public String toString() {
            return "Food{" +
                    "mName='" + mName + '\'' +
                    ", mPrice=" + mPrice +
                    "\n}";
        }
    }


}
