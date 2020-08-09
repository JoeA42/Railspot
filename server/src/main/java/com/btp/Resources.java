package com.btp;

import com.btp.serverData.clientObjects.Ticket;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("resources")
public class Resources {


    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getResources() {
        return "Resources Main page. (WIP)";
    }

    @GET
    @Path("getTicket")
    @Produces(MediaType.APPLICATION_JSON)
    public Ticket getTicket(@QueryParam("ticketId") int ticketId){
        Ticket ticket = new Ticket();
        ticket.setUserId(117340941);
        ticket.setFrom("Testing");
        ticket.setTo("Test Complete");
        ticket.setDate("1/8/2020");
        ticket.setTicketNumber(1);

        return ticket;
    }
}
