package view;

import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManagementPanel extends JPanel {

    private final JButton addBtn = new JButton("Add");
    private final JButton editBtn = new JButton("Edit");
    private final JButton deleteBtn = new JButton("Delete");
    private final JButton refreshBtn = new JButton("Refresh");
    private final JButton sortABtn = new JButton("Sort Price ↑");

    private final  DefaultTableModel model = new DefaultTableModel(
        new Object[]{"ID", "Name", "Category", "Price", "Stock"}, 0
    ){
        @Override 
        public boolean isCellEditable(int row, int col){return false;}
    };

    private final JTable table = new JTable(model);

    public ProductManagementPanel(){
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        top.add(addBtn);
        top.add(editBtn);
        top.add(deleteBtn);
        top.add(refreshBtn);
        top.add(sortABtn);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        setPreferredSize(new Dimension(800, 450));
    }
    public void setProducts(List<Product> products){
        model.setRowCount(0);
        for(Product p : products){
            model.addRow(new Object[]{
                p.getProductId(),
                p.getName(),
                p.getCategory(),
                p.getPrice(),
                p.getStockQuantity()
            });
        }
    }

    public String getSelectedProdeuct(){
        int row = table.getSelectedRow();
        if(row < 0) return null;
        return String.valueOf(model.getValueAt(row, 0));
    }

    public void showError(String msg){
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.ERROR_MESSAGE);
    }

    public void showInfo(String msg){
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
   
    public void onAdd(Runnable r){
        addBtn.addActionListener(e -> r.run());
    }
    public void onEdite(Runnable r){
        editBtn.addActionListener(e -> r.run());
    }
    public void onDelete(Runnable r){
        deleteBtn.addActionListener(e -> r.run());
    }
    public void onRefresh(Runnable r){
        refreshBtn.addActionListener(e -> r.run());
    }
    public void onSortA(Runnable r){
        sortABtn.addActionListener(e -> r.run());
    }
    

}
