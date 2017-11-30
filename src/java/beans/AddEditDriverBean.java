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
@Named(value = "addEditDriversBean")
@ViewScoped
public class AddEditDriverBean implements Serializable{

    private final DriversDao driverDao = new DriversDao();

    private int driverID;
    private String firstNameEn;
    private String firstNameAr;
    private String lastNameEn;
    private String lastNameAr;
    private String phoneNumber;

        @Inject
    private SessionBean sessionBean;
    
    public AddEditDriverBean() {        
    }
        
    @PostConstruct
    public void init(){                
        try {
            driverID = sessionBean.getSelectedDriverID();
         
            
            if(driverID > 0){
                Drivers driverInfo = driverDao.getDriverInfo(driverID);
           
                firstNameEn = driverInfo.getFirstNameEn();
                firstNameAr = driverInfo.getFirstNameAr();
                lastNameEn =  driverInfo.getLastNameEn();
                lastNameAr =  driverInfo.getLastNameAr();
                phoneNumber = driverInfo.getPhoneNumber();
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditDriverBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
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
        
            Drivers driverInfo = new Drivers();
            driverInfo.setDriverID(driverID);
            driverInfo.setFirstNameEn(firstNameEn);
            driverInfo.setFirstNameAr(firstNameAr);
            driverInfo.setLastNameEn(lastNameEn);
            driverInfo.setLastNameAr(lastNameAr);
            driverInfo.setPhoneNumber(phoneNumber);
            
            
            if (sessionBean.getSelectedDriverID() > 0) {
                driverDao.updateDriver(driverInfo);
            } else {
                driverDao.insertDriver(driverInfo);
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditDriverBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean.navigate("manage_drivers");
    }
    
}
