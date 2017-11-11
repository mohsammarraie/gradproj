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
import models.DriversInfo;
import daos.DriversDao;
import javax.inject.Named;
/**
 *
 * @author MOH
 */
@Named(value = "driversInfoBean")
@ViewScoped
public class ManageDriversBean implements Serializable {
    private DriversInfo selectedDriver;
    private final DriversDao driverDao = new DriversDao();
    private ArrayList<DriversInfo> driverInfo; 
    
    @Inject 
    private SessionBean sessionBean;
     
    public ManageDriversBean(){}
     
        @PostConstruct
      public void init(){
        try {            
            driverInfo = driverDao.buildDriverInfo();            
        } catch (Exception ex) {
            Logger.getLogger(ManageDriversBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DriversInfo getSelectedDriver() {
        return selectedDriver;
    }

    public void setSelectedDriver(DriversInfo selectedDriver) {
        this.selectedDriver = selectedDriver;
    }

    public ArrayList<DriversInfo> getDriverInfo() {
        return driverInfo;
    }


    public void setDriverInfo(ArrayList<DriversInfo> driverInfo) {
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
            Logger.getLogger(ManageDriversBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
