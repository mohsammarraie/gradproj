/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.DriverInfo;
import daos.DriverDao;
import javax.inject.Named;
/**
 *
 * @author MOH
 */
@Named(value = "driversInfoBean")
@ViewScoped
public class DriversInfoBean implements Serializable {
    private DriverInfo selectedDriver;
    private final DriverDao driverDao = new DriverDao();
    private ArrayList<DriverInfo> driverInfo; 
    
    @Inject 
    private SessionBean sessionBean;
     
    public DriversInfoBean(){}
     
        @PostConstruct
      public void init(){
        try {            
            driverInfo = driverDao.buildDriverInfo();            
        } catch (Exception ex) {
            Logger.getLogger(DriversInfoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DriverInfo getSelectedDriver() {
        return selectedDriver;
    }

    public void setSelectedDriver(DriverInfo selectedDriver) {
        this.selectedDriver = selectedDriver;
    }

    public ArrayList<DriverInfo> getDriverInfo() {
        return driverInfo;
    }


    
    

    public void setDriverInfo(ArrayList<DriverInfo> driverInfo) {
        this.driverInfo = driverInfo;
    }


    public void saveSelectedItemId(){
        sessionBean.setSelectedItemId(selectedDriver.getDriverID());
    }
    
    public void deleteSelectedDriver(){
        try {
            driverDao.deleteDriver(selectedDriver.getDriverID());
            sessionBean.navigate("manage_drivers");

        } catch (Exception ex) {
            sessionBean.navigate("error_page");
            Logger.getLogger(DriversInfoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
