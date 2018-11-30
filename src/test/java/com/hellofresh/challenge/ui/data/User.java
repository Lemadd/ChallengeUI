package com.hellofresh.challenge.ui.data;

import java.util.Date;
import java.util.Locale;

import com.github.javafaker.Faker;

public class User {
    Faker faker = new Faker(new Locale("en-US"));

	public String firstName;
	public String lastName;
	public String email;
	public Date birthDate;
	public String password;
	public String zipCode;
	public String city;
	public String address;
	public String phoneNumber;
	public String company;
	public String state;

	public User() {
		this.firstName = faker.name().firstName();
		this.lastName = faker.name().lastName();
		this.email = faker.internet().emailAddress();
		this.birthDate = faker.date().birthday();;
		this.password = faker.internet().password();
		this.zipCode = faker.address().zipCode().substring(0, 5);
		this.city = faker.address().city();
		this.address = faker.address().streetAddress();
		this.phoneNumber = faker.phoneNumber().cellPhone();
		this.company = faker.company().name();
		this.state = faker.address().state();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
}
