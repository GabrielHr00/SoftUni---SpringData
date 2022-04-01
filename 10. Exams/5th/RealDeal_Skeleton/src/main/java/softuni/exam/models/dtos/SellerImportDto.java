package softuni.exam.models.dtos;

import softuni.exam.models.entities.Rating;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "seller")
public class SellerImportDto {
    @Size(min = 2, max = 20)
    @XmlElement(name = "first-name")
    private String firstName;

    @Size(min = 2, max = 20)
    @XmlElement(name = "last-name")
    private String lastName;

    @Email
    @XmlElement
    private String email;

    @NotNull
    @XmlElement
    private Rating rating;

    @NotNull
    @XmlElement
    private String town;

    public SellerImportDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Rating getRating() {
        return rating;
    }

    public String getTown() {
        return town;
    }
}
