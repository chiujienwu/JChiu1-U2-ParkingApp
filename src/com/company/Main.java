package com.company;

import java.time.LocalDateTime;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        //holds the ticket information for all vehicles
        ArrayList<Ticket> tickets = new ArrayList<>();
        //counter to issue the next vehicleID value
        Integer vehicleIDMax = 0;
        //variable to determine if garage is open or not for checkin/checkout service
        Boolean garageOpen = true;

        Scanner keyboard = new Scanner(System.in);
        String input;
        char response;
        String csvFileName = "C:\\Users\\JerryChiu\\Downloads\\JChiu1-U2-ParkingApp\\src\\com\\company\\garage_data.csv";

         //looking for existing data file of previous parking activity
        FileIn inData = new FileIn(csvFileName);

        //only if file exists and has data
        if (inData.getIn() != null && inData.getFileLength() != 0) {
            System.out.println("*************************************************");
            System.out.println("*****Reading in garage information from file*****");
            System.out.println("*************************************************");
            String line;
            while ((line = inData.fileReadLine()) != null) {
                String[] fields = line.split(",");
                //because a String array is used to pass in file data, a constructor of String type must be used
                tickets.add(new Ticket(fields[0], fields[1], fields[2], fields[3], fields[4]));
            }
            inData.fileClose();

            for (Ticket list: tickets) {
                System.out.println(list);
                System.out.println("=================================================");
                int e = list.getVehicleID();
                if (e > vehicleIDMax) {
                    vehicleIDMax = e;
                }
            }
        } else {
            System.out.println("No file found or file is empty!");
        }

        //there are two machines and neither have an menu option to toggle between machines
        //student IDE has only one console to simulate garage operations

        do {

            do {
                System.out.println("Best Value Parking Garage");
                System.out.println("=========================");
                System.out.println("1 – Check/In");
                System.out.println("2 – Check/Out");
                System.out.println("3 – Lost Ticket");
                System.out.println("9 – Close Garage");
                System.out.print("=> ");

                input = keyboard.nextLine();
                response = input.charAt(0);

                } while (response != '1' && response != '2' && response != '3' && response != '9');

            switch (response) {
                case '1':
                    CheckIn parkCar = new CheckIn(vehicleIDMax);
                    tickets.add(new Ticket(parkCar.getVehicleID(), parkCar.getVehicleCheckIn()));
                    System.out.println("Your ticket number : " + parkCar.getVehicleID());
                    System.out.println("Your check/In date/time : " + parkCar.getVehicleCheckIn());
                    vehicleIDMax = parkCar.getVehicleID();

                    for (Ticket list : tickets) {
                        System.out.println(list);
                        System.out.println("====================================");
                    }
                    break;
                case '2':
                    //ask for vehicleID (ticket number) in order to calculate the right amount of time
                    do {
                        System.out.println("Check/Out Process.....");
                        System.out.print("Enter your ticket ID : ");
                        Integer entry = keyboard.nextInt();
                        input = keyboard.nextLine();    //need to absorb the extra <CR>
                        System.out.println("You entered ID : " + entry);
                        System.out.println("Is this correct Y/N ?");
                        response = keyboard.nextLine().toUpperCase().charAt(0);

                        // search ArrayList ticket
                        System.out.println("Searching for ticket....");
                        boolean found = false;

                        for (Ticket list : tickets) {

                            /*
                            for error checking - used the following
                            System.out.println("Entry : " + entry);
                            System.out.println("List : " + list.getVehicleID());
                            */

                            if (entry == list.getVehicleID()) {
                                System.out.println("Ticket found!");
                                found = true;

                                //check to see if the ticket has already been paid for
                                //if not then checkout

                                if (list.getVehicleCheckOut() == null) {
                                    LocalDateTime timeIn = list.getVehicleCheckIn();
                                    CheckOut carOut = new CheckOut(timeIn);
                                    list.setVehicleCheckOut(carOut.getVehicleCheckOut());
                                    list.setTicketAmount(carOut.getTicketAmount());
                                    list.setLostTicketAmount(carOut.getLostTicketAmount());

                                    LocalDateTime timeOut = carOut.getVehicleCheckOut();
                                    float tAmount = carOut.getTicketAmount();
                                    float lAmount = carOut.getLostTicketAmount();
                                    System.out.println("Time out: " + timeOut);
                                    System.out.printf("Amount: $%.2f%n", tAmount);
                                    System.out.printf("Lost: $%.2f%n", lAmount);

                                    //write checkout record to file
                                    //it will replace the entire file and not append to existing

                                    FileOut outData = new FileOut(csvFileName);
                                    for (Ticket l : tickets)
                                    {
                                        outData.fileWrite(l.toString());
                                    }
                                    outData.fileClose();

                                    break;
                                } else {
                                    System.out.println("This ticket has already been checked out.");
                                }
                            }
                        }  // end of for loop searching for ticket to check out

                        if (found == false)
                        {
                            System.out.println("Ticket not found!");
                        }

                    } while (response != 'Y') ;
                    break;

                case '3':

                    //lost tickets are tickets without vehicleID or checkIn Times stamps
                    //write record to record lost ticket for possible later reconciliation
                    //odd ticket #,null,null,0.00,25.00
                    //odd ticket # is arbitrary to separate it from normal tickets

                    System.out.println("Very unfortunate.  The charge for lost ticket is $25.00");
                    FileOut outData = new FileOut(csvFileName);

                    for (Ticket l : tickets)
                    {
                        outData.fileWrite(l.toString());
                    }

                    String out = (++vehicleIDMax) + "," + null + "," + null + "," + null + ","  + (float) 25.00;
                    outData.fileWrite(out);
                    outData.fileClose();
                    ++vehicleIDMax;

                    break;

                case '9':

                    // the garage closes and a summary is printed
                    float checkInAmount = 0;
                    float lostTicketAmount = 0;
                    int countCheckIn = 0;
                    int lostTicketCount = 0;

                    for (Ticket list : tickets)
                    {

                        if (list.getTicketAmount() != null && list.getTicketAmount() != 0)
                        {
                            checkInAmount = checkInAmount + list.getTicketAmount();
                            countCheckIn++;
                        }

                        if (list.getLostTicketAmount() != null && list.getLostTicketAmount() != 0)
                        {
                            lostTicketAmount = lostTicketAmount + list.getLostTicketAmount();
                            lostTicketCount++;
                        }
                    }

                    System.out.println("Best Value Parking Garage");
                    System.out.println("=========================");
                    System.out.println("Activity to Date");
                    System.out.printf("$%.2f was collected from %d Check/Ins%n", checkInAmount, countCheckIn);
                    System.out.printf("$%.2f was collected from %d Check/Ins%n", lostTicketAmount, lostTicketCount);
                    System.out.printf("$%.2f was collected overall%n", (checkInAmount + lostTicketAmount));

                    garageOpen = false;
                    break;

                default:
                    garageOpen = false;
                    break;
            } // end of switch

        } while (garageOpen == true);  // end of main do loop

        System.out.println("Good bye!");

    } // end of Main Method

} // end of Main Class



