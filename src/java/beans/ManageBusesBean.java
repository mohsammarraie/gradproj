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
import javax.inject.Named;
/**
 *
 * @author MOH
 */
@Named(value = "busesInfoBean")
@ViewScoped
public class ManageBusesBean implements Serializable {

    private Buses selectedBus;
    private final BusesDao busesDao = new BusesDao();
    private ArrayList<Buses> busesInfo;

    @Inject
    private SessionBean sessionBean;

    public ManageBusesBean() {
    }

    @PostConstruct
    public void init() {
        try {
            busesInfo = busesDao.buildBusesInfo();
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

    public ArrayList<Buses> getBusesInfo() {
        return busesInfo;
    }

    public void setBusesInfo(ArrayList<Buses> busesInfo) {
        this.busesInfo = busesInfo;
    }

    public void saveSelectedItemId() {
        sessionBean.setSelectedItemId(selectedBus.getBusID());
    }

    public void deleteSelectedBus() {
        try {
            busesDao.deleteBus(selectedBus.getBusID());
            sessionBean.navigate("manage_buses");
        } catch (Exception ex) {
            Logger.getLogger(ManageBusesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
