package ua.nure.kn.shalamov.usermanagement.gui.main;

import ua.nure.kn.shalamov.usermanagement.User;
import ua.nure.kn.shalamov.usermanagement.db.DaoFactory;
import ua.nure.kn.shalamov.usermanagement.db.UserDao;
import ua.nure.kn.shalamov.usermanagement.gui.*;
import ua.nure.kn.shalamov.usermanagement.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MainFrame extends JFrame {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;

    private JPanel contentPanel;
    private JPanel browsePanel;
    private AbstractModifiedPanel addPanel;
    private DetailsPanel detailsPanel;
    private AbstractModifiedPanel updatePanel;
    private UserDao userDao;

    public MainFrame() throws HeadlessException {
        super();
        userDao = DaoFactory.getInstance().getUserDao();
        initialize();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    private void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle(Messages.getString("userManagement"));
        this.setContentPane(getContentPanel());
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    private JPanel getContentPanel() {
        if (Objects.isNull(contentPanel)) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
        }
        return contentPanel;
    }

    private JPanel getBrowsePanel() {
        if (Objects.isNull(browsePanel)) {
            browsePanel = new BrowsePanel(this);
        }
        ((BrowsePanel) browsePanel).initTable();
        return browsePanel;
    }

    public void showAddPanel() {
        showPanel(getAddPanel());
    }

    private void showPanel(JPanel panel) {
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setVisible(true);
        panel.repaint();
    }

    private AbstractModifiedPanel getAddPanel() {
        if (Objects.isNull(addPanel)) {
            addPanel = new AddPanel(this);
        }
        return addPanel;
    }

    public void showBrowsePanel() {
        showPanel(getBrowsePanel());
    }

    public void showDetailsPanel(User user) {
        JPanel detailsPanel = getDetailsPanel();
        ((DetailsPanel) detailsPanel).showUserDetails(user);
        showPanel(detailsPanel);
    }

    private DetailsPanel getDetailsPanel() {
        if (Objects.isNull(detailsPanel)) {
            detailsPanel = new DetailsPanel(this);
        }
        return detailsPanel;
    }

    public void showUpdatePanel(User user) {
        JPanel updatePanel = getUpdatePanel();
        ((UpdatePanel) updatePanel).setUser(user);
        showPanel(updatePanel);
    }

    public AbstractModifiedPanel getUpdatePanel() {
        if (Objects.isNull(updatePanel)) {
            updatePanel = new UpdatePanel(this);
        }
        return updatePanel;
    }
}
