import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @BeforeEach
    void setUp() {
        // Reset static state before every test to ensure test isolation
        Message.resetAll();
    }

    @Test
    void testConstructorAndIDGeneration() {
        Message msg = new Message("+27831234567", "Hello World", 1);

        assertNotNull(msg.getMessageID(), "Message ID should be generated.");
        assertEquals(10, msg.getMessageID().length(), "Message ID must be exactly 10 characters.");
        assertTrue(msg.checkMessageID(), "checkMessageID should return true for valid 10-char IDs.");
    }

    @Test
    void testCheckRecipientCell_Valid() {
        Message msg = new Message("+27831234567", "Test message", 1);
        String expectedResult = "Cell phone number successfully captured.";
        assertEquals(expectedResult, msg.checkRecipientCell());
    }

    @Test
    void testCheckRecipientCell_Invalid() {
        // Test missing prefix
        Message msg1 = new Message("0831234567", "Test", 1);
        assertTrue(msg1.checkRecipientCell().contains("incorrectly formatted"));

        // Test tool long (e.g., 13 characters)
        Message msg2 = new Message("+278312345678", "Test", 1);
        assertTrue(msg2.checkRecipientCell().contains("incorrectly formatted"));
    }

    @Test
    void testCheckMessageLength() {
        // Under limit
        Message shortMsg = new Message("+27831234567", "Short text", 1);
        assertEquals("Message ready to send.", shortMsg.checkMessageLength());

        // Over limit
        String longText = "a".repeat(255);
        Message longMsg = new Message("+27831234567", longText, 1);
        assertEquals("Message exceeds 250 characters by 5; please reduce the size.", longMsg.checkMessageLength());
    }

    @Test
    void testCreateMessageHash() {
        Message msg = new Message("+27831234567", "Hello software testing world", 1);
        String hash = msg.createMessageHash();

        // Structure should be PREFIX:NUM_SENT:FIRSTWORDLASTWORD (All Uppercase)
        String prefix = msg.getMessageID().substring(0, 2);
        String expectedHash = (prefix + ":0:HELLOWORLD").toUpperCase();

        assertEquals(expectedHash, hash);
    }

    @Test
    void testSentMessage_Option1_Send() {
        Message msg = new Message("+27831234567", "Sending this message", 1);

        // Simulate user typing "1" into the console
        provideInput("1");

        String result = msg.SentMessage();

        assertEquals("Message successfully sent.", result);
        assertEquals("Sent", msg.getSentStatus());
        assertEquals(1, msg.returnTotalMessages(), "Total global messages sent should increment.");
    }

    @Test
    void testSentMessage_Option2_Disregard() {
        Message msg = new Message("+27831234567", "Disregarding this message", 1);

        provideInput("2");
        String result = msg.SentMessage();

        assertEquals("Press 0 to delete the message.", result);
        assertEquals("Disregarded", msg.getSentStatus());
        assertEquals(0, msg.returnTotalMessages(), "Total global messages should not increment.");
    }

    @Test
    void testSentMessage_Option3_Store() {
        Message msg = new Message("+27831234567", "Storing this message", 1);

        provideInput("3");
        String result = msg.SentMessage();

        assertEquals("Message successfully stored.", result);
        assertEquals("Stored", msg.getSentStatus());
        assertEquals(0, msg.returnTotalMessages(), "Total sent count should remain 0 when stored.");
    }

    @Test
    void testPrintMessages_EmptyAndPopulated() {
        Message msg = new Message("+27831234567", "History test", 1);

        // Before adding history
        assertEquals("No messages have been sent or stored yet.", msg.printMessages());

        // Send message to add it to history
        provideInput("1");
        msg.SentMessage();

        String historyTable = msg.printMessages();
        assertTrue(historyTable.contains("Message ID"));
        assertTrue(historyTable.contains("History test"));
    }

    /**
     * Helper method to mock System.in console inputs.
     */
    private void provideInput(String data) {
        InputStream testInput = new ByteArrayInputStream(data.getBytes());
        System.setIn(testInput);
    }
}