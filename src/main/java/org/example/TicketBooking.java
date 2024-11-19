package org.example;

public class TicketBooking {
    private final char source;
    private final char destination;
    private final int seats;
    private final TicketSystem ticketSystem;

    TicketBooking (char source, char destination, int seats){
        this.source = source;
        this.destination = destination;
        this.seats = seats;
        this.ticketSystem = TicketSystem.getInstance();
    }

    private void bookTicket(){
        if(ticketSystem.checkSeatAvailability(source,destination,seats)){
            Ticket ticket = new Ticket(source, destination, seats, TicketStatus.Booked);
            int newPnr = ticket.getPnrNumber();
            ticketSystem.addToBookedTickets(newPnr,ticket);
            System.out.println("Ticket Booked! your number is "+newPnr);
            ticketSystem.decreaseSeatAvailability(source,destination,seats);
        }
        else {
            if (ticketSystem.seatsBooked + seats > 2){
                System.out.println("No tickets available from "+source+"-->"+destination);
            }
            else {
                WaitingListManager waitingListManager = new WaitingListManager();
                waitingListManager.waitingListEntry(source,destination,seats);
            }
        }
    }

    protected void execute(){
        this.bookTicket();
    }

    public TicketSystem getTicketSystem() {
        return ticketSystem;
    }

    public int getSeats() {
        return seats;
    }

    public char getDestination() {
        return destination;
    }

    public char getSource() {
        return source;
    }
}
