package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Book Ticket \n2. Cancel Ticket \n3. Print Chart \n Pick any one :) ... ");
            Scanner scan = new Scanner(System.in);
            int option = scan.nextInt();

            switch (option) {
                case 1 -> {
                    System.out.println("Enter the source : ");
                    char source = scan.next().charAt(0);
                    System.out.println("Enter the destination : ");
                    char destination = scan.next().charAt(0);
                    System.out.println("Enter the number of seats book : ");
                    int seats = scan.nextInt();

                    if (source != destination) {
                        TicketBooking booking = new TicketBooking(source, destination, seats);
                        booking.execute();
                    } else {
                        System.out.println("I handled this scenario. Try again!");
                    }
                    break;
                }
                case 2 -> {
                    System.out.println("Enter the pnr number : ");
                    int pnr = scan.nextInt();
                    System.out.println("Enter no of seats to cancel : ");
                    int seats = scan.nextInt();

                    TicketCanceling canceling = new TicketCanceling(pnr, seats);
                    canceling.execute();
                    break;
                }
                case 3 -> {
                    TicketSystem.getInstance().printChart();
                    break;
                }
                default -> {
                    System.out.println("Unfortunately stopped! :/");
                    break;
                }
            }
        }
    }
}