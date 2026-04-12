/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package TestClasses;



/**
 *
 * @author femik
 */
import com.mycompany.poepart1.Login;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginTest {

    private Login login;

    @BeforeEach
    void setUp() {
        login = new Login();
    }

    @Test
    void testCheckUserName_Valid() {
        // Must contain "_" and be <= 5 characters
        assertTrue(login.checkUserName("ky_le"), "Username should be valid");
    }

    @Test
    void testCheckUserName_Invalid() {
        assertFalse(login.checkUserName("kyle"), "Missing underscore should fail");
        assertFalse(login.checkUserName("kyle_smith"), "More than 5 characters should fail");
    }

    @Test
    void testCheckPasswordComplexity_Valid() {
        // Must be >= 8 chars, have Upper, Digit, and Special
        assertTrue(login.checkPasswordComplexity("Ch@ck1ng"), "Password should be valid");
    }

    @Test
    void testCheckPasswordComplexity_Invalid() {
        assertFalse(login.checkPasswordComplexity("password"), "Missing everything should fail");
        assertFalse(login.checkPasswordComplexity("Short1!"), "Less than 8 chars should fail");
    }

    @Test
    void testCheckCellNumber_Valid() {
        // Must start with +27 and have 9 digits after
        assertTrue(login.checkCellNumber("+27123456789"), "Phone number should be valid");
    }

    @Test
    void testCheckCellNumber_Invalid() {
        assertFalse(login.checkCellNumber("0721234567"), "Missing +27 should fail");
        assertFalse(login.checkCellNumber("+27123"), "Too short should fail");
    }

    @Test
    void testRegisterUser_Success() {
        login.loginDetails("ky_le", "Ch@ck1ng", "John", "Doe", "+27123456789");
        String expected = "Username was successfully captured. \nPassword successfully captured. \nCell number successfully captured";
        assertEquals(expected, login.registerUser());
    }

    @Test
    void testLoginUser_Success() {
        login.loginDetails("ky_le", "Ch@ck1ng", "John", "Doe", "+27123456789");
        assertTrue(login.LoginUser("ky_le", "Ch@ck1ng"), "Login should succeed with correct credentials");
    }

    @Test
    void testReturnLoginStatus_Success() {
        login.loginDetails("ky_le", "Ch@ck1ng", "John", "Doe", "+27123456789");
        String status = login.returnLoginStatus(true);
        assertEquals("welcome John, Doe It is great to see you again", status);
    }
}
