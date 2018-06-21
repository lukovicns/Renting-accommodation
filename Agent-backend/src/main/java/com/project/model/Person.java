package com.project.model;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = -682922194147519312L;
	
	private String firstName;
	private String lastName;
	
	public Person() {
		
	}
	
	/**
	 * @param firstName
	 * @param lastName
	 */
	public Person(String firstName, String lastName) {
		super();
		// TODO Auto-generated constructor stub
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * @return Returns the firstName.
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return Returns the lastName.
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName The lastName to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
