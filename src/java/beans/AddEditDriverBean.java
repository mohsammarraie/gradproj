/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import daos.DriversDao;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.Driver;
import org.primefaces.context.RequestContext;

/**
 *
 * @author MOH
 */
@Named(value = "addEditDriverBean")
@ViewScoped
public class AddEditDriverBean implements Serializable {

    private final DriversDao driverDao = new DriversDao();

    private int driverId;
    private String firstNameEn;
    private String firstNameAr;
    private String lastNameEn;
    private String lastNameAr;
    private String phoneNumber;
    private String nationalId;
    private String nationalityEn;
    private Date dateOfBirth;
    private String genderEn;
    private String genderAr;
    private String nationalityAr;
    
    String error_message_header = "";
    String error_message_content = "";
    
    @Inject
    private SessionBean sessionBean;

    public AddEditDriverBean() {
    }

    @PostConstruct
    public void init() {
        try {
            driverId = sessionBean.getSelectedDriverId();

            if (driverId > 0) {
                Driver driversArray = driverDao.getDrivers(driverId);

                firstNameEn = driversArray.getFirstNameEn();
                firstNameAr = driversArray.getFirstNameAr();
                lastNameEn = driversArray.getLastNameEn();
                lastNameAr = driversArray.getLastNameAr();
                phoneNumber = driversArray.getPhoneNumber();
                nationalId = driversArray.getNationalId();
                dateOfBirth = driversArray.getDateOfBirth();
                nationalityEn= driversArray.getNationalityEn();
                nationalityAr=driversArray.getNationalityAr();
                genderEn=driversArray.getGenderEn();
                genderAr=driversArray.getGenderAr();
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditDriverBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getFirstNameEn() {
        return firstNameEn;
    }

    public void setFirstNameEn(String firstNameEn) {
        this.firstNameEn = firstNameEn;
    }

    public String getFirstNameAr() {
        return firstNameAr;
    }

    public void setFirstNameAr(String firstNameAr) {
        this.firstNameAr = firstNameAr;
    }

    public String getLastNameEn() {
        return lastNameEn;
    }

    public void setLastNameEn(String lastNameEn) {
        this.lastNameEn = lastNameEn;
    }

    public String getLastNameAr() {
        return lastNameAr;
    }

    public void setLastNameAr(String lastNameAr) {
        this.lastNameAr = lastNameAr;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNationalityEn() {
        return nationalityEn;
    }

    public void setNationalityEn(String nationalityEn) {
        this.nationalityEn = nationalityEn;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGenderEn() {
        return genderEn;
    }

    public void setGenderEn(String genderEn) {
        this.genderEn = genderEn;
    }

    public String getGenderAr() {
        return genderAr;
    }

    public void setGenderAr(String genderAr) {
        this.genderAr = genderAr;
    }

    public String getNationalityAr() {
        return nationalityAr;
    }

    public void setNationalityAr(String nationalityAr) {
        this.nationalityAr = nationalityAr;
    }
    
    public void saveDriver() {
        try {

            Driver drivers = new Driver();
            drivers.setDriverId(driverId);
            drivers.setFirstNameEn(firstNameEn);
            drivers.setFirstNameAr(firstNameAr);
            drivers.setLastNameEn(lastNameEn);
            drivers.setLastNameAr(lastNameAr);
            drivers.setPhoneNumber(phoneNumber);
            drivers.setNationalId(nationalId);
            drivers.setDateOfBirth(dateOfBirth);
            drivers.setGenderAr(genderAr);
            drivers.setGenderEn(genderEn);
            drivers.setNationalityAr(nationalityAr);
            drivers.setNationalityEn(nationalityEn);
            
            if (sessionBean.getSelectedDriverId() > 0) {
                driverDao.updateDriver(drivers);
            } else {
                driverDao.insertDriver(drivers);
            }
        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, error_message_header, error_message_content));
            Logger.getLogger(AddEditDriverBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean.navigateManageDrivers();
    }

}
