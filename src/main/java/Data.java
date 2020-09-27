import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.Period;

public class Data {
    private int id;
    private String firstName;
    private String lastName;
    private String city;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private Data(int id, String firstName, String lastName, String city, LocalDate dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.dateOfBirth = dateOfBirth;


    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "M/d/yy")
    private LocalDate dateOfBirth;

    public void setDateOfBirth(LocalDate dob) {
        if (dob.isAfter(LocalDate.now())) {
            dob = dob.minusYears(100);
        }
        this.dateOfBirth = dob;
    }

    @JsonIgnore
    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    @JsonIgnore
    public String getAgeGroup() {
        int age = getAge();
        if (age < 11) {
            return "0..10";
        }
        return (age / 10 * 10 + 1) + ".." + ((age / 10 + 1) * 10);
    }

    @Override
    public String toString() {
        return "[id = " + id + ", age=" + getAge() + ", firstName = " + firstName + ", lastName = " + lastName + ", dateOfBirth = " + dateOfBirth + ", city = " + city + "]";
    }
}
