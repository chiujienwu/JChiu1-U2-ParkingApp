package com.company;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

/*The purpose of this class is give a parked vehicle with check in time, a check out time and compute cost including lost
  ticekt*/

public class CheckOut {

    private LocalDateTime vehicleCheckOut;
    private float ticketAmount;
    private float lostTicketAmount = (float) 25.00;

    public CheckOut(LocalDateTime vehicleCheckIn) {

        Random randomNumbers = new Random();
        Integer randomNumber1 = randomNumbers.nextInt(11) + 13;
        Integer randomNumber2 = randomNumbers.nextInt(60);
        Integer randomNumber3 = randomNumbers.nextInt(60);
        vehicleCheckOut = LocalDateTime.of(2019, 9, 20, randomNumber1, randomNumber2, randomNumber3);
        ticketAmount = calcCost(timePeriod(vehicleCheckIn, this.vehicleCheckOut));
        lostTicketAmount = (float) 0.00;
    }

    public LocalDateTime getVehicleCheckOut() {
        return vehicleCheckOut;
    }

    public float getTicketAmount() {
        return ticketAmount;
    }

    public float getLostTicketAmount() {
        return lostTicketAmount;
    }

    public void setLostTicketAmount(Float lostTicketAmount) {
        this.lostTicketAmount = lostTicketAmount;
    }

    private float timePeriod (LocalDateTime in, LocalDateTime out) {

        float duration = (float) Duration.between(in, out).toMinutes()/60;
/*        double timeParked = Float.parseFloat(df.format(duration));*/
        System.out.print("Hours parked : ");
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println(df.format(duration));
        return duration;
    }

    private float calcCost (Float time) {

        float cost = (float) 0.00;

        if (time <= 3.00) {
            cost = (float) 5.00;
        } else if (time > 3.00) {
            double hours = Math.ceil(time);  // this is the culprit of convert back to int
            float total = (float) (5.00 + (hours - 3.00));
            if (total > 15.00) {
                cost = (float) 15.00;
            } else {
                cost = total;
            }
        }
        return cost;
    }

}
