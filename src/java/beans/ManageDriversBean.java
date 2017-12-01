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
import models.Drivers;
import daos.DriversDao;
import javax.inject.Named;
/**
 *
 * @author MOH
 */
@Named(value = "manageDriversBean")
@ViewScoped
public class ManageDriversBean implements Serializable {
    private Drivers selectedDriver;
    private final DriversDao driverDao = new DriversDao();
    private ArrayList<Drivers> driversArray; 
    
    @Inject 
    private SessionBean sessionBean;
     
    public ManageDriversBean(){}
     
        @PostConstruct
      public void init(){
        try {            
            driversArray = driverDao.buildDriver();            
        } catch (Exception ex) {
            Logger.getLogger(ManageDriversBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Drivers getSelectedDriver() {
        return selectedDriver;
    }

    public void setSelectedDriver(Drivers selectedDriver) {
        this.selectedDriver = selectedDriver;
    }

    public ArrayList<Drivers> getDriversArray() {
        return driversArray;
    }


    public void setDriversArray(ArrayList<Drivers> driversArray) {
        this.driversArray = driversArray;
    }


    public void saveSelectedDriverId(){
        sessionBean.setSelectedDriverId(selectedDriver.getDriverId());
    }
    
    public void deleteSelectedDriver(){
        try {
            driverDao.deleteDriver(selectedDriver.getDriverId());
            sessionBean.navigate("manage_drivers");

        } catch (Exception ex) {
            sessionBean.navigate("error_page");
            Logger.getLogger(ManageDriversBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
