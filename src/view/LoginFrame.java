package view;
import javax.swing.*;

import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);

    private final JButton customerLoginBtn = new JButton("Login(Customer)");
    private final JButton registerButton = new JButton("Register");
    private final JButton adminLoginBtn = new JButton("Login(Admin)");

    public LoginFrame(){
        super();
        setTitle("ShoppingMall - Login/Register");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(520, 320);
        setLocationRelativeTo(null);

        JPanel textPanel = new JPanel(new GridLayout(2, 2 , 2, 2));
        textPanel.add(new JLabel("Username: "));
        textPanel.add(usernameField);
        textPanel.add(new JLabel("Password: "));
        textPanel.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
        buttonPanel.add(customerLoginBtn);
        buttonPanel.add(registerButton);
        buttonPanel.add(adminLoginBtn);

        JPanel root = new JPanel(new BorderLayout(30, 30));
        root.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 5));
        root.add(textPanel, BorderLayout.CENTER);
        root.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(root);

    }
    public String getUsernameInput(){
        return usernameField.getText();
    }
    public String getPasswordInput(){
        return new String(passwordField.getPassword());
    }
    public void clearPassword(){
        passwordField.setText("");
    }
    public void showError(String massage){
        JOptionPane.showMessageDialog(this, massage, "Error", JOptionPane.ERROR_MESSAGE);
    }
    public void showInfo(String massage){
        JOptionPane.showMessageDialog(this, massage, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    public void onCustomerLogin(Runnable action){
        customerLoginBtn.addActionListener(e -> action.run());
    }
    public void onAdminLogin(Runnable action){
        adminLoginBtn.addActionListener(e -> action.run());
    }
    public void onRegister(Runnable action){
        registerButton.addActionListener(e -> action.run());
    }

    
}

    

  
