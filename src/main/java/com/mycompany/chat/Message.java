/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chat;


import javax.swing.JOptionPane;
import java.util.Random;

public class Message {

  

    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;

    private static final int MAX_MESSAGES = 100;
    private static Message[] sentMessages = new Message[MAX_MESSAGES];
    private static int sentCount = 0;

    private static int messageCount = 0;

    //Constructor
    public Message(String recipient, String messageText) {
        if (messageText.length() > 250) {
            throw new IllegalArgumentException("Message must be less than 250 characters.");
        }
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = generateMessageHash();

        // This Automatically store this message in the sentMessages array
        if (sentCount < MAX_MESSAGES) {
            sentMessages[sentCount++] = this;
        }

        messageCount++;
    }
    //This method Generate a 10-digit message ID
    private String generateMessageID() {
        Random rand = new Random();
        long id = 1000000000L + (long) (rand.nextDouble() * 8999999999L);
        return String.valueOf(id);
    }
    //This Method Ensure message ID is not more than 10 characters.
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // This method Ensure cell number starts with +27 and ≤ 12 characters
    public static boolean checkRecipientCell(String recipient) {
        return recipient.startsWith("+27") && recipient.length() <= 12;
    }

    // This method ensures that returns 1 if valid, 0 if not
    public static int checkRecipientCellLength(String recipient) {
        return (recipient.startsWith("+27") && recipient.length() <= 12) ? 1 : 0;
    }

    //This method generate and return the message hash
    public String createMessageHash() {
        return generateMessageHash();
    }

    private String generateMessageHash() {
        String firstTwoDigits = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "None";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        return (firstTwoDigits + ":" + messageCount + ":" + firstWord + lastWord).toUpperCase();
    }

    // === Method: Display message details ===
    public String displayMessageDetails() {
        return "MessageID: " + messageID +
                "\nMessage Hash: " + messageHash +
                "\nRecipient: " + recipient +
                "\nMessage: " + messageText;
    }

    // === Method: User chooses to send, store, or disregard ===
    public static String SentMessage() {
        String[] options = {"Send", "Store", "Disregard"};
        int choice = JOptionPane.showOptionDialog(null,
                "What would you like to do with the message?",
                "Choose Option",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) return "Send";
        if (choice == 1) return "Store";
        return "Disregard";
    }

    //This Method Return total number of messages created 
    public static int returnTotalMessages() {
        return messageCount;
    }

    //This method Print all messages stored in array
    public static String printMessages() {
        if (sentCount == 0) {
            return "No messages sent.";
        }
        StringBuilder sb = new StringBuilder("Sent Messages:\n");
        for (int i = 0; i < sentCount; i++) {
            sb.append(sentMessages[i].displayMessageDetails()).append("\n\n");
        }
        return sb.toString();
    }

    // this method Convert message to JSON string format
    public String toJson() {
        return "{\n" +
                "  \"messageID\": \"" + messageID + "\",\n" +
                "  \"messageHash\": \"" + messageHash + "\",\n" +
                "  \"recipient\": \"" + recipient + "\",\n" +
                "  \"messageText\": \"" + messageText + "\"\n" +
                "}";
    }
    
    // Getters for use in MessageManager
    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getRecipient() {
        return recipient;
    }

    static class setMessageLimit {

        public setMessageLimit() {
        }
    }
}
