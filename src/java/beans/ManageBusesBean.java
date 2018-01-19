/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import daos.BusesDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.Buses;
import daos.DriversDao;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
/**
 *
 * @author MOH
 */
@Named(value = "manageBusesBean")
@ViewScoped
public class ManageBusesBean implements Serializable {

    private Buses selectedBus;
    private final BusesDao busesDao = new BusesDao();
    private ArrayList<Buses> busesArray;
    String error_message_header;
    String error_message_content;

    @Inject
    private SessionBean sessionBean;

    public ManageBusesBean() {
    }

    @PostConstruct
    public void init() {
        try {
            busesArray = busesDao.buildBuses();
        } catch (Exception ex) {
            Logger.getLogger(ManageBusesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Buses getSelectedBus() {
        return selectedBus;
    }

    public void setSelectedBus(Buses selectedBus) {
        this.selectedBus = selectedBus;
    }

    public ArrayList<Buses> getBusesArray() {
        return busesArray;
    }

    public void setBusesArray(ArrayList<Buses> busesArray) {
        this.busesArray = busesArray;
    }

    public void saveSelectedBusId() {
        sessionBean.setSelectedBusId(selectedBus.getBusId());
    }

    public void deleteSelectedBus() {
        try {
            busesDao.deleteBus(selectedBus.getBusId());
            sessionBean.navigate("manage_buses");
        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(ManageBusesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
