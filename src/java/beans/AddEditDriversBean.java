/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;
import daos.DriverDao;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.DriverInfo;
/**
 *
 * @author MOH
 */
@Named(value = "addEditDriversBean")
@ViewScoped
public class AddEditDriversBean implements Serializable{

    private final DriverDao driverDao = new DriverDao();

    private int driverID;
    private String nameEn;
    private String nameAr;

        @Inject
    private SessionBean sessionBean1;
    
    public AddEditDriversBean() {        
    }
        
    @PostConstruct
    public void init(){                
        try {
            driverID = sessionBean1.getSelectedItemId();
         
            
            if(driverID > 0){
                DriverInfo driverInfo = driverDao.getDriverInfo(driverID);
           
                nameEn = driverInfo.getNameEn();
                nameAr = driverInfo.getNameAr();
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditBusesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }
    
    
    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    
        public void saveDriver() {
        try {
        
            DriverInfo driverInfo = new DriverInfo();
            driverInfo.setDriverID(driverID);
            driverInfo.setNameEn(nameEn);
            driverInfo.setNameAr(nameAr);
            if (sessionBean1.getSelectedItemId() > 0) {
                driverDao.updateDriver(driverInfo);
            } else {
                driverDao.insertDriver(driverInfo);
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditDriversBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean1.navigate("manage_drivers");
    }
    
}
