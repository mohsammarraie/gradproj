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
import daos.StudentTripsDao;
import models.Stop;
import models.StudentTrip;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author MOH
 */
@Named(value = "studentMapBean")
@ViewScoped
public class StudentMapBean implements Serializable {

    private final StudentTripsDao studentTripsDao = new StudentTripsDao();
    private ArrayList<Stop> studentRouteSchedulesStopsArray;
    private ArrayList<StudentTrip> studentTrackMapArray;
    private MapModel model = new DefaultMapModel();
    private double lat;
    private double lng;

    @Inject
    private SessionBean sessionBean;

    @PostConstruct
    public void init() {
        try {

            studentRouteSchedulesStopsArray = studentTripsDao.buildStudentRouteStopsSchedules(sessionBean.getSelectedTripId());
            displayLongLatOnMap();

        } catch (Exception ex) {
            Logger.getLogger(StudentMapBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public ArrayList<Stop> getStudentRouteSchedulesStopsArray() {
        return studentRouteSchedulesStopsArray;
    }

    public void setStudentRouteSchedulesStopsArray(ArrayList<Stop> studentRouteSchedulesStopsArray) {
        this.studentRouteSchedulesStopsArray = studentRouteSchedulesStopsArray;
    }

    public ArrayList<StudentTrip> getStudentTrackMapArray() {
        return studentTrackMapArray;
    }

    public void setStudentTrackMapArray(ArrayList<StudentTrip> studentTrackMapArray) {
        this.studentTrackMapArray = studentTrackMapArray;
    }

    public MapModel getModel() {
        return this.model;
    }

    public void displayLongLatOnMap() {
        try {
            int i;
            lat = 0;
            lng = 0;
            studentTrackMapArray = studentTripsDao.buildStudentTrackMap(sessionBean.getSelectedTripId());
            for (i = 0; i < studentTrackMapArray.size(); i++) {
                lat = Double.parseDouble(studentTrackMapArray.get(i).getLatitude());
                lng = Double.parseDouble(studentTrackMapArray.get(i).getLongtitude());
            }

            model.addOverlay(new Marker(new LatLng(lat, lng), ""));

        } catch (Exception ex) {
            Logger.getLogger(StudentMapBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
