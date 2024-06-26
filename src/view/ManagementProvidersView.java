package view;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controllers.Controller;
import helpers.StyleComponents;

public class ManagementProvidersView extends JDialog implements ActionListener, KeyListener, FocusListener {
        private JPanel panel;
        private JButton btnAdd, btnModify, btnRemove, btnSearch, btnClearForm, btnEdit, btnReturn, btnViewProducts;
        private JComboBox<String> cboPerson;
        private JTextField txtCompany, txtPhone, txtEmail, txtStreet, txtIntNum, txtExtNum, txtDelegation, txtCountry,
                        txtSearch;
        private JSeparator separatorH1, separatorH2, separatorV1;

        private JScrollPane tScroll;
        private JTable tProvidersList;
        private DefaultTableModel tModel;

        private int space_between = 20;
        private int width = Toolkit.getDefaultToolkit().getScreenSize().width - 100;
        private int height = Toolkit.getDefaultToolkit().getScreenSize().height - 100;

        private String[] activity = { "Business activity", "Legal entity", "Physics person" };

        private String[] columns = { "PID", "Company name", "Phone number" };
        private String[] row = new String[columns.length];

        private String PID;
        private ResultSet providerDetails;

        public ManagementProvidersView(MoreView parent, String title, boolean modal) {
                // window configuration
                super(parent, title, modal);
                this.setLocationRelativeTo(parent);
                this.setBounds(50, 32, width, height);

                // create components
                panel = new StyleComponents().Panel();

                txtCompany = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "Company name"),
                                (width - width + space_between), (height - height + space_between), 300, 50);

                cboPerson = new StyleComponents().ComboBox(activity, new Color(255, 255, 255), new Color(0, 0, 0), null,
                                (width - width + 300 + space_between * 2), (height - height + space_between + 8), 300,
                                40);

                separatorH1 = new StyleComponents().Separator("horizontal", (width - width + space_between),
                                (height - height + space_between * 4 + 10), 622, 1);

                txtStreet = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "Street"),
                                (width - width + space_between), (height - height + space_between * 6 - 10), 300, 50);

                txtExtNum = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "Exterior number"),
                                (width - width + space_between), (height - height + space_between * 9 - 10), 300, 50);

                txtIntNum = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "Interior number"),
                                (width - width + space_between * 2 + 300), (height - height + space_between * 9 - 10),
                                300, 50);

                txtDelegation = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "Delegation"),
                                (width - width + space_between), (height - height + space_between * 12), 300, 50);

                txtCountry = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "Country"),
                                (width - width + space_between * 2 + 300), (height - height + space_between * 12), 300,
                                50);

                separatorH2 = new StyleComponents().Separator("horizontal", (width - width + space_between),
                                (height - height + space_between * 16 - 10), 622, 1);

                txtPhone = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "Phone number"),
                                (width - width + space_between), (height - height + space_between * 17 - 10), 300, 50);

                txtEmail = new StyleComponents().Field(new Color(255, 255, 255), getForeground(),
                                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "E-mail"),
                                (width - width + space_between * 2 + 300), (height - height + space_between * 17 - 10),
                                300, 50);

                btnAdd = new StyleComponents().Button("Add new provider", (width - width + space_between),
                                (height - height + space_between * 21 - 10), 192, 40);

                btnModify = new StyleComponents().Button("Save changes", (width - width + space_between * 2 + 192),
                                (height - height + space_between * 21 - 10), 192, 40);

                btnClearForm = new StyleComponents().Button("Clear form", (width - width + space_between * 3 + 384),
                                (height - height + space_between * 21 - 10), 192, 40);

                btnReturn = new StyleComponents().Button("Back", (width - width + space_between * 3 + 384),
                                (height - height + space_between * 24 - 10), 192, 40);

                separatorV1 = new StyleComponents().Separator("vertical", (width - width + space_between * 3 + 600),
                                (height - height + space_between), 1, (height - space_between * 4 - 10));

                txtSearch = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                                new TitledBorder(new LineBorder(new Color(26, 82, 118)),
                                                "Enter Name, Phone or E-mail to search"),
                                (width - width + space_between * 4 + 600), (height - height + space_between), 300, 50);

                btnSearch = new StyleComponents().Button("Search provider", (width - width + space_between * 5 + 900),
                                (height - height + space_between + 8), 200, 40);

                tScroll = new StyleComponents().ScrollPane((width - width + space_between * 4 + 602),
                                (height - height + space_between * 4 + 10), 519, (height - space_between * 12 + 1));

                btnRemove = new StyleComponents().Button("Remove", (width - width + space_between * 4 + 601),
                                (height - space_between * 6 - 5), 150, 40);

                btnViewProducts = new StyleComponents().Button("View products",
                                (width - width + space_between * 6 + 751 - 5), (height - space_between * 6 - 5), 150,
                                40);

                btnEdit = new StyleComponents().Button("Edit details", (width - width + space_between * 5 + 951),
                                (height - space_between * 6 - 5), 150, 40);

                tProvidersList = new StyleComponents().Table();

                tModel = new StyleComponents().TableModel(columns);

                // listeners
                txtCompany.addActionListener(this);
                cboPerson.addActionListener(this);
                txtStreet.addActionListener(this);
                txtExtNum.addActionListener(this);
                txtIntNum.addActionListener(this);
                txtDelegation.addActionListener(this);
                txtCountry.addActionListener(this);
                txtPhone.addActionListener(this);
                txtEmail.addActionListener(this);
                btnAdd.addActionListener(this);
                btnModify.addActionListener(this);
                btnClearForm.addActionListener(this);
                btnReturn.addActionListener(this);
                txtSearch.addActionListener(this);
                btnSearch.addActionListener(this);
                btnRemove.addActionListener(this);
                btnViewProducts.addActionListener(this);
                btnEdit.addActionListener(this);

                txtCompany.addKeyListener(this);
                cboPerson.addKeyListener(this);
                txtStreet.addKeyListener(this);
                txtExtNum.addKeyListener(this);
                txtIntNum.addKeyListener(this);
                txtDelegation.addKeyListener(this);
                txtCountry.addKeyListener(this);
                txtPhone.addKeyListener(this);
                txtEmail.addKeyListener(this);
                btnAdd.addKeyListener(this);
                btnModify.addKeyListener(this);
                btnClearForm.addKeyListener(this);
                btnReturn.addKeyListener(this);
                txtSearch.addKeyListener(this);
                btnSearch.addKeyListener(this);
                btnRemove.addKeyListener(this);
                btnViewProducts.addKeyListener(this);
                btnEdit.addKeyListener(this);

                txtSearch.addFocusListener(this);

                // add components at panel
                panel.add(txtCompany);
                panel.add(cboPerson);
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
                panel.add(btnClearForm);
                panel.add(btnReturn);
                panel.add(separatorV1);
                panel.add(txtSearch);
                panel.add(btnSearch);
                panel.add(tScroll);
                panel.add(btnRemove);
                panel.add(btnViewProducts);
                panel.add(btnEdit);

                // disabled by default
                btnModify.setEnabled(false);

                // prepare table
                tProvidersList.setModel(tModel);
                tScroll.setViewportView(tProvidersList);

                // load data
                loadProviders();

                // add panel at dialog
                this.add(panel);
        }

        private void loadProviders() {
                // clean the table
                int x = tModel.getRowCount() - 1;
                for (int i = x; i >= 0; i--) {
                        tModel.removeRow(i);
                }

                this.clearForm();

                // generate rows
                try {
                        ResultSet providers = new Controller().readProviders(this.txtSearch.getText(), "");
                        int rows = 0;

                        while (providers.next()) {
                                this.row[0] = providers.getString("Provider ID");
                                this.row[1] = providers.getString("Company name");
                                this.row[2] = providers.getString("Provider phone");
                                this.tModel.addRow(row);
                                rows++;
                        }

                        if (rows == 0) {
                                JOptionPane.showMessageDialog(this, "Unregistered provider", "Information",
                                                JOptionPane.INFORMATION_MESSAGE);
                        }
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Data could not be loaded", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

        private void clearForm() {
                txtCompany.setText("");
                cboPerson.setSelectedItem(activity[0]);
                txtStreet.setText("");
                txtExtNum.setText("");
                txtIntNum.setText("");
                txtDelegation.setText("");
                txtCountry.setText("");
                txtPhone.setText("");
                txtEmail.setText("");
                btnModify.setEnabled(false);
                this.PID = null;
                this.providerDetails = null;
        }

        private void loadProviderDetails() {
                try {
                        this.providerDetails = new Controller().readProviders("", this.PID);

                        if (providerDetails.next()) {
                                txtCompany.setText(this.providerDetails.getString("Company name"));
                                cboPerson.setSelectedItem(this.providerDetails.getString("Person"));
                                txtStreet.setText(this.providerDetails.getString("Provider street"));
                                txtExtNum.setText(this.providerDetails.getString("Provider exterior number"));
                                txtIntNum.setText(this.providerDetails.getString("Provider interior number"));
                                txtDelegation.setText(this.providerDetails.getString("Provider delegation"));
                                txtCountry.setText(this.providerDetails.getString("Provider country"));
                                txtPhone.setText(this.providerDetails.getString("Provider phone"));
                                txtEmail.setText(this.providerDetails.getString("Provider mail"));
                                btnModify.setEnabled(true);
                        } else {
                                this.clearForm();
                        }
                } catch (Exception e) {
                        this.clearForm();
                        JOptionPane.showMessageDialog(this, "An error ocurred while loading data in the form", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btnAdd) {

                } else if (e.getSource() == btnModify) {

                } else if (e.getSource() == btnClearForm) {
                        this.clearForm();
                        this.txtCompany.requestFocus();
                } else if (e.getSource() == btnReturn) {
                        this.clearForm();
                        this.setVisible(false);
                } else if (e.getSource() == btnRemove) {

                } else if (e.getSource() == btnViewProducts) {

                } else if (e.getSource() == btnEdit) {
                        this.clearForm();

                        int rowSelected = tProvidersList.getSelectedRow();

                        if (rowSelected >= 0) {
                                try {
                                        this.PID = (String) tModel.getValueAt(rowSelected, 0);
                                        this.loadProviderDetails();
                                } catch (Exception ex) {
                                        JOptionPane.showMessageDialog(this, "Oops, an unexpected error ocurred",
                                                        "Error", JOptionPane.ERROR_MESSAGE);
                                }
                        } else {
                                JOptionPane.showMessageDialog(this,
                                                "Please, select row from the table for edit the information",
                                                "Information", JOptionPane.INFORMATION_MESSAGE);
                        }
                } else if (e.getSource() == btnSearch) {
                        this.loadProviders();
                }
        }

        @Override
        public void focusGained(FocusEvent e) {
                if (e.getSource() == txtSearch) {
                        this.txtSearch.setText("");
                }
        }

        @Override
        public void focusLost(FocusEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        this.clearForm();
                        this.setVisible(false);
                }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

}
