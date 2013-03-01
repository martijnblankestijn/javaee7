package nl.ordina.javaee7.server;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
@ServerEndpoint(value = "/inverter/{inverterId}")
public class InverterDataEndpoint {
    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @PostConstruct
    public void init() {
        LOG.log(Level.INFO, "Starting up websocket endpoint");
    }
    @OnOpen
    public void onOpen(Session peer) {
        LOG.info("Adding peer " + peer);
        sessions.add(peer);
    }

    @OnClose
    public void onClose(Session peer) {
        LOG.info("Removing peer " + peer);
        sessions.remove(peer);
    }

    @OnMessage
    public void message(@PathParam("inverterId") String inverterId, String message, Session client) {
        LOG.info("Got message from " + client +" for " + inverterId + ": " + message);
        for (Session peer : sessions) {
            try {
                LOG.info("Sending message to " + peer);
                peer.getBasicRemote().sendText("Reply from server: " + message);
            } catch (IOException e) {
                System.out.println("IO Exception writing to " + peer + " .... ignoring");
            }
        }
    }
}
