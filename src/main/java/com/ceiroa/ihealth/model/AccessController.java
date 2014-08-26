/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ceiroa.ihealth.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiroa.ihealth.model.daos.AeSimpleMD5;
import com.ceiroa.ihealth.model.daos.UserDAO;
import com.ceiroa.ihealth.model.enums.Status;

@Service
public class AccessController {

	@Autowired
	private UserDAO userDAO;
	
    public User findUser(String username, String password) throws Exception {
        //Find user in database
        User user = userDAO.findUserByUsername(username);
        String encPassword = AeSimpleMD5.MD5(password);
    
        if(user != null && user.getUsername().equals(username)
                && user.getPassword().equals(encPassword)
                && user.getStatus().equals(Status.active)) {
            return user;
        } else {    //If user is not found return null
            return null;
        }
    }
}
