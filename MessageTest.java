import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @BeforeEach
    public void setUp() {
        // Reset static state before every test to avoid test contamination
        Message.resetAll();

        // Clean up the JSON storage file if it exists
        File file = new File("messages.json");
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testConstructorAndIDGeneration() {
        Message msg = new Message("+27831234567", "Hello World", 1);

        assertNotNull(msg.getMessageID(), "Message ID should not be null");
        assertTrue(msg.checkMessageID(), "Message ID should be 10 digits or less");
        assertEquals("+27831234567", msg.getRecipient());
        assertEquals("Hello World", msg.getMessage());
    }

    @Test
    public void testCheckRecipientCell_Valid() {
        Message msg = new Message("+27831234567", "Valid number test", 1);
        String expectedResponse = "Cell phone number successfully captured.";
        assertEquals(expectedResponse, msg.checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCell_Invalid() {
        // Test incorrect length
        Message shortMsg = new Message("+2783123", "Short number", 1);
        assertTrue(shortMsg.checkRecipientCell().contains("incorrectly formatted"));

        // Test missing prefix
        Message wrongPrefixMsg = new Message("083123456789", "Wrong prefix", 1);
        assertTrue(wrongPrefixMsg.checkRecipientCell().contains("incorrectly formatted"));
    }

    @Test
    public void testCreateMessageHash() {
        Message msg = new Message("+27831234567", "Testing the hashing structure functionality", 1);
        String hash = msg.getMessageHash();

        // The tracking string converts everything to uppercase
        // Format should be [2 digits of ID]:[numMessagesSent]:TESTINGFUNCTIONALITY
        String idPrefix = msg.getMessageID().substring(0, 2);
        String expectedHash = (idPrefix + ":0:TESTINGFUNCTIONALITY").toUpperCase();

        assertEquals(expectedHash, hash);
    }

    @Test
    public void testCheckMessageLength_Valid() {
        Message msg = new Message("+27831234567", "Short message", 1);
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testCheckMessageLength_Invalid() {
        // Generate a string longer than 250 characters
        StringBuilder longStringBuilder = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            longStringBuilder.append("a");
        }

        Message msg = new Message("+27831234567", longStringBuilder.toString(), 1);
        String result = msg.checkMessageLength();

        assertTrue(result.contains("Message exceeds 250 characters"));
        assertTrue(result.contains("by 10"));
    }

    @Test
    public void testSentMessage_Option1_Send() {
        // Simulate typing "1" into the console
        provideInput("1");

        Message msg = new Message("+27831234567", "Sending a message", 1);
        String result = msg.SentMessage();

        assertEquals("Message successfully sent.", result);
        assertEquals("Sent", msg.getSentStatus());
        assertEquals(1, msg.returnTotalMessages());
    }

    @Test
    public void testSentMessage_Option2_Disregard() {
        // Simulate typing "2" into the console
        provideInput("2");

        Message msg = new Message("+27831234567", "Disregarding a message", 1);
        String result = msg.SentMessage();

        assertEquals("Press 0 to delete the message.", result);
        assertEquals("Disregarded", msg.getSentStatus());
        assertEquals(0, msg.returnTotalMessages()); // Should not increment total messages
    }

    @Test
    public void testSentMessage_Option3_Store() throws Exception {
        // Simulate typing "3" into the console
        provideInput("3");

        Message msg = new Message("+27831234567", "Storing a message", 1);
        String result = msg.SentMessage();

        assertEquals("Message successfully stored.", result);
        assertEquals("Stored", msg.getSentStatus());

        // Verify that the file was created and contains the JSON elements
        File file = new File("messages.json");
        assertTrue(file.exists(), "messages.json should be created.");

        String fileContent = new String(Files.readAllBytes(Paths.get("messages.json")));
        assertTrue(fileContent.contains(msg.getMessageID()));
        assertTrue(fileContent.contains("+27831234567"));
        assertTrue(fileContent.contains("Storing a message"));
    }

    @Test
    public void testSentMessage_InvalidOption() {
        // Simulate an invalid choice like "9"
        provideInput("9");

        Message msg = new Message("+27831234567", "Invalid option test", 1);
        String result = msg.SentMessage();

        assertEquals("Invalid option selected.", result);
        assertNull(msg.getSentStatus());
    }

    // Helper method to Mock System.in console user inputs
    private void provideInput(String data) {
        InputStream testInput = new ByteArrayInputStream(data.getBytes());
        System.setIn(testInput);
    }
}
