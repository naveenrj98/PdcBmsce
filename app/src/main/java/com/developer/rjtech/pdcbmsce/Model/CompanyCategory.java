package com.developer.rjtech.pdcbmsce.Model;

public class CompanyCategory {



    private String name;
    private String image;
    private String duration,jobtype,stipend,worktime;
    private String pdfname,pdfurl;
    private String jdname,jdurl;
    private String cgpa,eligibledepartment,jobdescrition,offers,role,visitdate,joblocation;
    private String linkedin, glassdoor, website;




    public CompanyCategory() {

    }

    public String getJoblocation() {
        return joblocation;
    }

    public void setJoblocation(String joblocation) {
        this.joblocation = joblocation;
    }

    @Override
    public String toString() {
        return "CompanyCategory{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", duration='" + duration + '\'' +
                ", jobtype='" + jobtype + '\'' +
                ", stipend='" + stipend + '\'' +
                ", worktime='" + worktime + '\'' +
                ", pdfname='" + pdfname + '\'' +
                ", pdfurl='" + pdfurl + '\'' +
                ", jdname='" + jdname + '\'' +
                ", jdurl='" + jdurl + '\'' +
                ", cgpa='" + cgpa + '\'' +
                ", eligibledepartment='" + eligibledepartment + '\'' +
                ", jobdescrition='" + jobdescrition + '\'' +
                ", offers='" + offers + '\'' +
                ", role='" + role + '\'' +
                ", visitdate='" + visitdate + '\'' +
                ", joblocation='" + joblocation + '\'' +
                ", linkedin='" + linkedin + '\'' +
                ", glassdoor='" + glassdoor + '\'' +
                ", website='" + website + '\'' +
                '}';
    }

    public CompanyCategory(String name, String image, String duration, String jobtype, String stipend, String worktime, String pdfname, String pdfurl, String jdname, String jdurl, String cgpa, String eligibledepartment, String jobdescrition, String offers, String role, String visitdate, String linkedin, String glassdoor, String website) {
        this.name = name;
        this.image = image;
        this.duration = duration;
        this.jobtype = jobtype;
        this.stipend = stipend;
        this.worktime = worktime;
        this.pdfname = pdfname;
        this.pdfurl = pdfurl;
        this.jdname = jdname;
        this.jdurl = jdurl;
        this.cgpa = cgpa;
        this.eligibledepartment = eligibledepartment;
        this.jobdescrition = jobdescrition;
        this.offers = offers;
        this.role = role;
        this.visitdate = visitdate;
        this.linkedin = linkedin;
        this.glassdoor = glassdoor;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getJobtype() {
        return jobtype;
    }

    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }

    public String getStipend() {
        return stipend;
    }

    public void setStipend(String stipend) {
        this.stipend = stipend;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    public String getPdfname() {
        return pdfname;
    }

    public void setPdfname(String pdfname) {
        this.pdfname = pdfname;
    }

    public String getPdfurl() {
        return pdfurl;
    }

    public void setPdfurl(String pdfurl) {
        this.pdfurl = pdfurl;
    }

    public String getJdname() {
        return jdname;
    }

    public void setJdname(String jdname) {
        this.jdname = jdname;
    }

    public String getJdurl() {
        return jdurl;
    }

    public void setJdurl(String jdurl) {
        this.jdurl = jdurl;
    }

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }

    public String getEligibledepartment() {
        return eligibledepartment;
    }

    public void setEligibledepartment(String eligibledepartment) {
        this.eligibledepartment = eligibledepartment;
    }

    public String getJobdescrition() {
        return jobdescrition;
    }

    public void setJobdescrition(String jobdescrition) {
        this.jobdescrition = jobdescrition;
    }

    public String getOffers() {
        return offers;
    }

    public void setOffers(String offers) {
        this.offers = offers;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getVisitdate() {
        return visitdate;
    }

    public void setVisitdate(String visitdate) {
        this.visitdate = visitdate;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getGlassdoor() {
        return glassdoor;
    }

    public void setGlassdoor(String glassdoor) {
        this.glassdoor = glassdoor;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

}
