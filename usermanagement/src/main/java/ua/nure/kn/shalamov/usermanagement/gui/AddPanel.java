package ua.nure.kn.shalamov.usermanagement.gui;

import javax.swing.*;

import ua.nure.kn.shalamov.usermanagement.User;
import ua.nure.kn.shalamov.usermanagement.db.DatabaseException;
import ua.nure.kn.shalamov.usermanagement.gui.main.MainFrame;
import ua.nure.kn.shalamov.usermanagement.util.Messages;

import java.awt.*;
import java.text.ParseException;

public class AddPanel extends AbstractModifiedPanel {


    private static final String ADD_PANEL = "addPanel";

    public AddPanel(MainFrame parent) {
        super(parent);
        this.setName(ADD_PANEL);
    }


    @Override
    protected void performAction() {
        User user = new User();
        user.setFirstName(getFirstNameField().getText());
        user.setLastName(getLastNameField().getText());
        try {
            user.setDateOfBirth(format.parse(getDateOfBirthField().getText()));
        } catch (ParseException e1) {
            getDateOfBirthField().setBackground(Color.RED);
            return;
        }
        try {
            parent.getUserDao().create(user);
        } catch (DatabaseException e1) {
            JOptionPane.showMessageDialog(this, e1.getMessage(), ERROR_TITLE,
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    @Override
    protected String getConfirmButtonText() {
        return Messages.getString("addButton");
    }

}
