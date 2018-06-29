package com.project.Rentingaccommodation.model;

import javax.persistence.*;

@Entity
@Table(name="administrator")
public class Admin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_id", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@Column(name="name", columnDefinition="VARCHAR(50)", nullable=false)
	private String name;
	
	@Column(name="surname", columnDefinition="VARCHAR(50)", nullable=false)
	private String surname;
	
	@Column(name="password", columnDefinition="VARCHAR(100)", nullable=false)
	private String password;
	
	@Column(name="email", columnDefinition="VARCHAR(50)", unique=true, nullable=false)
	private String email;
	
	@Column(name="question", columnDefinition="VARCHAR(100)", nullable=false)
	private String question;
	
	@Column(name="answer", columnDefinition="VARCHAR(100)", nullable=false)
	private String answer;
	
	@Column(name="block_time", columnDefinition="VARCHAR(50)", nullable=true)
	private String block_time;
	
	@Column(name="max_tries", columnDefinition="INT(11) DEFAULT 0", nullable=false)
	private int max_tries;
	
	@Enumerated(EnumType.STRING)
	private AdminStatus status;
	
	public Admin() {
		
	}

	public Admin(String name, String surname, String password, String email) {
		super();
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getBlock_time() {
		return block_time;
	}

	public void setBlock_time(String block_time) {
		this.block_time = block_time;
	}

	public int getMax_tries() {
		return max_tries;
	}

	public void setMax_tries(int max_tries) {
		this.max_tries = max_tries;
	}

	public AdminStatus getStatus() {
		return status;
	}

	public void setStatus(AdminStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Admin [id=" + id + ", name=" + name + ", surname=" + surname + ", password=" + password + ", email="
				+ email + "]";
	}
}
