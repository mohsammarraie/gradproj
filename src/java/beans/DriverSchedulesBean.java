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
import models.DriverSchedule;
import javax.inject.Named;
import daos.DriverSchedulesDao;
import daos.TripsDao;
import models.Trip;

/**
 *
 * @author MOH
 */
@Named(value = "driverSchedulesBean")
@ViewScoped
public class DriverSchedulesBean implements Serializable {

    private final DriverSchedulesDao driverSchedulesDao = new DriverSchedulesDao();
    private final TripsDao tripsDao = new TripsDao();

    private ArrayList<DriverSchedule> driverSchedulesArray;
    private DriverSchedule selectedSchedule;
    private int driverId;
    private int scheduleId;
    private int routeId;

    @Inject
    private SessionBean sessionBean;

    @PostConstruct
    public void init() {
        try {

            driverId = sessionBean.getSelectedDriverId();
            driverSchedulesArray = driverSchedulesDao.buildDriverSchedules(driverId);

        } catch (Exception ex) {
            Logger.getLogger(DriverSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<DriverSchedule> getDriverSchedulesArray() {
        return driverSchedulesArray;
    }

    public void setDriverSchedulesArray(ArrayList<DriverSchedule> driverSchedulesArray) {
        this.driverSchedulesArray = driverSchedulesArray;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public DriverSchedule getSelectedSchedule() {
        return selectedSchedule;
    }

    public void setSelectedSchedule(DriverSchedule selectedSchedule) {
        this.selectedSchedule = selectedSchedule;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public void startTrip() {
        //save Selected Driver Schedule Id
        sessionBean.setSelectedDriverSchedule(selectedSchedule.getDriverRouteScheduleId());

        Trip trip = new Trip();

        int i;
        for (i = 0; i < driverSchedulesArray.size(); i++) {
            if (driverSchedulesArray.get(i).getDriverRouteScheduleId() == sessionBean.getSelectedDriverSchedule()) {
                trip.setBusId(driverSchedulesArray.get(i).getBusId());
                trip.setRouteId(driverSchedulesArray.get(i).getRouteId());
                trip.setDriverId(driverSchedulesArray.get(i).getDriverId());
                trip.setScheduleId(driverSchedulesArray.get(i).getScheduleId());
                try {
                    sessionBean.setSelectedRouteId(trip.getRouteId());
                    sessionBean.setSelectedScheduleId(trip.getScheduleId());
                    tripsDao.insertTrip(trip);
                } catch (Exception ex) {
                    Logger.getLogger(DriverSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        sessionBean.navigateDriverMap();

    }

}
