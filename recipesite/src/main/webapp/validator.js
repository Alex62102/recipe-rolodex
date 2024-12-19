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
        alert("Password successfully changed!");
        return true;  // Form will be submitted if validation passes
    }
}

function validateLogin() {
    
}

function validateRecipe() {
    
}

function validateNewUser() {
    
}

function badPassword(pass) {
    if(pass === null || pass === "") return true;
    if(typeof pass !== "string") return true;
    if(pass.length < 8) return true;
    if(/\s/g.test(pass)) return true;
    return false;
}

