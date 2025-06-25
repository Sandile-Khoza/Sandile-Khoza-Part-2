/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.chat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageManagerTest {

    private MessageManager manager;

    @BeforeEach
    public void setup() {
        manager = new MessageManager();

        // messages
        Message msg1 = new Message("+27838884567", "Did you get the cake?");
        Message msg2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        Message msg3 = new Message("+27838884567", "Ok, I am leaving without you to be on time.");
        Message msg4 = new Message("+27838884567", "It is dinner time!");

        manager.addSentMessage(msg1);
        manager.addSentMessage(msg2);
        manager.addSentMessage(msg3);
        manager.addSentMessage(msg4);
    }
    //This tests the sent messages
    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        assertEquals("Did you get the cake?", manager.getSentMessages()[0].getMessageText());
        assertEquals("It is dinner time!", manager.getSentMessages()[3].getMessageText());
    }
    //This test the longest message 
    @Test
    public void testDisplayLongestMessage() {
        String longest = manager.getLongestMessage();
        assertTrue(longest.contains("Where are you? You are late! I have asked you to be on time."));
    }
    //this method test to search by messageid
    @Test
    public void testSearchByMessageID() {
        Message target = manager.getSentMessages()[3]; // "It is dinner time!"
        String result = manager.searchMessageByID(target.getMessageID());
        assertTrue(result.contains("It is dinner time!"));
    }
    //This method test by search recipient
    @Test
    public void testSearchByRecipient() {
        String result = manager.searchMessagesByRecipient("+27838884567");
        assertTrue(result.contains("Where are you? You are late!"));
        assertTrue(result.contains("Ok, I am leaving without you"));
        assertTrue(result.contains("It is dinner time!"));
    }
   //This test is message is deleted by hash
    @Test
    public void testDeleteByMessageHash() {
        Message toDelete = manager.getSentMessages()[1]; // msg2
        String hash = toDelete.getMessageHash();

        String response = manager.deleteMessageByHash(hash);
        assertEquals("Message deleted successfully.", response);

        // Ensure message was deleted
        String check = manager.searchMessageByID(toDelete.getMessageID());
        assertEquals("Message ID not found.", check);
    }
    //This test the full sent report
    @Test
    public void testDisplayFullSentReport() {
        String report = manager.getFullSentReport();
        assertTrue(report.contains("Total messages sent"));
        assertTrue(report.contains("Average message length"));
    }
}