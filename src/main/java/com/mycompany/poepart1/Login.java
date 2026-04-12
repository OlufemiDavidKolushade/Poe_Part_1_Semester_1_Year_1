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
    //Calls to public method by making it a private class
    private String userName;
    private String passWord;
    private String firstName;
    private String lastName;
    private String cellNumber;
    
    //Inputs user details after the rgistration process
    public void loginDetails(String userName, String passWord, String firstName, String lastName, String cellNumber ){
        
        //Storage container for the username
        this.userName = userName; 
        
        //Storage container for the password
        this.passWord = passWord; 
        
        //Storage container for the firstname
        this.firstName = firstName; 
        
        //Storage Container for thelastname
        this.lastName = lastName; 
        
        //Storage container forCellnumber
        this.cellNumber = cellNumber;
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
    
    
    //checks if the conditions are being met
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
    
    public boolean LoginUser(String enteredUsername, String enteredPassword){
        return enteredUsername.equals(userName) && enteredPassword.equals(passWord);
    }
    
    public String returnLoginStatus(boolean loggedIn){
        if (loggedIn){
            return "welcome "+ this.firstName + ", "+ this.lastName + " It is great to see you again";
        } else {
            return "Username or password incorrect, please try agian";
        }
    
    }
}
    

