package ua.nure.kn.shalamov.usermanagement.agent;

import java.util.Objects;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class SearchRequestBehavior extends Behaviour {

    private AID[] aids;
    private String firstName;
    private String lastName;

    public SearchRequestBehavior(AID[] aid, String firstName, String lastName) {
        this.aids = aid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public void action() {
        if (Objects.nonNull(aids)) {
            ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
            message.setContent(firstName + "," + lastName);
            for (AID aid : aids) {
                message.addReceiver(aid);
            }
            myAgent.send(message);
        }
    }

    @Override
    public boolean done() {
        return true;
    }
}