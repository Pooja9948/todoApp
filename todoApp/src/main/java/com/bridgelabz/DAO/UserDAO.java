package com.bridgelabz.DAO;

import com.bridgelabz.model.UserDetails;

public interface UserDAO {
	public void registration(UserDetails userDetails);
	public UserDetails login(UserDetails userDetails);
	public boolean emailValidation(String email);
}
