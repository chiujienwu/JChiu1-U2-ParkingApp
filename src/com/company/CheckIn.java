package com.company;

import java.time.LocalDateTime;
import java.util.Random;

/*The purpose of this class is to validate a ticket ID and give it a time stamp */

public class CheckIn {

    private Integer vehicleID;
    private LocalDateTime vehicleCheckIn;

    public CheckIn(Integer lastID) {
        this.vehicleID = lastID + 2;
        Random randomNumbers = new Random();
        Integer randomNumber1 = randomNumbers.nextInt(5) + 7;
        Integer randomNumber2 = randomNumbers.nextInt(61);
        Integer randomNumber3 = randomNumbers.nextInt(61);
        this.vehicleCheckIn = LocalDateTime.of(2019, 9, 20, randomNumber1, randomNumber2, randomNumber3);;
    }

    public Integer getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }

    public LocalDateTime getVehicleCheckIn() {
        return vehicleCheckIn;
    }

    public void setVehicleCheckIn(LocalDateTime vehicleCheckIn) {
        this.vehicleCheckIn = vehicleCheckIn;
    }
}
