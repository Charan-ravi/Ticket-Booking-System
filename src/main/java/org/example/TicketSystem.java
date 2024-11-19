package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//Inventory info
public class TicketSystem {
    //tickets info
    protected Map<Integer, Ticket> bookedTickets = new HashMap<>();
    protected Map<Integer, Ticket> canceledTickets = new HashMap<>();
    protected ConcurrentHashMap<Integer, Ticket> waitingList = new ConcurrentHashMap<>();

    //seats info
    protected int[] seatsAvailable = new int[5];

    //partial cancellation info
    protected Map<Integer, Integer> partiallyCanceled = new HashMap<>();

    protected int seatsBooked = 0;

    private static TicketSystem instance = null;

    private TicketSystem() {
        Arrays.fill(seatsAvailable, 8);
    }

    public static TicketSystem getInstance() {
        if (instance == null) {
            instance = new TicketSystem();
        }
        return instance;
    }

    protected int getSeatsBooked() {
        return seatsBooked;
    }

    protected void setSeatsBooked(int seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    public void addToBookedTickets(int newPnr, Ticket ticket) {
        bookedTickets.put(newPnr, ticket);
    }

    protected void decreaseSeatAvailability(char source, char destination, int seats) {
        for (int i = source - 'A'; i < destination - 'A'; i++) {
            seatsAvailable[i] -= seats;
        }
    }

    protected boolean checkSeatAvailability(char source, char destination, int seats) {
        for (int i = source - 'A'; i < destination - 'A'; i++) {
            if (seatsAvailable[i] < seats) {
                return false;
            }
        }
        return true;
    }

    //for cancellation
    protected Ticket getTicket(int pnr) {
        return bookedTickets.get(pnr);
    }

    protected void increaseSeatAvailability(char source, char destination, int seats) {
        for (int i = source - 'A'; i < destination - 'A'; i++) {
            seatsAvailable[i] += seats;
        }
    }

    protected void storePartiallyCanceledSeats(int pnr, int seats) {
        partiallyCanceled.merge(pnr, seats, Integer::sum);
    }

    protected void processCancellation(int pnr, Ticket ticket) {
        Integer getSeats = partiallyCanceled.get(pnr);
        int seatsToAdd = getSeats != null ? getSeats : 0;
        ticket.setSeats(ticket.getSeats() * seatsToAdd);
        addToCancelledQueue(pnr, ticket);
    }

    protected void addToCancelledQueue(int pnr, Ticket ticket) {
        ticket.setTicketStatus(TicketStatus.Canceled);
        canceledTickets.put(pnr, ticket);
        removeFromBookedQueue(pnr);
    }

    protected void removeFromBookedQueue(int pnr) {
        bookedTickets.remove(pnr);
    }

    //print chart info
    public void printChart() {
        System.out.println("\n Tickets Booked: ");
        bookedTickets.values().forEach(System.out::println);

        System.out.println("\n Tickets Cancelled: ");
        canceledTickets.values().forEach(System.out::println);

        System.out.println("\n Tickets in Waiting Lists: ");
        waitingList.values().forEach(System.out::println);

        System.out.println("\n Seats Availability : " + Arrays.toString(seatsAvailable));

        System.out.println("\n \t \t Seats Booked: ");
        System.out.println("\t 1 \t 2 \t 3 \t 4 \t 5 \t 6 \t 7 \t 8");
        for (char ch = 'A'; ch <= 'E'; ch++) {
            System.out.print(ch);
            int seatsBooked = 8 - seatsAvailable[ch - 'A'];

            for (int i = 0; i < seatsBooked; i++) {
                System.out.print("\t*");
            }
            System.out.println();
        }
        System.out.println();
    }
}
