/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import daos.DriversDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import daos.TripsDao;
import models.Trip;

/**
 *
 * @author MOH
 */
@Named(value = "driverPastTripsBean")
@ViewScoped
public class DriverPastTripsBean implements Serializable {

    private final TripsDao tripsDao = new TripsDao();

    private ArrayList<Trip> driverPastTripsArray;
    private int driverId;
    private final DriversDao driversDao = new DriversDao();
    private String nationalId;
    @Inject
    private SessionBean sessionBean;
  

    @PostConstruct
    public void init() {
        try {
            nationalId= sessionBean.getDriverUserNationalId();
            driverId = driversDao.nationalIdToDriverId(nationalId);
            driverPastTripsArray = tripsDao.buildPastTrips(driverId);

        } catch (Exception ex) {
  
            Logger.getLogger(DriverSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }
    
    public ArrayList<Trip> getDriverPastTripsArray() {
        return driverPastTripsArray;
    }

    public void setDriverPastTripsArray(ArrayList<Trip> driverPastTripsArray) {
        this.driverPastTripsArray = driverPastTripsArray;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

}
