/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;
import daos.DriversDao;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.Drivers;
/**
 *
 * @author MOH
 */
@Named(value = "addEditDriverBean")
@ViewScoped
public class AddEditDriverBean implements Serializable{

    private final DriversDao driverDao = new DriversDao();

    private int driverId;
    private String firstNameEn;
    private String firstNameAr;
    private String lastNameEn;
    private String lastNameAr;
    private String phoneNumber;
    private String nationalId;

        @Inject
    private SessionBean sessionBean;
    
    public AddEditDriverBean() {        
    }
        
    @PostConstruct
    public void init(){                
        try {
            driverId = sessionBean.getSelectedDriverId();
         
            
            if(driverId > 0){
                Drivers driversArray = driverDao.getDrivers(driverId);
           
                firstNameEn = driversArray.getFirstNameEn();
                firstNameAr = driversArray.getFirstNameAr();
                lastNameEn =  driversArray.getLastNameEn();
                lastNameAr =  driversArray.getLastNameAr();
                phoneNumber = driversArray.getPhoneNumber();
                nationalId = driversArray.getNationalId();
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

        public void saveDriver() {
        try {
        
            Drivers drivers = new Drivers();
            drivers.setDriverId(driverId);
            drivers.setFirstNameEn(firstNameEn);
            drivers.setFirstNameAr(firstNameAr);
            drivers.setLastNameEn(lastNameEn);
            drivers.setLastNameAr(lastNameAr);
            drivers.setPhoneNumber(phoneNumber);
            drivers.setNationalId(nationalId);
            
            if (sessionBean.getSelectedDriverId() > 0) {
                driverDao.updateDriver(drivers);
            } else {
                driverDao.insertDriver(drivers);
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditDriverBean.class.getName()).log(Level.SEVERE, null, ex);
        }

       sessionBean.navigateManageDrivers();
    }
    
}
