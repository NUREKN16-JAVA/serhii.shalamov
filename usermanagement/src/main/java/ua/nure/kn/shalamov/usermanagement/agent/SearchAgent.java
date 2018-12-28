package ua.nure.kn.shalamov.usermanagement.agent;

import java.util.Arrays;
import java.util.Collection;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import ua.nure.kn.shalamov.usermanagement.User;
import ua.nure.kn.shalamov.usermanagement.db.DaoFactory;
import ua.nure.kn.shalamov.usermanagement.db.DatabaseException;

public class SearchAgent extends Agent {

    private AID[] aids;
    private SearchGui searchGui;

    @Override
    protected void setup() {
        super.setup();
        searchGui = new SearchGui(this);
        searchGui.setVisible(true);
        System.out.println(getAID().getName() + " started");

        DFAgentDescription description = new DFAgentDescription();
        description.setName(getAID());
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setName("JADE-searching");
        serviceDescription.setType("searching");
        description.addServices(serviceDescription);

        try {
            DFService.register(this, description);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        addBehaviour(new TickerBehaviour(this, 60000) {
            @Override
            protected void onTick() {
                DFAgentDescription agentDescription = new DFAgentDescription();
                ServiceDescription serviceDescription1 = new ServiceDescription();
                serviceDescription1.setType("searching");
                agentDescription.addServices(serviceDescription1);
                try {
                    DFAgentDescription[] descriptions = DFService.search(myAgent, agentDescription);
                    AID selfAid = getAID();
                    aids = Arrays.stream(descriptions)
                            .map(DFAgentDescription::getName)
                            .filter(aid -> !selfAid.equals(aid))
                            .toArray(AID[]::new);
                } catch (FIPAException e) {
                    e.printStackTrace();
                }
            }
        });

        addBehaviour(new RequestServer());
    }

    @Override
    protected void takeDown() {
        System.out.println(getAID().getName() + " terminated");
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        searchGui.setVisible(false);
        searchGui.dispose();
        super.takeDown();
    }

    public void search(String firstName, String lastName) throws SearchException {
        try {
            Collection<User> users = DaoFactory.getInstance().getUserDao().find(firstName, lastName);
            if (users.size() > 0) {
                showUsers(users);
            } else {
                addBehaviour(new SearchRequestBehavior(aids, firstName, lastName));
            }
        } catch (DatabaseException e) {
            throw new SearchException(e);
        }
    }

    void showUsers(Collection<User> users) {
        searchGui.addUsers(users);
    }
}
