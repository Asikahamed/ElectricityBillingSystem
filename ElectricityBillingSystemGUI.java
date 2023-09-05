package Ebbillcalculation;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Properties;

public class ElectricityBillingSystemGUI {
    private JFrame frame;
    private JTextField consumerNameField;
    private JTextField emailField;
    private JTextField connectionTypeField;
    private JTextField connectionNumberField;
    private JTextField currentReadingField;
    private JTextField previousReadingField;
    private JButton calculateButton;

    public ElectricityBillingSystemGUI() {
        frame = new JFrame("Electricity Billing System");
        frame.setLayout(new GridLayout(8, 2));
        
        frame.add(new JLabel("Consumer Name:"));
        consumerNameField = new JTextField();
        frame.add(consumerNameField);

        frame.add(new JLabel("Email Id:"));
        emailField = new JTextField();
        frame.add(emailField);

        frame.add(new JLabel("Connection Type:"));
        connectionTypeField = new JTextField();
        frame.add(connectionTypeField);

        frame.add(new JLabel("Connection Number:"));
        connectionNumberField = new JTextField();
        frame.add(connectionNumberField);

        frame.add(new JLabel("Current Month Reading:"));
        currentReadingField = new JTextField();
        frame.add(currentReadingField);

        frame.add(new JLabel("Previous Month Reading:"));
        previousReadingField = new JTextField();
        frame.add(previousReadingField);

        calculateButton = new JButton("Calculate Bill");
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateBill();
            }
        });
        frame.add(calculateButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        

    }

    private void calculateBill() {
        String consumerName = consumerNameField.getText();
        String emailId = emailField.getText();
        String connectionType = connectionTypeField.getText();
        long connectionNumber = Long.parseLong(connectionNumberField.getText());
        int currentReading = Integer.parseInt(currentReadingField.getText());
        int previousReading = Integer.parseInt(previousReadingField.getText());

        // Rest of your calculation logic here...
        // Calculate the totalAmount and send the email
        int units = (previousReading < currentReading) ? currentReading - previousReading : 0;
        int totalAmount = 0;

        if (connectionType.equals("D")) {
            if (units >= 1 && units <= 100) {
                totalAmount = units * 5;
            } else if (units >= 101 && units <= 200) {
                totalAmount = 100 * 5 + (units - 100) * 7;
            } else if (units >= 201 && units <= 300) {
                totalAmount = 100 * 5 + 200 * 7 + (units - 200) * 10;
            } else {
                totalAmount = 100 * 5 + 200 * 7 + 300 * 10 + (units - 300) * 15;
            }
        } else {
            if (units >= 1 && units <= 100) {
                totalAmount = units * 5;
            } else if (units >= 101 && units <= 200) {
                totalAmount = 100 * 5 + (units - 100) * 7;
            } else if (units >= 201 && units <= 300) {
                totalAmount = 100 * 5 + 200 * 7 + (units - 200) * 10;
            } else {
                totalAmount = 100 * 5 + 200 * 7 + 300 * 10 + (units - 300) * 15;
            }
        }

        // Example email message
        String emailSubject = "Your Electricity Bill Statement";
        String emailMessage = "Your payment is pending\nConsumer Name: " + consumerName +
                "\nConnection Number: " + connectionNumber +
                "\nBill Amount: " + totalAmount;

        // Send email
        sendEmail(emailId, emailSubject, emailMessage);

        JOptionPane.showMessageDialog(frame, "Your bill has been sent to your registered email. Please check your inbox.");
    }

    private void sendEmail(String recipient, String subject, String msg) {
        // Your email sending code here...
    	String userName = "blackhatattackers@gmail.com";
        String password = "hphysynvxsxzyllb";
    	 Session session = null; // Declare session outside the try block
         try {
             Properties pros = new Properties();
             pros.put("mail.smtp.auth", "true");
             pros.put("mail.smtp.starttls.enable", "true");
             pros.put("mail.smtp.host", "smtp.gmail.com");
             pros.put("mail.smtp.port", "587");
             session = Session.getInstance(pros, new javax.mail.Authenticator() {
                 protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                     return new javax.mail.PasswordAuthentication(userName, password);
                 }
             });
         } catch (Exception ex) {
             System.out.println("This is Exception");
         }

         try {
             Message message = new MimeMessage(session);
             message.setFrom(new InternetAddress(userName));
             message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
             message.setSubject(subject);
             message.setText(msg);
             Transport.send(message);
         } catch (Exception ex) {
             System.out.println("The Exception is " + ex);
         }
     }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ElectricityBillingSystemGUI();
            }
        });
    }
}
