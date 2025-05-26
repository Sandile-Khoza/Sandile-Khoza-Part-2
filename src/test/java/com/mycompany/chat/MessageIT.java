/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.chat;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class MessageIT {

    @Test
    //This test if the message length
    public void testMessageLengthSuccess() {
        String validText = "This is a short valid message.";
        Message msg = new Message("+27663551200", validText);
        assertEquals(validText, msg.getMessageText());
    }

    @Test
    //This method tests if the message is not more more than 250
    public void testMessageLengthFailure() {
        StringBuilder longText = new StringBuilder();
        for (int i = 0; i < 251; i++) {
            longText.append("a");
        }

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Message message = new Message("+27663551200", longText.toString());
        });

        assertEquals("Message must be less than 250 characters.", exception.getMessage());
    }

    @Test
    //Method that makes sure that the recipient number is successfully captured
    public void testCheckRecipientSuccess() {
        assertTrue(Message.checkRecipientCell("+27663551200"));
    }

    @Test
    //This cell phone number test is incorrect formatted and does not contain an international code
    public void testCheckRecipientFailure() {
        assertFalse(Message.checkRecipientCell("0663551200"));
    }

    @Test
    //This test ensure message hash which should return eg00:0 HITONIGHT
    public void testMessageHashFormat() {
        Message msg = new Message("+27663551200", "Hi to night");
        String hash = msg.getMessageHash();

        assertTrue(hash.matches("^\\d{2}:\\d+:[A-Z0-9]+$"), "Hash format is incorrect");
        assertTrue(hash.contains("HI") || hash.contains("NIGHT")); // checks key words
    }

    @Test
    //this test message ID
    public void testMessageIDLength() {
        Message msg = new Message("+27663551200", "Check message ID");
        assertTrue(msg.getMessageID().length() <= 10, "Message ID is too long");
    }

    @Test
    public void testMessageListDisplay() {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message("+27663551200", "Hello World"));
        String result = Message.printMessages(messages);
        assertTrue(result.contains("MessageID"));
        assertTrue(result.contains("Hello World"));
    }

    @Test
    //This tests "Store,Diregard and Store 
    public void testMessageActionsSimulation() {
        Message sendMessage = new Message("+27663551200", "Send this");
        Message storeMessage = new Message("+27663551200", "Store this");

        ArrayList<Message> sentMessages = new ArrayList<>();
        sentMessages.add(sendMessage);
        sentMessages.add(storeMessage);

        assertEquals(2, sentMessages.size(), "Messages not tracked properly");
        assertEquals("Send this", sentMessages.get(0).getMessageText());
        assertEquals("Store this", sentMessages.get(1).getMessageText());
    }
}