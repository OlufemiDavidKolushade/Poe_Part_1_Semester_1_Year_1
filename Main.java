import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner Sc = new Scanner(System.in);
        Login auth = new Login();

        System.out.println("Registration process");

        System.out.println("Enter Firstname");
        String Firstname = Sc.nextLine();

        System.out.println("Enter Lastname");
        String Lastname = Sc.nextLine();

        //System.out.println("Enter Username: ");
        //String Username = Sc.nextLine();

        String Username;
        do{
            System.out.println("Enter username");
            Username = Sc.nextLine();
        }while(!(Username.contains("_") && Username.length() <=5));

        //System.out.println("Enter CellNumber(+27): ");
        //String CellNumber = Sc.nextLine();
        String CellNumber;

        do{
            System.out.println("Enter CellNumber(+27): ");
            CellNumber = Sc.nextLine();
        }
        while(!(CellNumber.startsWith("+27") && CellNumber.length() ==12));


        //System.out.println("Enter Password: ");
        //String Password = Sc.nextLine();


        String Password;
        do{
            System.out.println("Enter Password (8 characters and an uppercase and a number): ");
            Password = Sc.nextLine();
        }
        while(auth.checkPasswordComplexity(""));



        auth.loginDetails(Username, Firstname, Lastname, Password, CellNumber);

        String regStatus = auth.registerUser();
        System.out.println("\n" + regStatus);

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
                System.out.println("========================");
                System.out.println("\nWelcome to Quick Chat!");
                System.out.println("========================");
            }
            System.out.println("\n How many messages do you want to print? ");
            int totMessages = Sc.nextInt();

            Message[] messages = new Message[totMessages];

            int finalSent = 0;
            int choice = 0;

            while (choice != 3) {
                System.out.println("\nMenu");
                System.out.println("1) Send messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");
                System.out.println("Choose which option to enter: ");
                choice = Sc.nextInt();

                switch (choice) {

                    case 1:
                        for (int i = 0; i < totMessages; i++) {
                            System.out.println("==================");
                            System.out.println("Messages " + (i + 1));
                            System.out.println("==================");

                            System.out.println("\n==================================");
                            System.out.println("|  You selected: Send Quickchat  |");
                            System.out.println("==================================");

                            String recipient;
                            System.out.println("Enter your number (must start with +27 and be exactly 12 characters): ");
                            do {
                                recipient = Sc.nextLine();
                            } while (!(recipient.startsWith("+27") && recipient.length() == 12));


                            String  text;
                            System.out.println("Enter your Quickchat (must be 250 characters or less): ");
                            do{
                                text = Sc.nextLine();
                            }while (text.length() > 250);

                            Message msg = new Message(recipient, text, i);

                            System.out.println("====================================");
                            System.out.println("\n ---Captured Message Info---");
                            System.out.println("Message Hash: " + msg.createMessageHash());
                            System.out.println("------------------------------");
                            System.out.println("====================================");


                            System.out.println("---Choose an option: ---");
                            System.out.println("Option 1 - Send Quickchat");
                            System.out.println("Option 2 - Disregard Quickchat");
                            System.out.println("Option 3 - Store Quickchat to send later");

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
                            System.out.println("Coming soon!");
                            break;

                        case 3:

                            System.out.println("Goodbye");
                            break;

                        default:
                            System.out.println("Invaild option");
                            break;
                    }
                }
                System.out.println("Total messages sent: " + finalSent +"\n");

            }

        }
    }
