package view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import helpers.StyleComponents;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ManagementCustomerView extends JDialog implements ActionListener, FocusListener, KeyListener {
    private JPanel panel;
    private JButton btnAdd, btnModify, btnRemove, btnSearch;
    private JTextField txtName, txtLastname, txtDni, txtCurp, txtPhone, txtEmail, txtStreet, txtExtNum, txtIntNum,
            txtDelegation, txtCountry, txtSearch;
    private JSeparator separatorH1, separatorH2, separatorV1;

    private JScrollPane tScroll;
    private JTable tCustomersList;
    private DefaultTableModel tModel;

    private int space_between = 20;
    private int width = Toolkit.getDefaultToolkit().getScreenSize().width - 100;
    private int height = Toolkit.getDefaultToolkit().getScreenSize().height - 100;

    private String[] columns = { "Name", "Lastname", "DNI", "CURP" };
    // private String[] row = new String[columns.length];

    public ManagementCustomerView(HomeView parent, String title, boolean modal) {
        // windows configuration
        super(parent, title, modal);
        this.setLocationRelativeTo(parent);
        this.setBounds(50, 32, width, height);

        // create components
        panel = new StyleComponents().Panel();

        txtName = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "Name"), (width - width + space_between),
                (height - height + space_between), 300, 50);

        txtLastname = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "Lastame"),
                (width - width + space_between * 2 + 300), (height - height + space_between), 300, 50);

        txtDni = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "DNI"),
                (width - width + space_between), (height - height + space_between * 4), 300, 50);

        txtCurp = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "CURP"),
                (width - width + space_between * 2 + 300), (height - height + space_between * 4), 300, 50);

        separatorH1 = new StyleComponents().Separator("horizontal", (width - width + space_between),
                (height - height + space_between * 8), 622, 1);

        txtStreet = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "Street"), (width - width + space_between),
                (height - height + space_between * 9), 300, 50);

        txtExtNum = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "Exterior number"),
                (width - width + space_between),
                (height - height + space_between * 12), 300, 50);

        txtIntNum = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "Interior number"),
                (width - width + space_between * 2 + 300),
                (height - height + space_between * 12), 300, 50);

        txtDelegation = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "Delegation"), (width - width + space_between),
                (height - height + space_between * 15), 300, 50);

        txtCountry = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "Country"),
                (width - width + space_between * 2 + 300),
                (height - height + space_between * 15), 300, 50);

        separatorH2 = new StyleComponents().Separator("horizontal", (width - width + space_between),
                (height - height + space_between * 19), 622, 1);

        txtPhone = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "Phone number"),
                (width - width + space_between),
                (height - height + space_between * 20), 300, 50);

        txtEmail = new StyleComponents().Field(new Color(255, 255, 255), getForeground(),
                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "E-mail"),
                (width - width + space_between * 2 + 300), (height - height + space_between * 20), 300, 50);

        btnAdd = new StyleComponents().Button("Add new customer", (width - width + space_between),
                (height - height + space_between * 24), 192, 40);

        btnModify = new StyleComponents().Button("Save changes", (width - width + space_between * 2 + 192),
                (height - height + space_between * 24), 192, 40);

        btnRemove = new StyleComponents().Button("Remove customer", (width - width + space_between * 3 + 384),
                (height - height + space_between * 24), 192, 40);

        separatorV1 = new StyleComponents().Separator("vertical", (width - width + space_between * 3 + 600),
                (height - height + space_between), 1, (height - space_between * 4 - 10));

        txtSearch = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "Enter Name, DNI, CURP or E-mail to search"),
                (width - width + space_between * 4 + 600), (height - height + space_between), 300, 50);

        btnSearch = new StyleComponents().Button("Search customer", (width - width + space_between * 5 + 900),
                (height - height + space_between + 8), 200, 40);

        tScroll = new StyleComponents().ScrollPane((width - width + space_between * 4 + 602),
                (height - height + space_between * 4 + 10), 519, (height - space_between * 8 + 1));

        tCustomersList = new StyleComponents().Table();

        tModel = new StyleComponents().TableModel(columns);

        // listeners

        // add components at panel
        panel.add(txtName);
        panel.add(txtLastname);
        panel.add(txtDni);
        panel.add(txtCurp);
        panel.add(separatorH1);
        panel.add(txtStreet);
        panel.add(txtExtNum);
        panel.add(txtIntNum);
        panel.add(txtDelegation);
        panel.add(txtCountry);
        panel.add(separatorH2);
        panel.add(txtPhone);
        panel.add(txtEmail);
        panel.add(btnAdd);
        panel.add(btnModify);
        panel.add(btnRemove);
        panel.add(separatorV1);
        panel.add(txtSearch);
        panel.add(btnSearch);
        panel.add(tScroll);

        // prepare table
        tCustomersList.setModel(tModel);
        tScroll.setViewportView(tCustomersList);

        // add panel at dialog
        this.add(panel);
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}