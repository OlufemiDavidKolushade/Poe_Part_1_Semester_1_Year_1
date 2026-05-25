import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Message {

    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;
    private String sentStatus;

    private static int numMessagesSent = 0;
    private static List<Message> messageHistory = new ArrayList<>(); //to store messages in array lists

    //Constructor to initialize a new Message object.
    public Message(String recipient, String message, int i) {
        this.recipient  = recipient;
        this.message    = message;
        this.messageID  = generateMessageId(); //generates a unique 10-digit tracking ID for the message
        this.messageHash = createMessageHash(); //generates a unique security
    }

    //This checks if the users ID is not more than 10 digit
    public boolean checkMessageID() {

        return messageID != null && messageID.length() <= 10;
    }

    // checks if user's number starts with +27 and is exactly 12 characters long
    public String checkRecipientCell() {
        if (recipient != null && recipient.startsWith("+27") && recipient.length() == 12) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. " +
                "Please correct the number and try again.";
    }


    //Combines first and last words together all in caps
    public String createMessageHash() {
        if (messageID == null || message == null || message.trim().isEmpty()) return "";

        //Extracts up to the first 2 characters of the message ID safely
        String idPrefix  = messageID.substring(0, Math.min(2, messageID.length()));

        //Splits the message text into an array of words
        String[] words   = message.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord  = words[words.length - 1];

        //Combines the pieces into a structured tracking string which get forced into uppercase
        return (idPrefix + ":" + numMessagesSent + ":" + firstWord + lastWord).toUpperCase();
    }

    //This handles the  user  interaction by using console menu to process the message staus
    public String SentMessage() {
        Scanner sc = new Scanner(System.in);

        //Prints out choices for the user to use
        System.out.println("\nWhat would you like to do with this message?");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message");
        System.out.print("Select an option: ");

        String choice = sc.nextLine().trim();

        //This process the actions based on the user's choice option
        switch (choice) {
            case "1": 
                sentStatus = "Sent";
                numMessagesSent++;// this increments the counter for total messages sent
                messageHistory.add(this); //add the specific message object to global history
                return "Message successfully sent.";

            case "2":
                sentStatus = "Disregarded";
                return "Press 0 to delete the message.";

            case "3":
                sentStatus = "Stored";
                messageHistory.add(this); //This helps by keeping a record in the global history
                storeMessage();//Allows for storage procession
                return "Message successfully stored.";

            default:
                return "Invalid option selected.";
        }
    }

    //JSON STORAGE SYSTEM
    public void storeMessage() {
        String jsonPayLoad = "{\n"
                +" \"MessageID\":\"" + this.messageID
                + "\",\n"
                +" \"Recipient\":\"" +this.recipient
                +"\",\n"
                +" \"Message\":\"" +this.message
                +"\"\n"+"}";

        try(FileWriter file = new FileWriter("messages.json",true)){
            file.write(jsonPayLoad + "\n");
        } catch(IOException e){
            System.out.println("Error processing storage: " +e.getMessage());
        }

    }


    //returns the absolute count of successfully sent messages system-wide.
    public int returnTotalMessages() {
        return numMessagesSent;
    }

    // Checks if message length is less than 250 words
    public String checkMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        }
        int excess = message.length() - 250;
        return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
    }

    private static String generateMessageId() {
        Random random = new Random();
        String id = "";

        for (int i = 0; i < 10; i++) {
            id += random.nextInt(10);
        }
        return id;
    }

//getter methods to retrieve private instance variables
    public String getMessageID()   { return messageID; }
    public String getRecipient()   { return recipient; }
    public String getMessage()     { return message; }
    public String getMessageHash() { return messageHash; }
    public String getSentStatus()  { return sentStatus; }


    //method to completely clear records
    public static void resetAll() {
        numMessagesSent = 0;
        messageHistory.clear();
    }

    //this is a summary of this specific message's attributes directly to the console window.
    public void printMessage() {
        System.out.println("\n---Message Details---");
        System.out.println("Message Id: " + messageID);
        System.out.println("Message Hash: " + createMessageHash());
        System.out.println("Recipient: " + recipient);
        System.out.println("Messages " + message);
    }

    

}
