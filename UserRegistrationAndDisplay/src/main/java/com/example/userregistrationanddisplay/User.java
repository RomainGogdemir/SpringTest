package com.example.userregistrationanddisplay;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User object representing user data.
 * 
 * This class has been overdocumented. In a bigger project context,
 * the setters and getters would not be documented and only the fields
 * that are describing non straigth forward information would have been
 * documented.
 * But this project is really small and this is just a pretext 
 * to have a bit of documentation in it.
 */
@Document
public class User {
	
	@Id
	private String id;
	
	/**
	 * Mail of the user
	 */
	@NotNull
	@Email
	@Size(min=1, max=30)
	private String mailAddress;
	
	/**
	 * Job of the user
	 * Optionnal field
	 */
	@Size(min=1, max=30)
	private String job;
	
	/**
	 * Name of the user
	 */
	@NotNull
	@Size(min=1, max=20)
	private String name;
	
	/** 
	 * Name of the country the user lives in 
	 */
	@NotNull
	@Size(min=1, max=30)
	private String country;
	
	/** 
	 * User's birthdate
	 */
	@NotNull
	private LocalDate birthdate;
	
	/**
	 * Empty constructor
	 */
	public User() {
		
	}
	
	/**
	 * User's id getter
	 * @return id
	 */
	public String getId() {
		return id;
	}
	
	/** User's mail getter
	 * @return mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/** User's mail setter
	 * @param mailAddress
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/** User's name getter
	 * @return
	 */
	public String getName() {
		return name;
	}

	/** User's name setter
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** User's country getter
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/** User's country setter
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/** User's birthDate getter
	 * @return
	 */
	public LocalDate getBirthdate() {
		return birthdate;
	}

	/** User's birthDate setter
	 * @param birthDate
	 */
	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
	
	/**
	 * User's job getter
	 * @return job
	 */
	public String getJob() {
		return job;
	}

	/**
	 * User's job setter
	 * @param job
	 */
	public void setJob(String job) {
		this.job = job;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("User [id=" + id);
		sb.append(", mailAddress=" + mailAddress);
		sb.append(", job=" + job);
		sb.append(", mailAddress=" + mailAddress);
		sb.append(", name=" + name);
		sb.append(", country="+ country);
		sb.append(", birthdate=" + birthdate + "]");
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		//Auto-generated equals method
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (birthdate == null) {
			if (other.birthdate != null)
				return false;
		} else if (!birthdate.equals(other.birthdate))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (job == null) {
			if (other.job != null)
				return false;
		} else if (!job.equals(other.job))
			return false;
		if (mailAddress == null) {
			if (other.mailAddress != null)
				return false;
		} else if (!mailAddress.equals(other.mailAddress))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * Setter of the id of the user
	 * For test purposes
	 * @param id
	 */
	public void setID(String id) {
		this.id = id;
	}

	


}
