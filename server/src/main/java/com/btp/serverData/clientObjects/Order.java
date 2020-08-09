package com.btp.serverData.clientObjects;

/**
 * This class represents an order for an user purchasing tickets
 */
public class Order {
    private int ticketAmount;
    private int userId;
    private int cost;

    /**
     * Getter for the ticketAmount attribute
     * @return int the number of tickets being bought
     */
    public int getTicketAmount() {
        return ticketAmount;
    }

    /**
     * Setter for the ticketAmount attribute
     * @param ticketAmount int the number of tickets being bought
     */
    public void setTicketAmount(int ticketAmount) {
        this.ticketAmount = ticketAmount;
    }

    /**
     * Getter for the userId attribute
     * @return int the id of the user making the order
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter for the userId attribute
     * @param userId the id of the user making the order
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter for the cost attribute
     * @return int the total cost of the order
     */
    public int getCost() {
        return cost;
    }

    /**
     * Setter for the cost attribute
     * @param cost int the total cost of the order
     */
    public void setCost(int cost) {
        this.cost = cost;
    }
}
