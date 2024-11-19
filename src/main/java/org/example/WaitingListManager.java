package org.example;

public class WaitingListManager {
    private TicketSystem ticketSystem;

    WaitingListManager(){
        this.ticketSystem = TicketSystem.getInstance();
    }

    protected void processWaitingList(){
        for (Ticket waiting: ticketSystem.waitingList.values()){
            boolean isValid;
            char source = waiting.getSource(), destination = waiting.getDestination();
            int seats = waiting.getSeats();

            isValid = ticketSystem.checkSeatAvailability(source,destination,seats);
            if(isValid){
                ticketSystem.decreaseSeatAvailability(source,destination,seats);
                ticketSystem.setSeatsBooked(ticketSystem.getSeatsBooked()-seats);
                updateTicketToBookingList(waiting);
            }
        }
    }

    private void updateTicketToBookingList(Ticket waiting){
        int pnrNumber = waiting.getPnrNumber();
        waiting.setTicketStatus(TicketStatus.Booked);
        ticketSystem.addToBookedTickets(pnrNumber,waiting);
        ticketSystem.waitingList.remove(pnrNumber);
        System.out.println("Booking confirmed for the pnr number "+pnrNumber);
    }

    protected void waitingListEntry(char source, char destination,int seats){
        WaitingList waitingList = new WaitingList(source,destination,seats);
        waitingList.execute();
    }

    protected void waitingListRemoval(int pnr,int seatsToCancel, Ticket waitingListTicket){
        int seatsBooked = waitingListTicket.getSeats();
        if(seatsBooked == seatsToCancel){
            ticketSystem.waitingList.remove(pnr);
            System.out.println("Cancelled ticket in waiting list with pnr number "+pnr);
        }
        else {
            waitingListTicket.setSeats(waitingListTicket.getSeats()-seatsToCancel);
            System.out.println("Partially cancelled tickets in waiting list with pnr "+pnr);
        }
        ticketSystem.setSeatsBooked(ticketSystem.getSeatsBooked()-seatsToCancel);
    }
}
