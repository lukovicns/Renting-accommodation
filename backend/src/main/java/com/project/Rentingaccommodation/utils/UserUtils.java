package com.project.Rentingaccommodation.utils;

import com.project.Rentingaccommodation.model.Admin;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.service.AdminService;
import com.project.Rentingaccommodation.service.UserService;

public class UserUtils {
	
    public static boolean userExists(String email, AdminService adminService, UserService userService) {
		User foundUser = userService.findByEmail(email);
		Admin foundAdmin = adminService.findByEmail(email);
		if (foundUser != null) {
			return true;
		}
		if (foundAdmin != null) {
			return true;
		}
		return false;
    }
}
