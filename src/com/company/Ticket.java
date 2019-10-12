package com.company;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket {

    ///// declare Ticket Object Class variables /////
    private Integer vehicleID;
    private LocalDateTime vehicleCheckIn;
    private LocalDateTime vehicleCheckOut;
    private Float ticketAmount;
    private Float lostTicketAmount;

    ///// constructors /////

    public Ticket(Integer id, LocalDateTime in) {

        this.vehicleID = id;
        this.vehicleCheckIn = in;
    }


    public Ticket(String field, String field1, String field2, String field3, String field4) {

        //assumes every parking ticket will have vehicleID and vehicleCheckIn values (not null)
        //but not every ticket will have vehicleCheckOut, ticketAmount, lostTicketAmount values (can be null)
        this.vehicleID = Integer.parseInt(field);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        try {
            LocalDateTime timeField1 = LocalDateTime.parse(field1, formatter);
            this.vehicleCheckIn = timeField1;
            }
        catch (Exception e) {
            System.out.println("Vehicle: " + this.vehicleID + " has lost its ticket.");
            System.out.println("=================================================");
        }


        try {
                LocalDateTime timeField2 = LocalDateTime.parse(field2, formatter);
                this.vehicleCheckOut = timeField2;
            }
        catch (Exception e) {
            System.out.println("Vehicle: " + this.vehicleID + " has not checked out.");
            System.out.println("=================================================");
        }

        try {
                this.ticketAmount = Float.parseFloat(field3);
        }
        catch (Exception e) {
            System.out.println("Vehicle: " + this.vehicleID + " does not have a charge.");
            System.out.println("=================================================");
        }

        try {
            this.lostTicketAmount = Float.parseFloat(field4);
        }
        catch (Exception e) {
            System.out.println("Vehicle: " + this.vehicleID + " has yet to be lost.");
            System.out.println("=================================================");
        }

    }

    /*  Save everything as String and convert later
        LocalDateTime timeField2 = LocalDateTime.parse(fields[2], formatter);
        fields[3] = String.valueOf(fields[3]);
        fields[4] = String.valueOf(fields[4]);
    * */

    ///// setters /////
    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }

    public void setVehicleCheckIn(LocalDateTime vehicleCheckIn) {
        this.vehicleCheckIn = vehicleCheckIn;
    }

    public void setVehicleCheckOut(LocalDateTime vehicleCheckOut) {
        this.vehicleCheckOut = vehicleCheckOut;
    }

    public void setTicketAmount(Float ticketAmount) { this.ticketAmount = ticketAmount; }

    public void setLostTicketAmount(Float lostTicketAmount) { this.lostTicketAmount = lostTicketAmount; }

    ///// getters /////

    public Integer getVehicleID() { return vehicleID; }

    public LocalDateTime getVehicleCheckIn() {
        return vehicleCheckIn;
    }

    public LocalDateTime getVehicleCheckOut() {
        return vehicleCheckOut;
    }

    public Float getTicketAmount() { return ticketAmount; }

    public Float getLostTicketAmount() { return lostTicketAmount; }

    @Override
/*    public String toString() {
            return "Ticket:\n"
                    + "Ticket ID = " + vehicleID
                    + "\nCheck In = " + vehicleCheckIn
                    + "\nCheck Out = " + vehicleCheckOut
                    + "\nAmount = " + ticketAmount
                    + "\nLost Ticket Amount = " + lostTicketAmount;
    }*/

    public String toString() {
        return vehicleID+","+vehicleCheckIn+","+vehicleCheckOut+","+ticketAmount+","+lostTicketAmount;
    }

}
