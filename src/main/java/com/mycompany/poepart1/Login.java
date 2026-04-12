/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poepart1;

import java.util.regex.Pattern;

/**
 *
 * @author femik
 */
public class Login {
    
    //Calls to public method by making it a private class and stores the data 
    private String userName;
    private String firstName;
    private String lastName;
    private String passWord;
    private String cellNumber;
    
    //it acts like a setup tool, I declared  5 parameters and assigned them to the private files using this as the keyword
    public void loginDetails(String userName, String passWord, String firstName, String lastName, String cellNumber ){

        this.userName = userName; 

        this.passWord = passWord; 

        this.firstName = firstName; 
        
        this.cellNumber = cellNumber;
        
        this.lastName = lastName; 
    }
    
       //checks if username contains: "_" and <=5
    public boolean checkUserName(String userName){        
        return userName.contains("_") && userName.length() <=5;
        }
    
    
    //Checks if number contains international code "+27" and is not more than 9 characters long, I WAS ASSISTED BY AI!!!
    public boolean checkCellNumber(String cellNumber){
        String regex = "^\\+27[0-9]{9}$";
        
        return Pattern.compile(regex).matcher(cellNumber).matches();
    } 
    
    
    //checks if password is no more than 8 characters long and contains a number as well as a special character
        public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) return false;

        boolean hasUppercase = false;
        boolean hasDigitnumber = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUppercase = true;
            else if (Character.isDigit(c)) hasDigitnumber = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return hasUppercase && hasDigitnumber && hasSpecial;
    }
        
        //registerUser checks if any condition fails and if it does it prints an error message otherwise if conditions are met it prints a confrimation message
    public String registerUser(){
        if(!checkUserName(userName)){
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters";
        } if (!checkPasswordComplexity(passWord)){
            return "Password is not correctly formatted; please ensure that your password contains at least eight character, a capital letter, a number";
        }
        if(!checkCellNumber(cellNumber)){
            return "Phone number is not correctly formatted; please ensure it starts with +27 and contains 12 characters in total";
        }
        
        return "Username was successfully captured. \nPassword successfully captured. \nCell number successfully captured";
    }
     
    //This compares if the username and password entered by the user is the same as the one that has been stored in the class and returns true if they match
    public boolean LoginUser(String enteredUsername, String enteredPassword){
        return enteredUsername.equals(userName) && enteredPassword.equals(passWord);
    }
    
    //This prints out the final message for the user, if the users details match then it prints the welcome message else if the users details don't match it prints the error message
    public String returnLoginStatus(boolean loggedIn){
        if (loggedIn){
            return "welcome "+ this.firstName + ", "+ this.lastName + " It is great to see you again";
        } else {
            return "Username or password incorrect, please try agian";
        }
    
    }
}
    

