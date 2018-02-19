/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import daos.BusesDriversDao;
import daos.DriversDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.BusDriver;
import org.primefaces.context.RequestContext;

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
    private final DriversDao driversDao = new DriversDao();
    private String nationalId;
    @Inject
    private SessionBean sessionBean;

    public BusDriverInfoBean() {
    }

    @PostConstruct
    public void init() {
        try {
            nationalId = sessionBean.getDriverUserNationalId();
            driverId = driversDao.nationalIdToDriverId(nationalId);
            busDriverInfoArray = busesDriversDao.buildBusDriverInfo(driverId);

        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, error_message_header, error_message_content));
            Logger.getLogger(BusDriverInfoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
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
