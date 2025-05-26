/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chat;

/**
 *
 * @author lab_services_student
 */

import java.util.Random;

public class Message {

    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private static int messageCount = 0;

    // Constructor
    // validate message length
    public Message(String recipient, String messageText) {
        if (messageText.length() > 250) {
            throw new IllegalArgumentException("Message must be less than 250 characters.");
        }
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = generateMessageHash();
        messageCount++;
    }
  //Method that generates a 10-digit random messageId
    private String generateMessageID() {
        Random rand = new Random();
        long id = 1000000000L + (long) (rand.nextDouble() * 8999999999L);
        return String.valueOf(id);
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    //Get first two digits of the message ID
    //Extract first and last name
    //build the hash   
    private String generateMessageHash() {
       String firstTwoDigits = messageID.substring(0, 2);
       
       String [] words = messageText.trim().split("\\s+");
       String firstWord = words.length > 0 ? words [0] : "None";
       String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
       
       String hash = firstTwoDigits + ":" + messageCount + ":" + (firstWord + lastWord);
       return hash.toUpperCase();
    }
     //Return message details as a string
    public String displayMessageDetails() {
        return "MessageID: " + messageID +
                "\nMessage Hash: " + messageHash +
                "\nRecipient: " + recipient +
                "\nMessage: " + messageText;
    }
    //Method that checks recipient cell format
    public static boolean checkRecipientCell(String recipient) {
        return recipient.startsWith("+27") && recipient.length() <= 12;
    }
    //method that gets the total number of cell format
    public static int getMessageCount() {
        return messageCount;
    }
    //method that returns the total messages sent
    public static int returnTotalMessages(){
        return messageCount;
    }
    //this returns the formatted list of messages
    public static String printMessages(java.util.ArrayList<Message>messages){
    if (messages.isEmpty()){
        return "No messages sent.";
    }
    StringBuilder sb = new StringBuilder("Sent Messages: ");
    for (Message m : messages) {
        sb.append(m.displayMessageDetails()).append("");
    }
    return sb.toString();
   
   } 
    //Stores messages into a JSON file
    public String toJson() {
        return "" +
               "  \"messageID\": \"" + messageID + "\",\n" +
               "  \"messageHash\": \"" + messageHash + "\",\n" +
               "  \"recipient\": \"" + recipient + "\",\n" +
               "  \"messageText\": \"" + messageText + "\"\n" +
               "}";
        
    }
    //Getters for testing
    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }
    public String getMessageText(){
        return messageText;
    }
    public String getRecipient(){
        return recipient;
    }
}