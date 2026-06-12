import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner Sc = new Scanner(System.in);
        Login auth = new Login();

        System.out.println("========================");
        System.out.println("| Registration process |");
        System.out.println("========================\n");


        System.out.println("----Enter Firstname----");
        String Firstname = Sc.nextLine();


        System.out.println("----Enter Lastname----");
        String Lastname = Sc.nextLine();


        System.out.println("Enter Username: ");

        String Username = Sc.nextLine();


        System.out.println("Enter CellNumber(+27): ");
        String CellNumber = Sc.nextLine();


        System.out.println("Enter Password: ");

        String Password = Sc.nextLine();


        auth.loginDetails(Username, Firstname, Lastname, Password, CellNumber);

        String regStatus = auth.registerUser();
        System.out.println("\n====================================" +
                                "\n" +regStatus + "\n"+
                            "====================================");

        if (regStatus.toLowerCase().contains("successfully captured")) {
            System.out.println("\n---Login---");

            System.out.println("Enter Username: ");
            String loginUser = Sc.nextLine();

            System.out.println("Enter Password: ");
            String loginPass = Sc.nextLine();

            boolean loginResult = auth.LoginUser(loginUser, loginPass);

            System.out.println(auth.returnLoginStatus(loginResult));

            while (!loginResult) {
                System.out.println("\n[!] Login failed. Please try again.");

                System.out.println("Enter Username: ");
                String retryUser = Sc.nextLine();

                System.out.println("Enter Password: ");
                String retryPass = Sc.nextLine();

                loginResult = auth.LoginUser(retryUser, retryPass);

                if (loginResult) {
                    System.out.println(auth.returnLoginStatus(loginResult));
                } else {
                    if (!retryUser.equals(Username)) {
                        System.out.println("The username you entered does not match our records.");
                    } else {
                        System.out.println("The password you entered is incorrect.");
                    }
                }
            }


            if (loginResult) {
                System.out.println("\n========================");
                System.out.println("Welcome to Quick Chat!");
                System.out.println("========================");
            }

            System.out.println("\n ~~~How many messages do you want to print?~~~ ");
            int totMessages = Sc.nextInt();

            Message[] messages = new Message[totMessages];

            int finalSent = 0;
            int choice = 0;

            Message msg = null;
            while (choice != 3) {
                System.out.println("\n========");
                System.out.println("| Menu |");
                System.out.println("========");

                System.out.println("1) Send messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Display Longest Message");
                System.out.println("4) Search message by Id");
                System.out.println("5) Search messages by recipient");
                System.out.println("6) Delete message by hash");
                System.out.println("7) Display full report");
                System.out.println("8) Quit");
                System.out.println("Choose which option to enter: ");
                choice = Sc.nextInt();

                //Please serve the Lord you God with all you heart because he is Christ Jesus
                switch (choice) {

                    case 1:
                        for (int i = 0; i < totMessages; i++) {
                            System.out.println("\n==================");
                            System.out.println("  Messages " + (i + 1));
                            System.out.println("==================");

                            System.out.println("\n==================================");
                            System.out.println("|  You selected: Send Quickchat  |");
                            System.out.println("==================================");

                            String recipient;
                            System.out.println("Enter your number (must start with +27 and be exactly 12 characters): ");
                            do {
                                recipient = Sc.nextLine();
                            } while (!(recipient.startsWith("+27") && recipient.length() == 12));


                            String text;
                            System.out.println("Enter your Quickchat (must be 250 characters or less): ");
                            do {
                                text = Sc.nextLine();
                            } while (text.length() > 250);

                            msg = new Message(recipient, text, i);

                            System.out.println("\n====================================");
                            System.out.println(" ---Captured Message Info---");
                            System.out.println("Message Hash: " + msg.createMessageHash());
                            System.out.println("------------------------------");
                            System.out.println("====================================\n");

                            System.out.println("===============================================");
                            System.out.println("|         ---Choose an option: ---            |");
                            System.out.println("===============================================");
                            System.out.println("|    Option 1 - Send Quickchat                |");
                            System.out.println("|    Option 2 - Disregard Quickchat           |");
                            System.out.println("|    Option 3 - Store Quickchat to send later |");
                            System.out.println("===============================================");
                            System.out.println("|    Choose which option to enter             |");
                            System.out.println("===============================================");

                            int option = Sc.nextInt();
                            Sc.nextLine();
                            String result = msg.SentMessage();

                            System.out.println(result);

                            if (option == 1) {
                                messages[i] = msg;

                                finalSent++;


                                msg.printMessage();
                            }
                        }

                    case 2:
                        System.out.println("\n======================================");
                        System.out.println("~~~DISPLAY RECENTLY SENT MESSAGES~~~");
                        System.out.println("======================================\n");
                        Manager.displaySendersAndRecipients();
                        break;
                        //System.out.println("Coming soon!");
                       // break;

                    case 3:
                        System.out.println("\n===============================");
                        System.out.println("~~~DISPLAY LONGEST MESSAGE~~~");
                        System.out.println("===============================\n");
                        Manager.displayLongestMessage();
                        break;
                        //System.out.println("Goodbye");
                       // break;

                    case 4:
                        System.out.println("\n==========================");
                        System.out.println("~~~SEARCH MESSAGE BY ID~~~");
                        System.out.println("==========================\n");
                        System.out.println("Enter Message ID to search: ");
                        String searchId = Sc.next();
                        Manager.searchByMessageId(searchId);
                        break;

                    case 5:
                        System.out.println("\n==================================");
                        System.out.println("~~~SEARCH MESSAGES BY RECIPIENT~~~");
                        System.out.println("==================================\n");
                        System.out.println("Enter Recipient Phone/Name to search: ");
                        String searchRecipient = Sc.next();
                        Manager.searchMessagesByRecipient(searchRecipient);
                        break;

                    case 6:
                        System.out.println("\n============================");
                        System.out.println("~~~DELETE MESSAGE BY HASH~~~");
                        System.out.println("============================\n");
                        System.out.println("Enter Message Hash to delete: ");
                        String deleteHash = Sc.next();
                        Manager.deleteMessageByHash(deleteHash);
                        break;

                    case 7:
                        System.out.println("\n=========================");
                        System.out.println("~~~DISPLAY FULL REPORT~~~");
                        System.out.println("=========================\n");
                        Manager.displayFullReport();
                        break;

                    case 8:
                        System.out.println("\n========================");
                        System.out.println("Exiting System. Goodbye!");
                        System.out.println("========================\n");
                        return;

                    default:
                        System.out.println("Invaild option!!!!!");
                        break;
                }
            }
            System.out.println("Total messages sent: " + finalSent + "\n");

            //msg.printMessage();

        }

    }
}