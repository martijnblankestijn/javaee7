package nl.ordina.javaee7.server.websockets;

import javax.annotation.PostConstruct;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
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
    public void onOpen(Session peer) {
        log(" Adding peer with id " + peer.getId() + ", aantal open " + peer.getOpenSessions().size());

        sessions.add(peer);

        log("Added peer with id " + peer.getId() + ", aantal open " + peer.getOpenSessions().size());
    }

    @OnClose
    public void onClose(Session peer) {
        log("Removing peer " + peer);
        sessions.remove(peer);
    }

    @OnMessage
    public void message(@PathParam("inverterId") String inverterId, String message, Session client) {
        log("Got message from " + client + " for " + inverterId + ": " + message);
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
