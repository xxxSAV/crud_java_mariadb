package view;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
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
import helpers.Validator;

public class ManagementStockView extends JDialog implements ActionListener, FocusListener, KeyListener, ItemListener {
        private JPanel panel;
        private JButton btnAdd, btnModify, btnRemove, btnClearForm, btnSearch, btnEdit, btnReturn, btnViewProvider;
        private JTextField txtUnits, txtUnitsByUnitType, txtUnitType, txtTotalUnits, txtPriceByUnitType, txtProductName,
                        txtSize, txtPriceByUnit, txtPID, txtSearch;
        private JComboBox<String> cboUnitType, cboSize, cboProvider;
        private JSeparator separatorH1, separatorH2, separatorV1;

        private JScrollPane tScroll;
        private JTable tStockList;
        private DefaultTableModel tModel;

        private int space_between = 20;
        private int width = Toolkit.getDefaultToolkit().getScreenSize().width - 100;
        private int height = Toolkit.getDefaultToolkit().getScreenSize().height - 100;

        private String[] columns = { "SID", "Product name", "Stock" };
        private String[] row = new String[columns.length];

        // data of combobox
        private String[] uTypes = { "Specify the unit type", "Box", "Granel", "Kit", "Lote", "Other" };
        private String[] uSize = { "Specify the unit size", "Small", "Medium", "Big", "Kit", "Other" };
        private String[][] providersList = getProviders();
        private String[] list = getList();

        private String SID;
        private ResultSet stockDetails;

        public ManagementStockView(MoreView parent, String title, boolean modal) {
                // window configuration
                super(parent, title, modal);
                this.setLocationRelativeTo(parent);
                this.setBounds(50, 32, width, height);

                // create components
                panel = new StyleComponents().Panel();

                txtUnits = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "How many units?"),
                                (width - width + space_between), (height - height + space_between), 300, 50);

                cboUnitType = new StyleComponents().ComboBox(uTypes, new Color(255, 255, 255), new Color(0, 0, 0), null,
                                (width - width + 300 + space_between * 2), (height - height + space_between), 300, 50);

                txtUnitsByUnitType = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                                new TitledBorder(new LineBorder(new Color(26, 82, 118)),
                                                "How many units contain each unit?"),
                                (width - width + space_between), (height - height + space_between * 2 + 50), 300, 50);

                txtUnitType = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                                new TitledBorder(new LineBorder(new Color(26, 82, 118)),
                                                "Please, specify the unit type"),
                                (width - width + space_between * 2 + 300), (height - height + space_between * 2 + 50),
                                300, 50);

                txtTotalUnits = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "This is the total units"),
                                (width - width + space_between), (height - height + space_between * 3 + 100), 300, 50);

                txtPriceByUnitType = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "Price by unit type"),
                                (width - width + space_between * 2 + 300), (height - height + space_between * 3 + 100),
                                300, 50);

                separatorH1 = new StyleComponents().Separator("horizontal", (width - width + space_between),
                                (height - height + space_between * 4 + 150), 622, 1);

                txtProductName = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "Product name"),
                                (width - width + space_between), (height - height + space_between * 5 + 150), 300, 50);

                cboSize = new StyleComponents().ComboBox(uSize, new Color(255, 255, 255), new Color(0, 0, 0), null,
                                (width - width + space_between * 2 + 300), (height - height + space_between * 5 + 150),
                                300, 50);

                txtPriceByUnit = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "Price by unit"),
                                (width - width + space_between), (height - height + space_between * 6 + 200), 300, 50);

                txtSize = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                                new TitledBorder(new LineBorder(new Color(26, 82, 118)),
                                                "Please, specify the unit size"),
                                (width - width + space_between * 2 + 300), (height - height + space_between * 6 + 200),
                                300, 50);

                separatorH2 = new StyleComponents().Separator("horizontal", (width - width + space_between),
                                (height - height + space_between * 9 + 210), 622, 1);

                cboProvider = new StyleComponents().ComboBox(list, new Color(255, 255, 255), new Color(0, 0, 0), null,
                                (width - width + space_between), (height - height + space_between * 10 + 210), 300, 50);

                txtPID = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                                new TitledBorder(new LineBorder(new Color(26, 82, 118)), "This is the provider ID"),
                                (width - width + space_between * 2 + 300), (height - height + space_between * 10 + 210),
                                300, 50);

                btnAdd = new StyleComponents().Button("Add new stock", (width - width + space_between),
                                (height - height + space_between * 11 + 260), 192, 40);

                btnModify = new StyleComponents().Button("Save changes", (width - width + space_between * 2 + 192),
                                (height - height + space_between * 11 + 260), 192, 40);

                btnClearForm = new StyleComponents().Button("Clear form", (width - width + space_between * 3 + 384),
                                (height - height + space_between * 11 + 260), 192, 40);

                btnReturn = new StyleComponents().Button("Back", (width - width + space_between * 3 + 384),
                                (height - height + space_between * 12 + 300), 192, 40);

                separatorV1 = new StyleComponents().Separator("vertical", (width - width + space_between * 3 + 600),
                                (height - height + space_between), 1, (height - space_between * 4 - 10));

                txtSearch = new StyleComponents().Field(new Color(255, 255, 255), new Color(0, 0, 0),
                                new TitledBorder(new LineBorder(new Color(26, 82, 118)),
                                                "Enter Product name, provider or PRID to search"),
                                (width - width + space_between * 4 + 600), (height - height + space_between), 300, 50);

                btnSearch = new StyleComponents().Button("Search stock", (width - width + space_between * 5 + 900),
                                (height - height + space_between + 8), 200, 40);

                tScroll = new StyleComponents().ScrollPane((width - width + space_between * 4 + 602),
                                (height - height + space_between * 4 + 10), 519, (height - space_between * 12 + 1));

                btnRemove = new StyleComponents().Button("Remove", (width - width + space_between * 4 + 601),
                                (height - space_between * 6 - 5), 150, 40);

                btnViewProvider = new StyleComponents().Button("View provider",
                                (width - width + space_between * 6 + 751 - 5), (height - space_between * 6 - 5), 150,
                                40);

                btnEdit = new StyleComponents().Button("Edit details", (width - width + space_between * 5 + 951),
                                (height - space_between * 6 - 5), 150, 40);

                tStockList = new StyleComponents().Table();

                tModel = new StyleComponents().TableModel(columns);

                // listeners
                cboUnitType.addItemListener(this);
                cboSize.addItemListener(this);
                cboProvider.addItemListener(this);
                txtPriceByUnitType.addFocusListener(this);

                txtUnits.addActionListener(this);
                cboUnitType.addActionListener(this);
                txtUnitsByUnitType.addActionListener(this);
                txtUnitType.addActionListener(this);
                txtTotalUnits.addActionListener(this);
                txtPriceByUnitType.addActionListener(this);
                txtProductName.addActionListener(this);
                cboSize.addActionListener(this);
                txtPriceByUnit.addActionListener(this);
                txtSize.addActionListener(this);
                cboProvider.addActionListener(this);
                txtPID.addActionListener(this);
                btnAdd.addActionListener(this);
                btnModify.addActionListener(this);
                btnClearForm.addActionListener(this);
                btnReturn.addActionListener(this);
                txtSearch.addActionListener(this);
                btnSearch.addActionListener(this);
                btnRemove.addActionListener(this);
                btnViewProvider.addActionListener(this);
                btnEdit.addActionListener(this);

                txtUnits.addKeyListener(this);
                cboUnitType.addKeyListener(this);
                txtUnitsByUnitType.addKeyListener(this);
                txtUnitType.addKeyListener(this);
                txtTotalUnits.addKeyListener(this);
                txtPriceByUnitType.addKeyListener(this);
                txtProductName.addKeyListener(this);
                cboSize.addKeyListener(this);
                txtPriceByUnit.addKeyListener(this);
                txtSize.addKeyListener(this);
                cboProvider.addKeyListener(this);
                txtPID.addKeyListener(this);
                btnAdd.addKeyListener(this);
                btnModify.addKeyListener(this);
                btnClearForm.addKeyListener(this);
                btnReturn.addKeyListener(this);
                txtSearch.addKeyListener(this);
                btnSearch.addKeyListener(this);
                btnRemove.addKeyListener(this);
                btnViewProvider.addKeyListener(this);
                btnEdit.addKeyListener(this);

                txtSearch.addFocusListener(this);

                // add components at panel
                panel.add(txtUnits);
                panel.add(cboUnitType);
                panel.add(txtUnitsByUnitType);
                panel.add(txtUnitType);
                panel.add(txtTotalUnits);
                panel.add(txtPriceByUnitType);
                panel.add(separatorH1);
                panel.add(txtProductName);
                panel.add(cboSize);
                panel.add(txtPriceByUnit);
                panel.add(txtSize);
                panel.add(separatorH2);
                panel.add(cboProvider);
                panel.add(txtPID);
                panel.add(btnAdd);
                panel.add(btnModify);
                panel.add(btnClearForm);
                panel.add(btnReturn);
                panel.add(separatorV1);
                panel.add(txtSearch);
                panel.add(btnSearch);
                panel.add(tScroll);
                panel.add(btnRemove);
                panel.add(btnViewProvider);
                panel.add(btnEdit);

                // non editable
                txtTotalUnits.setEditable(false);
                txtPID.setEditable(false);

                // hidden by default
                txtUnitType.setVisible(false);
                txtSize.setVisible(false);
                btnModify.setVisible(false);

                // prepare table
                tStockList.setModel(tModel);
                tScroll.setViewportView(tStockList);

                // load data
                loadStock();

                // add panel at dialog
                this.add(panel);
        }

        private String[][] getProviders() {
                try {
                        ResultSet providers = new Controller().readProviders("", "");
                        int rows = 0;

                        // count rows
                        while (providers.next()) {
                                rows++;
                        }

                        providers.beforeFirst();

                        // initialize array
                        String[][] list = new String[rows + 1][2];

                        // fill data
                        int i = 0;
                        int j = 0;
                        list[i][j] = "Specify the provider";
                        j++;
                        list[i][j] = null;
                        i++;
                        j--;
                        while (providers.next()) {
                                list[i][j] = providers.getString("Company name");
                                j++;
                                list[i][j] = providers.getString("Provider ID");
                                i++;
                                j--;
                        }

                        if (rows == 0) {
                                int op = JOptionPane.showConfirmDialog(this,
                                                "¡There are no registered providers!\nBefore that agregate stock needs to add providers... Do you want to make it now?",
                                                "Alert", 0);
                                                
                                if (op == 0) {
                                        this.setVisible(false);
                                        new ManagementProvidersView(null, "Providers management", true)
                                                        .setVisible(true);
                                } else {
                                        this.setVisible(false);
                                }
                        }

                        return list;
                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        JOptionPane.showMessageDialog(this, "The providers could not be loaded", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                }
                return null;
        }

        private String[] getList() {
                list = new String[providersList.length];
                for (int i = 0; i < providersList.length; i++) {
                        list[i] = providersList[i][0];
                }
                return list;
        }

        private void loadStock() {
                // clean the table
                int x = tModel.getRowCount() - 1;
                for (int i = x; i >= 0; i--) {
                        tModel.removeRow(i);
                }

                this.clearForm();

                // generate rows
                try {
                        ResultSet stock = new Controller().readStock(this.txtSearch.getText(), "");
                        int rows = 0;

                        while (stock.next()) {
                                this.row[0] = stock.getString("PRID");
                                this.row[1] = stock.getString("Product name");
                                this.row[2] = stock.getString("Total units") + " units";
                                this.tModel.addRow(row);
                                rows++;
                        }

                        if (rows == 0) {
                                btnRemove.setVisible(false);
                                btnEdit.setVisible(false);
                                btnViewProvider.setVisible(false);
                                JOptionPane.showMessageDialog(this, "Unregistered stock", "Information",
                                                JOptionPane.INFORMATION_MESSAGE);
                        } else {
                                btnRemove.setVisible(true);
                                btnEdit.setVisible(true);
                                btnViewProvider.setVisible(true);
                        }
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Data could not be loaded", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

        private void loadStockDetails() {
                try {
                        this.stockDetails = new Controller().readStock("", this.SID);

                        if (stockDetails.next()) {
                                this.txtUnits.setText(this.stockDetails.getString("Units"));
                                for (int index = 0; index < uTypes.length; index++) {
                                        if (uTypes[index].equalsIgnoreCase(this.stockDetails.getString("Unit type"))) {
                                                cboUnitType.setSelectedItem(uTypes[index]);
                                                this.txtUnitType.setVisible(false);
                                                break;
                                        } else {
                                                cboUnitType.setSelectedItem(uTypes[index]);
                                                this.txtUnitType.setText(this.stockDetails.getString("Unit type"));
                                                this.txtUnitType.setVisible(true);

                                        }
                                }
                                this.txtUnitsByUnitType.setText(this.stockDetails.getString("Content by unit"));
                                this.txtTotalUnits.setText(this.stockDetails.getString("Total units"));
                                this.txtPriceByUnitType.setText(this.stockDetails.getString("Price by unit"));
                                this.txtProductName.setText(this.stockDetails.getString("Product name"));
                                for (int index = 0; index < uSize.length; index++) {
                                        if (uSize[index].equalsIgnoreCase(
                                                        this.stockDetails.getString("Product size"))) {
                                                this.cboSize.setSelectedItem(uSize[index]);
                                                this.txtSize.setVisible(false);
                                                break;
                                        } else {
                                                this.cboSize.setSelectedItem(uSize[index]);
                                                this.txtSize.setText(this.stockDetails.getString("Product size"));
                                                this.txtSize.setVisible(true);

                                        }
                                }
                                this.txtPriceByUnit.setText(this.stockDetails.getString("Product price"));

                                String tmpPID = this.stockDetails.getString("PID");
                                int index;
                                for (index = 0; index < providersList.length; index++) {
                                        if (providersList[index][1] != null) {
                                                if (providersList[index][1].equalsIgnoreCase(tmpPID)) {
                                                        this.cboProvider.setSelectedItem(list[index]);
                                                        this.txtPID.setText(providersList[index][1]);
                                                }
                                        }

                                }
                                btnAdd.setVisible(false);
                                btnModify.setVisible(true);
                                btnRemove.setVisible(true);
                        } else {
                                this.clearForm();
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                        this.clearForm();
                        JOptionPane.showMessageDialog(this, "An error ocurred while loading data in the form", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

        private void clearForm() {
                txtUnits.setText("");
                txtUnitsByUnitType.setText("");
                txtUnitType.setText("");
                txtTotalUnits.setText("");
                txtPriceByUnitType.setText("");
                txtProductName.setText("");
                txtSize.setText("");
                txtPriceByUnit.setText("");
                txtPID.setText("");
                cboUnitType.setSelectedItem(uTypes[0]);
                cboSize.setSelectedItem(uSize[0]);
                cboProvider.setSelectedItem(providersList[0][0]);
                this.stockDetails = null;
                this.SID = null;
                btnModify.setVisible(false);
                btnAdd.setVisible(true);
        }

        private void addStock(String sUnitType, String sSize, String sProvider) {
                String value = new Controller(
                                this.txtUnits.getText().trim(),
                                sUnitType,
                                this.txtUnitsByUnitType.getText().trim(),
                                this.txtTotalUnits.getText().trim(),
                                this.txtPriceByUnitType.getText().trim(),
                                this.txtProductName.getText().trim().toUpperCase(),
                                sSize,
                                this.txtPriceByUnit.getText().trim())
                                .newStock(new BigDecimal(sProvider));

                if (value.equals("product")) {
                        JOptionPane.showMessageDialog(this, "Please, check the information", "Notice",
                                        JOptionPane.INFORMATION_MESSAGE);
                } else if (value.equals("key_stock")) {
                        JOptionPane.showMessageDialog(this, "Please, unexpected wrong on SID", "Notice",
                                        JOptionPane.INFORMATION_MESSAGE);
                } else if (value.equals("stock")) {
                        JOptionPane.showMessageDialog(this, "Please, check the information", "Notice",
                                        JOptionPane.INFORMATION_MESSAGE);
                } else if (value.equals("key_provider")) {
                        JOptionPane.showMessageDialog(this, "Oops, unexpected wrong on PID", "Notice",
                                        JOptionPane.INFORMATION_MESSAGE);
                } else if (value.equals("general_err")) {
                        JOptionPane.showMessageDialog(this, "Oops, unexpected wrong", "Notice",
                                        JOptionPane.ERROR_MESSAGE);
                } else {
                        String val = new Validator().VerifyInteger(value);

                        if (!val.equals("Err")) {
                                this.clearForm();
                                this.loadStock();

                                JOptionPane.showMessageDialog(this, "New record successfully saved", "Successful",
                                                JOptionPane.INFORMATION_MESSAGE);
                        } else {
                                JOptionPane.showMessageDialog(this, "Oops, unexpected wrong, not saved", "Notice",
                                                JOptionPane.ERROR_MESSAGE);
                        }
                }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btnAdd) {
                        String sUnitType = (String) cboUnitType.getSelectedItem();
                        String sSize = (String) cboSize.getSelectedItem();
                        String sProvider = (String) cboProvider.getSelectedItem();

                        if (!this.txtUnits.getText().trim().isEmpty()
                                        || !this.txtUnitsByUnitType.getText().trim().isEmpty()
                                        || !this.txtTotalUnits.getText().trim().isEmpty()
                                        || !this.txtPriceByUnitType.getText().trim().isEmpty()
                                        || !this.txtProductName.getText().trim().isEmpty()
                                        || !this.txtPriceByUnit.getText().trim().isEmpty()
                                        || !this.txtPID.getText().trim().isEmpty()) {
                                if (!sUnitType.equals("Specify the unit type")) {
                                        if (!sSize.equals("Specify the unit size")) {
                                                if (!sProvider.equals("Specify the provider")) {
                                                        String validaU = new Validator()
                                                                        .VerifyDouble(this.txtUnits.getText());
                                                        String validaUBUT = new Validator().VerifyDouble(
                                                                        this.txtUnitsByUnitType.getText());
                                                        String validaTTU = new Validator()
                                                                        .VerifyDouble(this.txtTotalUnits.getText());
                                                        String validaPBUT = new Validator().VerifyDouble(
                                                                        this.txtPriceByUnitType.getText());
                                                        String validaPBU = new Validator()
                                                                        .VerifyDouble(this.txtPriceByUnit.getText());

                                                        if (!validaU.equals("Err")) {
                                                                if (!validaUBUT.equals("Err")) {
                                                                        this.txtTotalUnits.setText(
                                                                                        String.valueOf(Double
                                                                                                        .parseDouble(this.txtUnits
                                                                                                                        .getText()
                                                                                                                        .trim())
                                                                                                        * Double.parseDouble(
                                                                                                                        this.txtUnitsByUnitType
                                                                                                                                        .getText()
                                                                                                                                        .trim())));
                                                                        if (!validaTTU.equals("Err")) {
                                                                                if (!validaPBUT.equals("Err")) {
                                                                                        if (!validaPBU.equals("Err")) {
                                                                                                if (sUnitType.equals(
                                                                                                                "Other")
                                                                                                                || sSize.equals("Other")) {
                                                                                                        if (!this.txtUnitType
                                                                                                                        .getText()
                                                                                                                        .trim()
                                                                                                                        .isEmpty()
                                                                                                                        || !this.txtSize.getText()
                                                                                                                                        .trim()
                                                                                                                                        .isEmpty()) {

                                                                                                                if (sUnitType == "Other") {
                                                                                                                        sUnitType = this.txtUnitType
                                                                                                                                        .getText()
                                                                                                                                        .trim()
                                                                                                                                        .toUpperCase();
                                                                                                                }

                                                                                                                if (sSize == "Other") {
                                                                                                                        sSize = this.txtSize
                                                                                                                                        .getText()
                                                                                                                                        .trim()
                                                                                                                                        .toUpperCase();
                                                                                                                }

                                                                                                                this.addStock(sUnitType,
                                                                                                                                sSize,
                                                                                                                                this.txtPID.getText()
                                                                                                                                                .trim());
                                                                                                        } else {
                                                                                                                JOptionPane.showMessageDialog(
                                                                                                                                this,
                                                                                                                                "Some fields are blank, please check",
                                                                                                                                "Notice",
                                                                                                                                JOptionPane.INFORMATION_MESSAGE);
                                                                                                        }
                                                                                                } else {
                                                                                                        this.addStock(sUnitType,
                                                                                                                        sSize,
                                                                                                                        this.txtPID.getText()
                                                                                                                                        .trim());
                                                                                                }
                                                                                        } else {
                                                                                                JOptionPane.showMessageDialog(
                                                                                                                this,
                                                                                                                "The value in 'Price by unit' must be numeric",
                                                                                                                "Notice",
                                                                                                                JOptionPane.INFORMATION_MESSAGE);
                                                                                        }
                                                                                } else {
                                                                                        JOptionPane.showMessageDialog(
                                                                                                        this,
                                                                                                        "The value in 'Price by unit type' must be numeric",
                                                                                                        "Notice",
                                                                                                        JOptionPane.INFORMATION_MESSAGE);
                                                                                }
                                                                        } else {
                                                                                JOptionPane.showMessageDialog(this,
                                                                                                "The value in 'This is the total units' must be numeric",
                                                                                                "Notice",
                                                                                                JOptionPane.INFORMATION_MESSAGE);
                                                                        }
                                                                } else {
                                                                        JOptionPane.showMessageDialog(this,
                                                                                        "The value in 'How many units contain each unit' must be numeric",
                                                                                        "Notice",
                                                                                        JOptionPane.INFORMATION_MESSAGE);
                                                                }
                                                        } else {
                                                                JOptionPane.showMessageDialog(this,
                                                                                "The value in 'How many units' must be numeric",
                                                                                "Notice",
                                                                                JOptionPane.INFORMATION_MESSAGE);
                                                        }
                                                } else {
                                                        JOptionPane.showMessageDialog(this,
                                                                        "Please specify the provider of product",
                                                                        "Notice",
                                                                        JOptionPane.INFORMATION_MESSAGE);
                                                }
                                        } else {
                                                JOptionPane.showMessageDialog(this, "Please specify the unit size",
                                                                "Notice",
                                                                JOptionPane.INFORMATION_MESSAGE);
                                        }
                                } else {
                                        JOptionPane.showMessageDialog(this, "Please specify the unit type", "Notice",
                                                        JOptionPane.INFORMATION_MESSAGE);
                                }
                        } else {
                                JOptionPane.showMessageDialog(this, "You must provide complete information", "Notice",
                                                JOptionPane.INFORMATION_MESSAGE);
                        }
                } else if (e.getSource() == btnModify) {

                } else if (e.getSource() == btnClearForm) {
                        this.clearForm();
                        this.txtUnits.requestFocus();
                } else if (e.getSource() == btnReturn) {
                        this.clearForm();
                        this.setVisible(false);
                } else if (e.getSource() == btnRemove) {

                } else if (e.getSource() == btnViewProvider) {
                        int rowSelected = tStockList.getSelectedRow();

                        if (rowSelected >= 0) {
                                try {
                                        this.SID = (String) tModel.getValueAt(rowSelected, 0);
                                        RProviderView rpv = new RProviderView(this, "Provider details", true, this.SID);
                                        rpv.setVisible(true);
                                } catch (Exception ex) {
                                        JOptionPane.showMessageDialog(this, "Oops, an unexpected error ocurred",
                                                        "Error", JOptionPane.ERROR_MESSAGE);
                                }
                        } else {
                                JOptionPane.showMessageDialog(this,
                                                "Please, select row from the table to edit the information",
                                                "Information", JOptionPane.INFORMATION_MESSAGE);
                        }
                } else if (e.getSource() == btnEdit) {
                        this.clearForm();

                        int rowSelected = tStockList.getSelectedRow();

                        if (rowSelected >= 0) {
                                try {
                                        this.SID = (String) tModel.getValueAt(rowSelected, 0);
                                        this.loadStockDetails();
                                } catch (Exception ex) {
                                        JOptionPane.showMessageDialog(this, "Oops, an unexpected error ocurred",
                                                        "Error", JOptionPane.ERROR_MESSAGE);
                                }
                        } else {
                                JOptionPane.showMessageDialog(this,
                                                "Please, select row from the table to edit the information",
                                                "Information", JOptionPane.INFORMATION_MESSAGE);
                        }
                } else if (e.getSource() == btnSearch) {
                        this.loadStock();
                }
        }

        @Override
        public void focusGained(FocusEvent e) {
                if (e.getSource() == txtSearch) {
                        this.txtSearch.setText("");
                } else if (e.getSource() == txtPriceByUnitType) {
                        String validaUnits = new Validator().VerifyDouble(this.txtUnits.getText().trim());
                        String validaUnitsByUnitType = new Validator()
                                        .VerifyDouble(this.txtUnitsByUnitType.getText().trim());

                        if (validaUnits != "Err" && validaUnitsByUnitType != "Err") {
                                this.txtTotalUnits.setText(
                                                String.valueOf(Double.parseDouble(validaUnits)
                                                                * Double.parseDouble(validaUnitsByUnitType)));
                        } else {
                                this.txtTotalUnits.setText("");
                        }
                }
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

        @Override
        public void focusLost(FocusEvent arg0) {
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
                String selected;

                if (e.getSource() == cboUnitType) {
                        selected = (String) cboUnitType.getSelectedItem();
                        if (selected != "Other") {
                                txtUnitType.setText("");
                                txtUnitType.setVisible(false);
                        } else {
                                txtUnitType.setText("");
                                txtUnitType.setVisible(true);
                        }
                } else if (e.getSource() == cboSize) {
                        selected = (String) cboSize.getSelectedItem();
                        if (selected != "Other") {
                                txtSize.setText("");
                                txtSize.setVisible(false);
                        } else {
                                txtSize.setText("");
                                txtSize.setVisible(true);
                        }
                } else if (e.getSource() == cboProvider) {
                        selected = (String) cboProvider.getSelectedItem();
                        String PID = null;

                        for (int i = 0; i < providersList.length; i++) {
                                if (providersList[i][0] == selected) {
                                        PID = providersList[i][1];
                                        break;
                                }
                        }

                        txtPID.setText(PID);
                }
        }

}
