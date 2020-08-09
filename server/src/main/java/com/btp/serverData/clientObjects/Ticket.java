package com.btp.serverData.clientObjects;

/**
 * This class represents a ticket for a train route
 */
public class Ticket {
    private int userId;
    private String from;
    private String to;
    private int ticketNumber;
    private String date;

    /**
     * Getter for the userId attribute
     * @return int the id of the person that bought the ticket
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter for the userId attribute
     * @param userId int the id of the person that bought the ticket
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter for the from attribute
     * @return String the destination the ticket starts on
     */
    public String getFrom() {
        return from;
    }

    /**
     * Setter for the from attribute
     * @param from String the destination the ticket starts on
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Getter for the to attribute
     * @return String the destination the ticket ends on
     */
    public String getTo() {
        return to;
    }

    /**
     * Setter for the to attribute
     * @param to String the destination the ticket ends on
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Getter for the ticketNumber attribute
     * @return int the id for the ticket
     */
    public int getTicketNumber() {
        return ticketNumber;
    }

    /**
     * Getter for the ticketNumber attribute
     * @param ticketNumber int the id for the ticket
     */
    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    /**
     * Getter for the date attribute
     * @return String the date the ticket can be used
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter for the date attribute
     * @param date the date the ticket can be used
     */
    public void setDate(String date) {
        this.date = date;
    }
}
