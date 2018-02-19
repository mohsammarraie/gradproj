/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import daos.DriverSchedulesDao;
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
import models.DriverSchedule;
import models.Stop;
import models.Trip;

/**
 *
 * @author MOH
 */
@Named(value = "tripsBean")
@ViewScoped
public class TripsBean implements Serializable {

    private final TripsDao tripsDao = new TripsDao();
    private final DriverSchedulesDao driverSchedulesDao = new DriverSchedulesDao();

    private ArrayList<Stop> driverRouteSchedulesStopsArray;
    Trip trip = new Trip();
    private int driverId;
    private ArrayList<DriverSchedule> driverSchedulesArray;
    private final DriversDao driversDao = new DriversDao();
    private String nationalId;
    @Inject
    private SessionBean sessionBean;

    @PostConstruct
    public void init() {
        try {
            nationalId= sessionBean.getDriverUserNationalId();
            driverId = driversDao.nationalIdToDriverId(nationalId);
            driverSchedulesArray = driverSchedulesDao.buildDriverSchedules(driverId);

            driverRouteSchedulesStopsArray = tripsDao.buildDriverRouteStopsSchedules(sessionBean.getSelectedRouteId(), sessionBean.getSelectedScheduleId());

        } catch (Exception ex) {
            
   
            Logger.getLogger(TripsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Stop> getDriverRouteSchedulesStopsArray() {
        return driverRouteSchedulesStopsArray;
    }

    public void setDriverRouteSchedulesStopsArray(ArrayList<Stop> driverRouteSchedulesStopsArray) {
        this.driverRouteSchedulesStopsArray = driverRouteSchedulesStopsArray;
    }

    public void cancelTrip() {
        try {
            tripsDao.updateTripCancel();

        } catch (Exception ex) {
            Logger.getLogger(TripsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        sessionBean.navigateDriverSchedules();
    }

    public void endTrip() {
        
         int i;
        for (i = 0; i < driverSchedulesArray.size(); i++) {
            if (driverSchedulesArray.get(i).getDriverRouteScheduleId() == sessionBean.getSelectedDriverSchedule()) {
                trip.setBusId(driverSchedulesArray.get(i).getBusId());
                trip.setRouteId(driverSchedulesArray.get(i).getRouteId());
                //trip.setDriverId(driverSchedulesArray.get(i).getDriverId());
                trip.setScheduleId(driverSchedulesArray.get(i).getScheduleId());
                trip.setDepartureTime(driverSchedulesArray.get(i).getDepartureTime());
                trip.setArrivalTime(driverSchedulesArray.get(i).getArrivalTime());
                try {
                    trip.setDriverId(driverId);

                    sessionBean.setSelectedRouteId(trip.getRouteId());
                    sessionBean.setSelectedScheduleId(trip.getScheduleId());
                    tripsDao.updateTripEnd(trip);
                } catch (Exception ex) {
                   
                    Logger.getLogger(TripsBean.class.getName()).log(Level.SEVERE, null, ex);
                }
                sessionBean.navigateDriverSchedules();
            }
        }
        
    
      
    }

}
