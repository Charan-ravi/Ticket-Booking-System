package org.example;

public class Ticket {
    private static int pnrGenerator = 1;
    private final int pnrNumber = pnrGenerator++;
    private final char source;
    private final char destination;
    private int seats;
    private TicketStatus ticketStatus;

    Ticket(char source, char destination, int seats, TicketStatus ticketStatus) {
        this.source = source;
        this.destination = destination;
        this.seats = seats;
        this.ticketStatus = ticketStatus;
    }

    public int getPnrNumber() {
        return pnrNumber;
    }

    public char getSource() {
        return source;
    }

    public char getDestination() {
        return destination;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
}
