/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chat;

/**
 *
 * @author lab_services_student
 */
import javax.swing.JOptionPane;

public class Chat {
       
    public static void main(String[] args) {     

        //Registration
        //firstName
        //lastname
        String firstName = JOptionPane.showInputDialog("Enter your first name:");
        String lastName = JOptionPane.showInputDialog("Enter your last name:");

        //username which contains an underscore and no more than <5 characters
        String username;
        while (true) {
            username = JOptionPane.showInputDialog("Enter your username:");
            if (Login.checkUserName(username)) {
                JOptionPane.showMessageDialog(null, "Username successfully created.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Username must contain an underscore and be no more than 5 characters.");
            }
        }
        //password >8 characters,uppercase,number and special character
        String password;
        while (true) {
            password = JOptionPane.showInputDialog("Enter your password:");
            if (Login.checkPasswordComplexity(password)) {
                JOptionPane.showMessageDialog(null, "Password successfully captured.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Password must have at least 8 characters, a capital letter, a number, and a special character.");
            }
        }
        //phone number that starts with +27 and be 12 characters long
        String phoneNumber;
        while (true) {
            phoneNumber = JOptionPane.showInputDialog("Enter your phone number (start with +27):");
            if (Login.checkPhoneNumber(phoneNumber)) {
                JOptionPane.showMessageDialog(null, "Phone number successfully added.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Phone number must start with +27 and be 12 characters.");
            }
        }
    
        //login success message
        //Ask user to login based on their details
        JOptionPane.showMessageDialog(null, Login.registerUser(firstName, lastName, username, password, phoneNumber));

              //LOGIN
        boolean isLoggedIn = Login.loginUser(
            JOptionPane.showInputDialog("Login - enter your username:"),
            JOptionPane.showInputDialog("Login - enter your password:")
        );
        //This JOptionPane returns their login status
        JOptionPane.showMessageDialog(null, Login.returnLoginStatus(isLoggedIn, firstName, lastName));
        if (!isLoggedIn) return;
        
        
    
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
        
        //Message Manager to manage messages
        MessageManager messageManager = new MessageManager();
        
        //load stored messages from file int arrayys
        messageManager.loadStoredMessagesFromJson();

        // Main menu
        while (true) {
            String menu = """
                Choose an option:
                1) Send Message
                2) Show Recently Sent Messages
                3) Manage Messages (All Arrays)
                4) Quit
                """;

            int choice = Integer.parseInt(JOptionPane.showInputDialog(menu));

           switch (choice) {
    case 1 -> messageManager.handleMessagePrompt();
    case 2 -> messageManager.displaySendersAndRecipients();
    case 3 -> {
        // Manage messages submenu
        boolean back = false;
        while (!back) {
            String fullMenu = """
                Message Manager:
                1) Display All Sent Messages
                2) Display All Stored Messages
                3) View All Disregarded Messages
                4) Search by Message ID
                5) Search by Recipient
                6) Delete by Message Hash
                7) Display Longest Sent Message
                8) Display Full Sent Report
                9) Back to Main Menu
                """;
            int option = Integer.parseInt(JOptionPane.showInputDialog(fullMenu));

            switch (option) {
                case 1 -> messageManager.displaySendersAndRecipients();
                case 2 -> messageManager.displayStoredMessages();
                case 3 -> messageManager.displayDisregardedMessages();
                case 4 -> {
                    String id = JOptionPane.showInputDialog("Enter message ID:");
                    messageManager.searchByMessageID(id);
                }
                case 5 -> {
                    String recipient = JOptionPane.showInputDialog("Enter recipient number:");
                    messageManager.searchByRecipient(recipient);
                }
                case 6 -> {
                    String hash = JOptionPane.showInputDialog("Enter message hash:");
                    messageManager.deleteByMessageHash(hash);
                }
                case 7 -> messageManager.displayLongestSentMessage();
                case 8 -> messageManager.displayFullSentReport();
                case 9 -> back = true;
                default -> JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        }
    }
    case 4 -> {
        messageManager.storeSentMessagesToJson();
        JOptionPane.showMessageDialog(null, "Messages saved to JSON, Goodbye");
        System.exit(0);
    }
    default -> JOptionPane.showMessageDialog(null, "Invalid option.");
}
        }
    }
}