package org.example;

public class TicketCanceling {
    private final int seats;
    private final int pnr;
    private final TicketSystem ticketSystem;

    TicketCanceling(int pnr, int seats){
        this.pnr= pnr;
        this.seats = seats;
        this.ticketSystem= TicketSystem.getInstance();
    }

    private void cancelTicket(){
        Ticket ticket = ticketSystem.getTicket(pnr);
        WaitingListManager waitingListManager= new WaitingListManager();

        if(ticket!= null){
            if(ticket.getTicketStatus() == TicketStatus.WaitingList){
                waitingListManager.waitingListRemoval(pnr,seats,ticket);
                return;
            }
            int bookedSeats = ticket.getSeats();
            char source = ticket.getSource(),destination = ticket.getDestination();

            if(bookedSeats>seats){
                ticket.setSeats(bookedSeats-seats);
                ticketSystem.storePartiallyCanceledSeats(pnr,seats);
                System.out.println("Partially Cancelled pnr "+pnr);
            }
            else {
                ticketSystem.processCancellation(pnr,ticket);
                System.out.println("Cancelled ticket pnr "+pnr);
            }
            ticketSystem.increaseSeatAvailability(source,destination,seats);
            waitingListManager.processWaitingList();
        }
        else {
            System.out.println("Ticket with pnr "+pnr+" not found");
        }
    }

    protected void execute() {
        this.cancelTicket();
    }
}
