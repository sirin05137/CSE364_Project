package group11.restservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// USER INPUT
//A model object to represent the passed JSON data
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
	private String gender = ""; // OR null for errors?
	private String age = "";
	private String occupation = "";
	private String genre = "";

	@JsonProperty("gender")
	public String getGender() { return gender; }
	@JsonProperty("gender")
	public void setGender(String value) { this.gender = value; }

	@JsonProperty("age")
	public String getAge() { return age; }
	@JsonProperty("age")
	public void setAge(String value) { this.age = value; }

	@JsonProperty("occupation")
	public String getOccupation() { return occupation; }
	@JsonProperty("occupation")
	public void setOccupation(String value) { this.occupation = value; }

	@JsonProperty("genre")
	public String getGenre() { return genre; }
	@JsonProperty("genre")
	public void setGenre(String value) { this.genre = value; }

	public String[] getJavaInput() {
		String[] javainput = new String[4];
		javainput[0] = gender;
		javainput[1] = age;
		javainput[2] = occupation;
		javainput[3] = genre;

		return javainput;
	}

	/*
	@Override
	public String toString() {
		return "[\"gender\" : \"" + gender + "]";
	}
	*/

}
