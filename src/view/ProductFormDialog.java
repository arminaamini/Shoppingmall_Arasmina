package view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ProductFormDialog extends JDialog{
    
    private final JTextField nameField = new JTextField(15);
    private final JTextField categoryField = new JTextField(15);
    private final JTextField priceField = new JTextField(10);
    private final JTextField stockField = new JTextField(10);
    private final JTextField descField = new JTextField(15);

    private boolean confirmed = false;

    public ProductFormDialog(JFrame parent, String title){

        super(parent, title, true);
        setLayout(new GridLayout(0, 2, 8, 8));

        add(new JLabel("Name: "));
        add(nameField);

        add(new JLabel("Category: "));
        add(categoryField);

        add(new JLabel("Price: "));
        add(priceField);
        
        add(new JLabel("Stock: "));
        add(stockField);
        
        add(new JLabel("Description: "));
        add(descField);

        

        JButton okBtn = new JButton("OK");
        JButton cancelBtn = new JButton("Cancel");

        okBtn.addActionListener(e -> {
            confirmed = true;
            dispose();
        });
        cancelBtn.addActionListener(e -> {
            dispose();
        });
        add(okBtn);
        add(cancelBtn);


        pack();
        setLocationRelativeTo(parent);

    }
    public boolean isConfirmed(){
        return confirmed;
    }

    public String getNameInput(){return nameField.getText();}
    public String getCategoryInput(){return categoryField.getText();}
    public String getStockInput(){return stockField.getText();}
    public String getPriceInput(){return priceField.getText();}
    public String getDescriptionInput(){return descField.getText();}

}
