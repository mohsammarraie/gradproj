/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import daos.BusesDao;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.Bus;
import org.primefaces.context.RequestContext;

/**
 *
 * @author MOH
 */
@Named(value = "addEditBusBean")
@ViewScoped
public class AddEditBusBean implements Serializable {

    private final BusesDao busesDao = new BusesDao();
    private int busId;
    private String licenseNumber;
    private int chasisNumber;
    private int capacity;
    private int model;
    private String manufacturer;
    
    String error_message_header = "";
    String error_message_content = "";
    
    @Inject
    private SessionBean sessionBean;

    public AddEditBusBean() {
    }

    @PostConstruct
    public void init() {
        try {
            busId = sessionBean.getSelectedBusId();

            if (busId > 0) {
                Bus busesArray = busesDao.getBuses(busId);
                chasisNumber = busesArray.getChasisNumber();
                licenseNumber = busesArray.getLicenseNumber();
                capacity = busesArray.getCapacity();
                manufacturer = busesArray.getManufacturer();
                model = busesArray.getModel();
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditBusBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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

            Bus buses = new Bus();
            buses.setBusId(busId);
            buses.setLicenseNumber(licenseNumber);
            buses.setCapacity(capacity);
            buses.setChasisNumber(chasisNumber);
            buses.setManufacturer(manufacturer);
            buses.setModel(model);
            if (sessionBean.getSelectedBusId() > 0) {
                busesDao.updateBus(buses);
            } else {
                busesDao.insertBus(buses);
            }
        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();
            
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, error_message_header, error_message_content));
            Logger.getLogger(AddEditBusBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean.navigateManageBuses();
    }

}
