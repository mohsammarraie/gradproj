/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import daos.BusesDriversDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.BusDriver;

/**
 *
 * @author MOH
 */
@Named(value = "busDriverInfoBean")
@ViewScoped
public class BusDriverInfoBean implements Serializable {

    private ArrayList<BusDriver> busDriverInfoArray;
    String error_message_header = "";
    String error_message_content = "";
    private int driverId;
    BusesDriversDao busesDriversDao = new BusesDriversDao();

    @Inject
    private SessionBean sessionBean;

    public BusDriverInfoBean() {
    }

    @PostConstruct
    public void init() {
        try {
            busDriverInfoArray = busesDriversDao.buildBusDriverInfo(driverId);

        } catch (Exception ex) {
            Logger.getLogger(BusDriverInfoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<BusDriver> getBusDriverInfoArray() {
        return busDriverInfoArray;
    }

    public void setBusDriverInfoArray(ArrayList<BusDriver> busDriverInfoArray) {
        this.busDriverInfoArray = busDriverInfoArray;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

}
