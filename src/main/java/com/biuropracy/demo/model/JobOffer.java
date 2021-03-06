package com.biuropracy.demo.model;

import org.hibernate.validator.constraints.Range;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "job_offer")
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idjob_offer")
    private Integer idJobOffer;

    @Column(name = "title", nullable = false)
    @NotEmpty(message = "Wpisz tytuł ogłoszenia")
    private String title;

    @Column(name = "location", nullable = false)
    @NotEmpty(message = "Wpisz lokalizację")
    private String location;

    @Column(name = "description" , nullable = false)
    @NotEmpty(message = "Dodaj opis")
    private String description;

    @Column(name = "contact", nullable = false)
    @NotEmpty(message = "Podaj dane kontaktowe")
    private String contact;

    @Column(name = "category", nullable = false)
    @NotEmpty(message = "Wybierz kategorię")
    private String category;

    @Column(name = "contract_type", nullable = false)
    @NotEmpty(message = "Wybierz rodzaj umowy")
    private String contractType;

    @Column(name = "working_time", nullable = false)
    @NotEmpty(message = "Wybierz wymiar pracy")
    private String workingTime;

    @Column(name = "monthly_pay")
    @Range(min = 1, max = 9999999)
    private Integer monthlyPay;

    @Column(name = "category_salary", nullable = false)
    @NotEmpty(message = "Wybierz rodzaj wynagrodzenia")
    private String categorySalary;

    @Column(name = "position_level", nullable = false)
    @NotEmpty(message = "Wpisz poziom stanowiska")
    private String positionLevel;

    @ManyToOne
    @JoinColumn(name = "id_employer")
    private Employer employer;

    @OneToMany(mappedBy = "jobOffer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProfileProposition> jobOffers;

    public JobOffer(@NotEmpty(message = "Wpisz tytuł ogłoszenia") String title, @NotEmpty(message = "Wpisz lokalizację") String location, @NotEmpty(message = "Dodaj opis") String description, @NotEmpty(message = "Podaj dane kontaktowe") String contact, @NotEmpty(message = "Wybierz kategorię") String category, @NotEmpty(message = "Wybierz rodzaj umowy") String contractType, @NotEmpty(message = "Wybierz wymiar pracy") String workingTime, @Range(min = 1, max = 9999999) Integer monthlyPay, @NotEmpty(message = "Wybierz rodzaj wynagrodzenia") String categorySalary, @NotEmpty(message = "Wpisz poziom stanowiska") String positionLevel, Employer employer, List<ProfileProposition> jobOffers) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.contact = contact;
        this.category = category;
        this.contractType = contractType;
        this.workingTime = workingTime;
        this.monthlyPay = monthlyPay;
        this.categorySalary = categorySalary;
        this.positionLevel = positionLevel;
        this.employer = employer;
        this.jobOffers = jobOffers;
    }

    public JobOffer() {
    }

    public Integer getIdJobOffer() {
        return idJobOffer;
    }

    public void setIdJobOffer(Integer idJobOffer) {
        this.idJobOffer = idJobOffer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(String workingTime) {
        this.workingTime = workingTime;
    }

    public Integer getMonthlyPay() {
        return monthlyPay;
    }

    public void setMonthlyPay(Integer monthlyPay) {
        this.monthlyPay = monthlyPay;
    }

    public String getCategorySalary() {
        return categorySalary;
    }

    public void setCategorySalary(String categorySalary) {
        this.categorySalary = categorySalary;
    }

    public String getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public List<ProfileProposition> getJobOffers() {
        return jobOffers;
    }

    public void setJobOffers(List<ProfileProposition> jobOffers) {
        this.jobOffers = jobOffers;
    }
}
