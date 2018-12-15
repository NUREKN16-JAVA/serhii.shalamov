package ua.nure.kn.shalamov.usermanagement.gui;

import javax.swing.*;

import ua.nure.kn.shalamov.usermanagement.User;
import ua.nure.kn.shalamov.usermanagement.db.DatabaseException;
import ua.nure.kn.shalamov.usermanagement.gui.main.MainFrame;
import ua.nure.kn.shalamov.usermanagement.util.Messages;

import java.awt.*;
import java.text.ParseException;

public class UpdatePanel extends AbstractModifiedPanel {

    private static final String UPDATE_PANEL = "updatePanel";

    private User user;

    public UpdatePanel(MainFrame parent) {
        super(parent);
        this.setName(UPDATE_PANEL);
    }

    @Override
    protected String getConfirmButtonText() {
        return Messages.getString("editButton");
    }

    @Override
    protected void performAction() {
        user.setFirstName(getFirstNameField().getText());
        user.setLastName(getLastNameField().getText());
        try {
            user.setDateOfBirth(format.parse(getDateOfBirthField().getText()));
        } catch (ParseException e1) {
            getDateOfBirthField().setBackground(Color.RED);
            return;
        }
        try {
            parent.getUserDao().update(user);
        } catch (DatabaseException e1) {
            JOptionPane.showMessageDialog(this, e1.getMessage(), ERROR_TITLE,
                    JOptionPane.ERROR_MESSAGE);
        }
        user = null;
    }

    public void setUser(User user) {
        this.user = user;
        setFieldsValue(user);
    }

    private void setFieldsValue(User user) {
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        dateOfBirthField.setText(format.format(user.getDateOfBirth()));
    }
}
