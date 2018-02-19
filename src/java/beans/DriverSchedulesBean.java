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
import daos.DriversDao;
import daos.TripsDao;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.faces.application.FacesMessage;
import models.Trip;
import org.primefaces.context.RequestContext;

/**
 *
 * @author MOH
 */
@Named(value = "driverSchedulesBean")
@ViewScoped
public class DriverSchedulesBean implements Serializable {

    private final DriverSchedulesDao driverSchedulesDao = new DriverSchedulesDao();
    private final TripsDao tripsDao = new TripsDao();
    private final DriversDao driversDao = new DriversDao();
    private ArrayList<DriverSchedule> driverSchedulesArray;
    private DriverSchedule selectedSchedule;
    private int driverId;
    private int scheduleId;
    private int routeId;
    private String nationalId;
    private boolean color;

    String error_message_header = "";
    String error_message_content = "";

    @Inject
    private SessionBean sessionBean;

    @PostConstruct
    public void init() {
        try {
            //startTripRestriction();
            nationalId= sessionBean.getDriverUserNationalId();
            driverId = driversDao.nationalIdToDriverId(nationalId);
            driverSchedulesArray = driverSchedulesDao.buildDriverSchedules(driverId);

        } catch (Exception ex) {

            Logger.getLogger(DriverSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }
    
    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
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
        Date currentTime = new Date();
        Date scheduleDepartureTime = selectedSchedule.getDepartureTime();
        Format formatt = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        String currentTimeToString = formatt.format(currentTime);
        String scheduleDepartureTimeToString = formatt.format(scheduleDepartureTime);

        String[] splitCurrentTimeToString = currentTimeToString.split(" ");
        String[] splitScheduleDepartureTimeToString = scheduleDepartureTimeToString.split(" ");

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");

        try {
            Date currentTimeOnly = df.parse(splitCurrentTimeToString[1]);
            Date scheduleDepartureTimeOnly = df.parse(splitScheduleDepartureTimeToString[1]);

            Calendar calCurrentTime = Calendar.getInstance();
            Calendar calLateTime = Calendar.getInstance();
            Calendar calEarlyTime = Calendar.getInstance();

            calCurrentTime.setTime(currentTimeOnly);
            //calLateTime.setTime(scheduleDepartureTimeOnly);
            calEarlyTime.setTime(scheduleDepartureTimeOnly);

            calEarlyTime.add(Calendar.MINUTE, -30);

            if (calCurrentTime.compareTo(calEarlyTime) > 0) {
                
                Trip trip = new Trip();
                int i;
                for (i = 0; i < driverSchedulesArray.size(); i++) {
                    if (driverSchedulesArray.get(i).getDriverRouteScheduleId() == sessionBean.getSelectedDriverSchedule()) {
                        trip.setBusId(driverSchedulesArray.get(i).getBusId());
                        trip.setRouteId(driverSchedulesArray.get(i).getRouteId());
                        trip.setDriverId(driverSchedulesArray.get(i).getDriverId());
                        trip.setScheduleId(driverSchedulesArray.get(i).getScheduleId());
                        trip.setDepartureTime(driverSchedulesArray.get(i).getDepartureTime());
                        trip.setArrivalTime(driverSchedulesArray.get(i).getArrivalTime());
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

            } else {
                   //show error popup
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('popup_driver_schedule').show();");
              
            }

        } catch (ParseException ex) {
               error_message_header = "Error!";
                 error_message_content = ex.getMessage();
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, error_message_header, error_message_content));

            Logger.getLogger(DriverSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean startTripRestriction(Date scheduleDepartureTime) {
    
        Date currentTime = new Date();
//        scheduleDepartureTime = selectedSchedule.getDepartureTime();
        Format formatt = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        String currentTimeToString = formatt.format(currentTime);
        String scheduleDepartureTimeToString = formatt.format(scheduleDepartureTime);

        String[] splitCurrentTimeToString = currentTimeToString.split(" ");
        String[] splitScheduleDepartureTimeToString = scheduleDepartureTimeToString.split(" ");

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");

      
            Date currentTimeOnly;
        try {
            currentTimeOnly = df.parse(splitCurrentTimeToString[1]);
            Date scheduleDepartureTimeOnly = df.parse(splitScheduleDepartureTimeToString[1]);
            Calendar calCurrentTime = Calendar.getInstance();
            Calendar calLateTime = Calendar.getInstance();
            Calendar calEarlyTime = Calendar.getInstance();

            calCurrentTime.setTime(currentTimeOnly);
            //calLateTime.setTime(scheduleDepartureTimeOnly);
            calEarlyTime.setTime(scheduleDepartureTimeOnly);

            calEarlyTime.add(Calendar.MINUTE, -30);

            if (calCurrentTime.compareTo(calEarlyTime) > 0) {
                color =true;
            }
            else{
                color=false;
            }

        } catch (ParseException ex) {
            Logger.getLogger(DriverSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return color;
    }

}
