/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function validatePassChange() {
    var oldpass = document.getElementById("passchange:oldpass").value;
    var newpass = document.getElementById("passchange:newpass").value;
    var newpassconfirm = document.getElementById("passchange:newpassconfirm").value;
    
    // Update to check old password against user password
    if(oldpass === "") {
        alert("Old Password cannot be left blank");
        return false;
    } else if(newpass === "") {
        alert("New Password cannot be left blank");
        return false;
    } else if(badPassword(newpass)) {
        alert("New password must be at least 8 characters and not contain whitespace");
        return false;
    } else if(newpass !== newpassconfirm) {
        alert("New password confirmation does not match");
        return false;
    } else if(newpass === oldpass) {
        alert("New password cannot be same as old password");
        return false;
    } else {
        //alert("Password successfully changed!");
        return true;  // Form will be submitted if validation passes
    }
}

function validateLogin() {
    var username = document.getElementById("login:username").value;
    var pass = document.getElementById("login:password").value;
    
    //TODO check email and password against database
    if(username === "") {
        alert("Please enter your username");
        return false;
    } else if(pass === "") {
        alert("Please enter your password");
        return false;
    } else {
        //alert("Welcome!");
        return true;
    }
}

function validateRecipe() {
    var name = document.getElementById("newrecipe:name").value;
    var servings = document.getElementById("newrecipe:servings").value;
    var preptime = document.getElementById("newrecipe:preptime").value;
    
    if(name === "") {
        alert("Please provide a name for the recipe");
    } else if(isNaN(servings)) {
        alert("Please enter the servings yield as a whole number");
    } else if(isNaN(preptime)) { //TODO Still accepts decimals
        alert("Please enter the prep time as a whole number of minutes");
    } else {
        alert("New recipe \"" + name + "\" has been submitted!");
        return true;
    }
    return false;
}

function validateNewUser() {
    var email = document.getElementById("newuser:email").value;
    var emailconfirm = document.getElementById("newuser:emailconfirm").value;
    var username = document.getElementById("newuser:username").value;
    var password = document.getElementById("newuser:password").value;
    var passwordconfirm = document.getElementById("newuser:passwordconfirm").value;
    
    if(email === "") {
        alert("Please enter an e-mail address");
    } else if(!isEmail(email)) {
        alert("Please enter a valid e-mail address");
    } else if(email !== emailconfirm) {
        alert("Email confirmation does not match");
    } else if(username.length < 4) {
        alert("Please enter a username of four or more characters");
        //TODO check if username is already taken
    } else if(badPassword(password)) {
        alert("Password must be at least 8 characters and not contain whitespace");
    } else if(password !== passwordconfirm) {
        alert("Password confirmation does not match");
    } else {
        //alert("New user successfully created. Welcome, " + username + "!");
        return true;
    }
    return false;
}

function badPassword(pass) {
    if(pass === null || pass === "") return true;
    if(typeof pass !== "string") return true;
    if(pass.length < 8) return true;
    if(/\s/g.test(pass)) return true;
    return false;
}

function isEmail(email) {
    var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,})$/; //regex from stack overflow
    
    if(reg.test(email)) {
        return true;
    } else {
        return false;
    }
}