/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poepart1;

import java.util.Scanner;

/**
 *
 * @author femik
 */
public class PoePart1 {
//Main method
    public static void main(String[] args) {
        
        //From Scanner import
        Scanner Sc = new Scanner(System.in);
        Login auth = new Login();
        
        //Registration process where we input the username, password, cellnumber
        System.out.println("Registration process");//Prints out registration process
        
        
        //Allows enter of the username using the import Scanner function
        System.out.println("Enter Firstname");
        String Firstname = Sc.nextLine();
        
        //Allows enter for the Lastname using the import scanner function
        System.out.println("Enter Lastname");
        String Lastname = Sc.nextLine();
        
        //Allows for user to enter username
        System.out.println("Enter Username: ");
        String Username = Sc.nextLine();
        
        //Allows for user to enter Cellnumber
        System.out.println("Enter Cellnumber(+27): ");
        String Cellnumber = Sc.nextLine();
        
        //Allows for user to enter password
        System.out.println("Enter Password: ");
        String Password = Sc.nextLine();
        
        //Store the data in the object
        auth.loginDetails(Username,Password, Firstname, Lastname, Cellnumber);
        
        //Validating the registration
        String regStatus = auth.registerUser();
        System.out.println("\n"+regStatus);
        
        //If registration worked then we proceed to login
        if(regStatus.toLowerCase().contains("successfully captured")){
            System.out.println("\n---Login---");
            
            System.out.println("Enter Username: ");
            String loginUser = Sc.nextLine();
            
            System.out.println("Enter Password: ");
            String loginPass = Sc.nextLine();
            
            boolean loginResult = auth.LoginUser(loginUser, loginPass);
            System.out.println(auth.returnLoginStatus(loginResult));
        }
       
    }
}