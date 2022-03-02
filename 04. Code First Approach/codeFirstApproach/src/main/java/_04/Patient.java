package _04;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.util.Set;

@Entity(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="first_name", length = 20)
    private String firstName;

    @Column(name="last_name", length = 20)
    private String lastName;

    private String address;

    @Column(nullable = false, unique = false)
    private String email;

    @Column(name="date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    private File image;

    @Column(name="medical_insurance")
    private boolean medicalInsurance;

    @OneToMany
    private Set<Visitation> visitations;

    @OneToMany
    private Set<Visitation> diagnoses;

    @OneToMany
    private Set<Visitation> medications;

    public Patient() {}

    public Patient(String firstName, String lastName, String address, String email, LocalDate dateOfBirth, File image, boolean medicalInsurance, Set<Visitation> visitations, Set<Visitation> diagnoses, Set<Visitation> medications) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.image = image;
        this.medicalInsurance = medicalInsurance;
        this.visitations = visitations;
        this.diagnoses = diagnoses;
        this.medications = medications;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public boolean isMedicalInsurance() {
        return medicalInsurance;
    }

    public void setMedicalInsurance(boolean medicalInsurance) {
        this.medicalInsurance = medicalInsurance;
    }

    public Set<Visitation> getVisitations() {
        return visitations;
    }

    public void setVisitations(Set<Visitation> visitations) {
        this.visitations = visitations;
    }

    public Set<Visitation> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(Set<Visitation> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public Set<Visitation> getMedications() {
        return medications;
    }

    public void setMedications(Set<Visitation> medications) {
        this.medications = medications;
    }
}
