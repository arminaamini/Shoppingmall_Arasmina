package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;//////////////OK\\\\\\\\\\\\\\\\
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionListener;

import javax.swing.table.DefaultTableModel;

public class CustomerMainFrame extends JFrame{
    
    private final JTextField productIdField = new JTextField(12);
    private final JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

    private final JButton addBtn = new JButton("Add to cart");
    private final JButton removeBtn = new JButton("Remove from cart");
 



    private final JButton clearBtn = new JButton("Clear");
    private final JButton profileBtn = new JButton("profile");
    private final JButton checkOutBtn = new JButton("checkout");
    private final JButton logoutBtn = new JButton("Logout");
    private final JButton refreshBtn = new JButton("Refresh ");
    private final JButton showProductBtn = new JButton("ShowProduct");



    private final JLabel balanceLabel = new JLabel("Balance: " );
    private final JLabel totLabel = new JLabel("Total:0 " );



    private final DefaultTableModel tableModel =
        new DefaultTableModel(new Object[]{"product ID", "Quantity"}, 0){
            @Override public boolean isCellEditable(int row, int col){return false;}
        };
        
    private final JTable cartTable = new JTable(tableModel);

    public CustomerMainFrame(String username){
        super();
        setTitle("Customer Panel - " + username);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 250);
        setLocationRelativeTo(null);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        top.add(new JLabel("Product ID: "));
        top.add(productIdField);
        top.add(new JLabel("Quantity: "));
        top.add(quantitySpinner);
        top.add(addBtn);
        top.add(removeBtn);
      

        JScrollPane scroll = new JScrollPane(cartTable);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10 , 8));
        bottom.add(balanceLabel);
        bottom.add(totLabel);
        bottom.add(profileBtn);
        bottom.add(checkOutBtn);
        bottom.add(clearBtn);
        bottom.add(refreshBtn);
        bottom.add(logoutBtn);
        bottom.add(showProductBtn);


        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10,10, 10));
        root.add(top, BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);
    }
    public String getProductIdInput(){
        return productIdField.getText();
    }
    public int getQuantityInput(){
        return (Integer) quantitySpinner.getValue();
    }
    public String getSelectedProductFromTable(){
        int row = cartTable.getSelectedRow();
        if(row < 0) return null;
        return (String) tableModel.getValueAt(row,0 );
    }
    public void setcartRows(java.util.List<model.CartItem> items){
        tableModel.setRowCount(0);
        for(model.CartItem item : items){
            tableModel.addRow(new Object[]{item.getProductId(), item.getQuantity()});
        }
    }
    public void showError(String msg){
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showInfo(String msg){
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    public void showProfile(String username, String role, long balance){
        String text = "Username: " + username + "\nRole: " + role + "\nBalance:" + balance;
        JOptionPane.showMessageDialog(this, text , "Profile", JOptionPane.INFORMATION_MESSAGE);
    }


    public void onAdd(Runnable action){
        addBtn.addActionListener(e -> action.run());
    }

    public void onRemove(Runnable action){
        removeBtn.addActionListener(e -> action.run());
    }
    
    public void onClear(Runnable action){
        clearBtn.addActionListener(e -> action.run());
    }
    public void onProfile(Runnable action){
        profileBtn.addActionListener(e -> action.run());
    }
    public void onLogout(Runnable action){
        logoutBtn.addActionListener(e -> action.run());
    }
    public void onRefresh(Runnable action){
        refreshBtn.addActionListener(e -> action.run());
    }
    public void onCheckOut(Runnable r){
        checkOutBtn.addActionListener(e -> r.run());
    }
    public void onShowProduct(Runnable r){
        showProductBtn.addActionListener(e -> r.run());
    }
    public void setTotalText(String total){
        totLabel.setText("Total: " + total);
    }
    public void setBalanceText(String balance){
        balanceLabel.setText("Balance: " + balance);
    }

}
