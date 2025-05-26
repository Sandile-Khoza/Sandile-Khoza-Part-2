/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chat;

/**
 *
 * @author lab_services_student
 */
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Chat {
     
    //array list that stores all sent messages
    private static final  ArrayList<Message> sentMessages = new ArrayList<>();
    //otal messages sent
    private static int totalMessagesSent = 0;

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

        boolean isLoggedIn = Login.loginUser(
            JOptionPane.showInputDialog("Login - enter your username:"),
            JOptionPane.showInputDialog("Login - enter your password:")
        );
        //This JOptionPane returns their login status
        JOptionPane.showMessageDialog(null, Login.returnLoginStatus(isLoggedIn, firstName, lastName));

        if (!isLoggedIn) return;
        
        //-------Messaging ------//

        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");

        //Ask how many messages user wants to send
        int messageLimit = Integer.parseInt(JOptionPane.showInputDialog("How many messages would you like to send?"));
        //This ask user to choose an option 
        while (true) {
            String menu = "Choose an option:\n1) Send Message\n2) Show recently sent messages\n3) Quit";
            int choice = Integer.parseInt(JOptionPane.showInputDialog(menu));

            switch (choice) {
                case 1 -> {
                    //warning if limit reached
                    if (totalMessagesSent >= messageLimit) {
                        JOptionPane.showMessageDialog(null, "Message limit reached.");
                        break;
                    }

                    //recipient phone number
                    String recipient = JOptionPane.showInputDialog("Enter recipient's phone number (+ and 10 digits):");
                    while (!Message.checkRecipientCell(recipient)) {
                        recipient = JOptionPane.showInputDialog("Invalid recipient. Must start with '+' and be max 10 characters. Try again:");
                    }

                    String text = JOptionPane.showInputDialog("Enter your message text:");
                    if (text.length() > 250){
                        JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters. ");
                        break;
                    }
                    
                    //Prompt the User to choose action,either to send, store or disregard the message
                    String[] options = {"Send", "Store to Json", "Disregard"};
                    int action = JOptionPane.showOptionDialog(null, "Choose what to do with the message: ",
                     "Message Options", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                     null, options, options[0]);
                    
                    if (action == 2 || action == JOptionPane.CLOSED_OPTION){
                    JOptionPane.showMessageDialog(null, "Message disregarded.");
                    break;
                    }
                    
                    //create and optionally store the message
                    try {
        Message msg = new Message(recipient, text);
        sentMessages.add(msg);
        totalMessagesSent++;

        if (action == 0) { // Send
            JOptionPane.showMessageDialog(null, "Message successfully sent\n" + msg.displayMessageDetails());

            // Prompt to delete
            String input = JOptionPane.showInputDialog("Press 0 to delete message");
            if ("0".equals(input)) {
                sentMessages.remove(msg);
                totalMessagesSent--;
                JOptionPane.showMessageDialog(null, "Message deleted.");
            }

        } else if (action == 1) { // Store
            JOptionPane.showMessageDialog(null, "Message successfully stored\n" + msg.displayMessageDetails());
        }

    } catch (IllegalArgumentException e) {
        JOptionPane.showMessageDialog(null, "Failed to send message: " + e.getMessage());
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Unexpected error: " + e.getMessage());
    }
}
                //display messages sent
                case 2 -> {
                   String result = Message.printMessages(sentMessages);
                   JOptionPane.showMessageDialog(null, result);
                }
                //Stores messages to file before quitting
                case 3 -> {
                    storeMessagesToJsonFile(sentMessages);
                    JOptionPane.showMessageDialog(null, "Messages stored as JSON file.");
                     JOptionPane.showMessageDialog(null, "Total messages sent: " + totalMessagesSent + "\nExiting app.");
                    System.exit(0);
                }
               //Shows total messages sent and exits the app
                case 4 -> {
                    JOptionPane.showMessageDialog(null, "Total messages sent: " + totalMessagesSent);
                }
                
                default -> JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        }
    }
    //this stores messages to a json file
    public static void storeMessagesToJsonFile(ArrayList<Message> messages) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("messages.json"))) {
            writer.write("[\n");
            for (int i = 0; i < messages.size(); i++) {
                writer.write(messages.get(i).toJson());
                if (i < messages.size() - 1) writer.write(",");
                writer.write("\n");
            }
            writer.write("]");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving messages: " + e.getMessage());
        }
    }
}