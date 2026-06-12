import org.junit.jupiter.api.*;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    @BeforeEach
    void resetManager() throws Exception {
        Field field = Manager.class.getDeclaredField("messageCount");
        field.setAccessible(true);
        field.set(null, 0);
    }

    @Test
    void testAddMessage() {
        Manager.addMessage("Alice", "ID1", "Bob", "Hello", "Hash1");
        assertDoesNotThrow(() -> Manager.displayFullReport());
    }

    @Test
    void testSearchByMessageIdFound() {
        Manager.addMessage("Alice", "ID1", "Bob", "Hello", "Hash1");
        assertDoesNotThrow(() -> Manager.searchByMessageId("ID1"));
    }

    @Test
    void testSearchByMessageIdNotFound() {
        assertDoesNotThrow(() -> Manager.searchByMessageId("NONEXISTENT"));
    }

    @Test
    void testDeleteMessageByHash() {
        Manager.addMessage("Alice", "ID1", "Bob", "Hello", "Hash1");
        Manager.deleteMessageByHash("Hash1");
        assertDoesNotThrow(() -> Manager.searchByMessageId("ID1"));
    }

    @Test
    void testLongestMessage() {
        Manager.addMessage("A", "ID1", "B", "Short", "H1");
        Manager.addMessage("A", "ID2", "B", "This is the longest message", "H2");

        assertDoesNotThrow(() -> Manager.displayLongestMessage());
    }
}