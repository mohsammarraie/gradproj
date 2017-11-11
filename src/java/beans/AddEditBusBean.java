/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;
import daos.DriversDao;
import daos.BusesDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.BusesInfo;
import models.DriversInfo;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author MOH
 */

@Named(value = "addEditBusesBean")
@ViewScoped
public class AddEditBusBean implements Serializable{
    private ArrayList<DriversInfo> driverInfos;
    private final DriversDao driverDao = new DriversDao();
    private final BusesDao busesDao = new BusesDao();
    private int busID;
    private String licenseNumber;
    private int chasisNumber;
    private int capacity;
    
        @Inject
    private SessionBean sessionBean;
    
    public AddEditBusBean() {        
    }
        
    @PostConstruct
    public void init(){                
        try {
            busID = sessionBean.getSelectedItemId();
            driverInfos = driverDao.buildDriverInfo();
            
            if(busID > 0){
                BusesInfo busesInfo = busesDao.getBusesInfo(busID);
                chasisNumber= busesInfo.getChasisNumber();
                licenseNumber = busesInfo.getLicenseNumber();
                capacity = busesInfo.getCapacity();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditBusBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<DriversInfo> getDriverInfos() {
        return driverInfos;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public int getChasisNumber() {
        return chasisNumber;
    }

    public void setChasisNumber(int chasisNumber) {
        this.chasisNumber = chasisNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
        public void saveBus() {
        try {
            
            BusesInfo busesInfo = new BusesInfo();
            busesInfo.setBusID(busID);
            busesInfo.setLicenseNumber(licenseNumber);
            busesInfo.setCapacity(capacity);
            busesInfo.setChasisNumber(chasisNumber);
            if (sessionBean.getSelectedItemId() > 0) {
                busesDao.updateBus(busesInfo);
            } else {
                busesDao.insertBus(busesInfo);
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditBusBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean.navigate("manage_buses");
    }
    
}
