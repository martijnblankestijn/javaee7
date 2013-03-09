package nl.ordina.javaee7.server.websockets;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 *
 */
@ServerEndpoint(value = "/inverter/{inverterId}")
public class InverterDataEndpoint {
    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    // Sessions should static to share with different clients
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    public InverterDataEndpoint() {
        log("ctor");
    }

    @PostConstruct
    public void init() {
        log("Starting up websocket endpoint");
    }

    @OnOpen
    public void onOpen(Session client) {
        log(" Adding client with id " + client.getId() + ", aantal open " + client.getOpenSessions().size());

        sessions.add(client);
        sendMessageOnlyToOthers(client.getId() + " just connected, secure:  " + client.isSecure(), client);
        log("Added client with id " + client.getId() + ", aantal open " + client.getOpenSessions().size());
    }

    @OnClose
    public void onClose(Session client) {
        log("Removing client " + client);
        sessions.remove(client);
        sendMessageOnlyToOthers(client.getId() + " just left the building", client);
    }

    @OnMessage
    public void message(@PathParam("inverterId") String inverterId, String message, Session client) {
        log("Got message from " + client + " for " + inverterId + ": " + message);
        sendMessageOnlyToOthers(message, client);
    }

    @OnError
    public void onError(Throwable throwable, Session client) {
        log("Got error from " + client);
    }

    private void sendMessageOnlyToOthers(String message, Session client) {
        for (Session peer : sessions) {
            // broadcast only to the other clients
            if (peer.equals(client)) {
                System.out.println("Client equals found session, will not echo!");
            } else {
                try {
                    log("Sending message to " + peer);
                    peer.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    System.out.println("IO Exception writing to " + peer + " .... ignoring");
                }
            }
        }
    }

    private void log(String msg) {
        LOG.info("InverterDataEndpoint " + hashCode() + " - " + msg);
    }

}
