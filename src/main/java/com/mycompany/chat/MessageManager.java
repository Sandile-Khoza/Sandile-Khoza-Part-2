/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chat;


import javax.swing.JOptionPane;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class MessageManager {
    
//This refers to the maximum number of arrays that can be stored in each of the arrays
    private final int MAX_MESSAGES = 100;
    
    //This consist of arrays for different types of message storage 
    private Message[] sentMessages = new Message[MAX_MESSAGES];
    private Message[] storedMessages = new Message[MAX_MESSAGES];
    private Message[] disregardedMessages = new Message[MAX_MESSAGES];
    
    //How many message are in each array
    private int sentCount = 0;
    private int storedCount = 0;
    private int disregardedCount = 0;
  
    
    // Handle message with prompt to send/store/disregard
    //Method that also ask a user to enter a recipient or message
    public void handleMessagePrompt() {
        String recipient = JOptionPane.showInputDialog("Enter recipient number (start with +27):");
        while (!Message.checkRecipientCell(recipient)) {
            recipient = JOptionPane.showInputDialog("Invalid. Must start with +27 and be 12 characters.");
        }

        String text = JOptionPane.showInputDialog("Enter message text (max 250 characters):");
        if (text.length() > 250) {
            JOptionPane.showMessageDialog(null, "Message too long.");
            return;
        }

        String[] options = {"Send", "Store", "Disregard"};
        int action = JOptionPane.showOptionDialog(null, "Choose action:", "Message Options",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        Message msg = new Message(recipient, text);

       
switch (action) {
    case 0 -> { // SEND
        if (sentCount < MAX_MESSAGES) {
            sentMessages[sentCount++] = msg;
            JOptionPane.showMessageDialog(null, "Message sent:\n" + msg.displayMessageDetails());
        }
    }
    case 1 -> { // STORE
        if (storedCount < MAX_MESSAGES) {
            storedMessages[storedCount++] = msg;
            JOptionPane.showMessageDialog(null, "Message stored:\n" + msg.displayMessageDetails());
        }
        // ❌ DO NOT write to file here unless you want to
        // If you do, use a separate method like storeStoredMessagesToJson()
    }
    case 2, JOptionPane.CLOSED_OPTION -> {
        if (disregardedCount < MAX_MESSAGES) {
            disregardedMessages[disregardedCount++] = msg;
            JOptionPane.showMessageDialog(null, "Message disregarded.");
        }
    }
}
    }
 // Store sent messages to JSON file
 //It also covert JSON file.
 public void storeSentMessagesToJson() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("messages.json"))) {
            writer.write("[\n");
            for (int i = 0; i < sentCount; i++) {
                writer.write(sentMessages[i].toJson());
                if (i < sentCount - 1) writer.write(",\n");
            }
            writer.write("\n]");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving messages: " + e.getMessage());
        }
    }

// Load messages from JSON file into storedMessages
public void loadStoredMessagesFromJson() {
    try (BufferedReader reader = new BufferedReader(new FileReader("messages.json"))) {
        StringBuilder json = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
        json.append(line.trim());
            }

        String[] entries = json.toString().replace("[", "").replace("]", "").split("},");
        for (String entry : entries) {
                if (storedCount >= MAX_MESSAGES) break;

                entry = entry.replace("{", "").replace("}", "").trim();
                String[] fields = entry.split(",");

                String recipient = "", text = "";
                for (String field : fields) {
                    String[] pair = field.split(":", 2);
                    if (pair.length < 2) continue;

                    String key = pair[0].replace("\"", "").trim();
                    String value = pair[1].replace("\"", "").trim();

                    if (key.equals("recipient")) recipient = value;
                    if (key.equals("messageText")) text = value;
                }

                storedMessages[storedCount++] = new Message(recipient, text);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading JSON file: " + e.getMessage());
        }
    }
//This method displays the details of all sent messages.
public void displaySendersAndRecipients() {
        if (sentCount == 0) {
            JOptionPane.showMessageDialog(null, "No sent messages.");
            return;
        }
        StringBuilder sb = new StringBuilder("Sent Messages:\n");
        for (int i = 0; i < sentCount; i++) {
            sb.append(sentMessages[i].displayMessageDetails()).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

//This method displays all the messages that were stored
public void displayStoredMessages() {
        if (storedCount == 0) {
            JOptionPane.showMessageDialog(null, "No stored messages.");
            return;
        }
        StringBuilder sb = new StringBuilder("Stored Messages:\n");
        for (int i = 0; i < storedCount; i++) {
            sb.append(storedMessages[i].displayMessageDetails()).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

//Method that displays all disregarded messages
public void displayDisregardedMessages() {
        if (disregardedCount == 0) {
            JOptionPane.showMessageDialog(null, "No disregarded messages.");
            return;
        }
        StringBuilder sb = new StringBuilder("Disregarded Messages:\n");
        for (int i = 0; i < disregardedCount; i++) {
            sb.append(disregardedMessages[i].displayMessageDetails()).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

//Method that sent messages for a specific messageID and displays the result.
public void searchByMessageID(String id) {
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].getMessageID().equals(id)) {
                JOptionPane.showMessageDialog(null, "Message Found:\n" + sentMessages[i].displayMessageDetails());
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "No message found with ID: " + id);
}

//Method that searches sent messages for all the messages that are sent
public void searchByRecipient(String recipient) {
        boolean found = false;
        StringBuilder sb = new StringBuilder("Messages to recipient: " + recipient + "\n");
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].getRecipient().equals(recipient)) {
                sb.append(sentMessages[i].displayMessageDetails()).append("\n\n");
                found = true;
            }
        }
        if (found) {
            JOptionPane.showMessageDialog(null, sb.toString());
        } else {
            JOptionPane.showMessageDialog(null, "No messages found for this recipient.");
        }
    }

//Method that deletes a message from the sent message array using its message hash.
public void deleteByMessageHash(String hash) {
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].getMessageHash().equalsIgnoreCase(hash)) {
                for (int j = i; j < sentCount - 1; j++) {
                    sentMessages[j] = sentMessages[j + 1];
                }
                sentMessages[--sentCount] = null;
                JOptionPane.showMessageDialog(null, "Message with hash " + hash + " deleted.");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message hash not found.");
    }
//Method that finds and displays the longest sent message by checking the message text lengths.
public void displayLongestSentMessage() {
        if (sentCount == 0) {
            JOptionPane.showMessageDialog(null, "No sent messages to evaluate.");
            return;
        }

        Message longest = sentMessages[0];
        for (int i = 1; i < sentCount; i++) {
            if (sentMessages[i].getMessageText().length() > longest.getMessageText().length()) {
                longest = sentMessages[i];
            }
        }
        JOptionPane.showMessageDialog(null, "Longest Sent Message:\n" + longest.displayMessageDetails());
    }

//Method that displays a full report of all sent messages.
public void displayFullSentReport() {
        if (sentCount == 0) {
            JOptionPane.showMessageDialog(null, "No messages to report.");
            return;
        }

        int totalChars = 0;
        for (int i = 0; i < sentCount; i++) {
            totalChars += sentMessages[i].getMessageText().length();
        }
        double avgLength = (double) totalChars / sentCount;

        String report = """
                        Full Sent Report
                        ----------------------
                        Total messages sent: """ + sentCount + "\n" +
                "Average message length: " + String.format("%.2f", avgLength);
        JOptionPane.showMessageDialog(null, report);
    }

// Add message to sent list for testing
public void addSentMessage(Message message) {
    if (sentCount < MAX_MESSAGES) {
        sentMessages[sentCount++] = message;
    }
}
//
public Message[] getSentMessages() {
    return sentMessages;
}

public String getLongestMessage() {
    if (sentCount == 0) return "No sent messages.";

    Message longest = sentMessages[0];
    for (int i = 1; i < sentCount; i++) {
        if (sentMessages[i].getMessageText().length() > longest.getMessageText().length()) {
            longest = sentMessages[i];
        }
    }
    return longest.displayMessageDetails();
}

public String searchMessageByID(String id) {
    for (int i = 0; i < sentCount; i++) {
        if (sentMessages[i].getMessageID().equals(id)) {
            return sentMessages[i].displayMessageDetails();
        }
    }
    return "Message ID not found.";
}

public String searchMessagesByRecipient(String recipient) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < sentCount; i++) {
        if (sentMessages[i].getRecipient().equals(recipient)) {
            sb.append(sentMessages[i].displayMessageDetails()).append("\n\n");
        }
    }
    return sb.length() == 0 ? "No messages found for this recipient." : sb.toString();
}

public String deleteMessageByHash(String hash) {
    for (int i = 0; i < sentCount; i++) {
        if (sentMessages[i].getMessageHash().equalsIgnoreCase(hash)) {
            for (int j = i; j < sentCount - 1; j++) {
                sentMessages[j] = sentMessages[j + 1];
            }
            sentMessages[--sentCount] = null;
            return "Message deleted successfully.";
        }
    }
    return "Message hash not found.";
}

public String getFullSentReport() {
    if (sentCount == 0) return "No messages to report.";

    int totalChars = 0;
    for (int i = 0; i < sentCount; i++) {
        totalChars += sentMessages[i].getMessageText().length();
    }

    double avgLength = (double) totalChars / sentCount;
    return """
            Full Sent Report
            ----------------------
            Total messages sent: """ + sentCount + "\n" +
            "Average message length: " + String.format("%.2f", avgLength);
}
}