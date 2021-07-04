package com.marketclient.logic;

// Employee POJO class
public class Employee {

	private String id;
	private String firstName;
	private String lastName;
	private String workId;
	private String telephone;
	private String account;
	private String position;

	public Employee() {

		setId("");
		setFirstName("");
		setLastName("");
		setWorkId("");
		setTelephone("");
		setAccount("");
		setPosition("");

	}// ctor

	public String getId() {

		return id;

	}// getId

	public void setId(String id) {

		if (id != null) {

			this.id = id;
		}

	}// setId

	public String getFirstName() {

		return firstName;

	}// getFirstName

	public void setFirstName(String firstName) {

		if (firstName != null) {

			this.firstName = firstName;
		}

	}// setFirstName

	public String getLastName() {

		return lastName;

	}// getLastName

	public void setLastName(String lastName) {

		if (lastName != null) {

			this.lastName = lastName;
		}

	}// setLastName

	public String getWorkId() {

		return workId;

	}// getWorkId

	public void setWorkId(String workId) {

		if (workId != null) {

			this.workId = workId;
		}

	}// setWorkId

	public String getTelephone() {

		return telephone;

	}// getTelephone

	public void setTelephone(String telephone) {

		if (telephone != null) {

			this.telephone = telephone;
		}

	}// setTelephone

	public String getAccount() {

		return account;

	}// getAccount

	public void setAccount(String account) {

		if (account != null) {

			this.account = account;
		}

	}// setAccount

	public String getPosition() {

		return position;

	}// getPosition

	public void setPosition(String position) {

		if (position != null) {
			
			this.position = position;
		}

	}// setPosition

}// class
