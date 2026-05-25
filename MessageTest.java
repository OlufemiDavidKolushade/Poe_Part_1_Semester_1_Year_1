import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    // Clears static counters and history arrays before running every single test case
    @BeforeEach
    public void setUp() {
        Message.resetAll();
    }

    @Test
    public void testConstructorAndIDGeneration() {
        Message msg = new Message("+27821234567", "Hello World", 0);

        assertNotNull(msg.getMessageID(), "Message ID should automatically generate.");
        assertEquals(10, msg.getMessageID().length(), "Generated ID must be exactly 10 digits.");
        assertTrue(msg.checkMessageID(), "Validates true when ID meets requirements.");
    }

    @Test
    public void testCheckRecipientCell_Valid() {
        // Valid South African format: Starts with +27 and has exactly 12 total characters
        Message validMsg = new Message("+27821234567", "Valid test", 0);
        String expectedResponse = "Cell phone number successfully captured.";

        assertEquals(expectedResponse, validMsg.checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCell_Invalid() {
        // Missing the required country code prefix (+27)
        Message invalidPrefix = new Message("0821234567", "Invalid prefix test", 0);
        // Correct prefix but too short (under 12 characters)
        Message invalidLength = new Message("+2782123", "Too short test", 0);

        String failureResponse = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";

        assertEquals(failureResponse, invalidPrefix.checkRecipientCell());
        assertEquals(failureResponse, invalidLength.checkRecipientCell());
    }

    @Test
    public void testCreateMessageHash() {
        Message msg = new Message("+27821234567", "java programming is fun", 0);
        String generatedHash = msg.getMessageHash();
        String prefix = msg.getMessageID().substring(0, 2);

        // Expected format shape: PREFIX:TOTAL_SENT:FIRSTWORDLASTWORD (All upper case)
        String expectedHashShape = (prefix + ":0:JAVAFUN").toUpperCase();

        assertEquals(expectedHashShape, generatedHash);
    }

    @Test
    public void testCheckMessageLength_Valid() {
        Message msg = new Message("+27821234567", "Short message", 0);
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testCheckMessageLength_Excessive() {
        // Generates an explicit 255 character text body to trigger calculation boundaries
        String longText = "a".repeat(255);
        Message msg = new Message("+27821234567", longText, 0);

        String expectedResponse = "Message exceeds 250 characters by 5; please reduce the size.";
        assertEquals(expectedResponse, msg.checkMessageLength());
    }

    @Test
    public void testSentMessage_OptionSend() {
        Message msg = new Message("+27821234567", "Testing console automation choice 1", 0);

        // Simulates a user typing "1" into the console terminal followed by a return key press
        InputStream simulatedInput = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(simulatedInput);

        String result = msg.SentMessage();

        assertEquals("Message successfully sent.", result);
        assertEquals("Sent", msg.getSentStatus());
        assertEquals(1, msg.returnTotalMessages(), "Global message sent metrics tracking should increment.");
    }

    @Test
    public void testSentMessage_OptionDisregard() {
        Message msg = new Message("+27821234567", "Testing console automation choice 2", 0);

        // Simulates user typing "2" into the console terminal
        InputStream simulatedInput = new ByteArrayInputStream("2\n".getBytes());
        System.setIn(simulatedInput);

        String result = msg.SentMessage();

        assertEquals("Press 0 to delete the message.", result);
        assertEquals("Disregarded", msg.getSentStatus());
        assertEquals(0, msg.returnTotalMessages(), "Disregarded selections should not alter global counts.");
    }

    @Test
    public void testSentMessage_OptionInvalid() {
        Message msg = new Message("+27821234567", "Testing edge invalid values", 0);

        // Simulates bad inputs outside defined switch-case logic ranges
        InputStream simulatedInput = new ByteArrayInputStream("9\n".getBytes());
        System.setIn(simulatedInput);

        String result = msg.SentMessage();

        assertEquals("Invalid option selected.", result);
    }
}
