/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
import java.io.Serializable;

/**
 *
 * @author Taha Al-khaffaf
 */
public class Students implements Serializable {
    
    private int studentId;
    private String firstNameEn;
    private String lastNameEn;
    private String firstNameAr;
    private String lastNameAr;

    public int getStudentId() {
        return studentId;
    }

    public String getFirstNameEn() {
        return firstNameEn;
    }

    public String getLastNameEn() {
        return lastNameEn;
    }

    public String getFirstNameAr() {
        return firstNameAr;
    }

    public String getLastNameAr() {
        return lastNameAr;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setFirstNameEn(String firstNameEn) {
        this.firstNameEn = firstNameEn;
    }

    public void setLastNameEn(String lastNameEn) {
        this.lastNameEn = lastNameEn;
    }

    public void setFirstNameAr(String firstNameAr) {
        this.firstNameAr = firstNameAr;
    }

    public void setLastNameAr(String lastNameAr) {
        this.lastNameAr = lastNameAr;
    }



    
}
