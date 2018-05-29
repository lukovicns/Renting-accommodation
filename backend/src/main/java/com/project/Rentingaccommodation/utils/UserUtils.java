package com.project.Rentingaccommodation.utils;

import org.springframework.beans.factory.annotation.Autowired;
import com.project.Rentingaccommodation.model.Admin;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.service.AdminService;
import com.project.Rentingaccommodation.service.UserService;

public class UserUtils {
	
	@Autowired
	private static UserService userService;
	
	@Autowired
	private static AdminService adminService;
	
    public static boolean userExists(Object obj) {
		User foundUser = userService.findByEmail(((User) obj).getEmail());
		Admin foundAdmin = adminService.findByEmail(((Admin) obj).getEmail());
		
		if (foundUser != null) {
			return true;
		}
		if (foundAdmin != null) {
			return true;
		}
		return false;
    }
}
