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
 
    @Inject
    private SessionBean sessionBean;

    @PostConstruct
    public void init() {
        try {

            //driverId = sessionBean.getSelectedDriverId();
            driverPastTripsArray = tripsDao.buildPastTrips(driverId);

        } catch (Exception ex) {
  
            Logger.getLogger(DriverSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
