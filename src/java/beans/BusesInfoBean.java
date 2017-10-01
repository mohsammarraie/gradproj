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
import models.BusesInfo;
import daos.DriverDao;
import javax.inject.Named;
/**
 *
 * @author MOH
 */
@Named(value = "busesInfoBean")
@ViewScoped
public class BusesInfoBean implements Serializable {

    private BusesInfo selectedBus;
    private final DriverDao driverDao = new DriverDao();
    private final BusesDao busesDao = new BusesDao();
    private ArrayList<BusesInfo> busesInfo;

    @Inject
    private SessionBean sessionBean;

    public BusesInfoBean() {
    }

    @PostConstruct
    public void init() {
        try {
            busesInfo = busesDao.buildBusesInfo(driverDao.buildDriverInfoMap());
        } catch (Exception ex) {
            Logger.getLogger(BusesInfoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BusesInfo getSelectedBus() {
        return selectedBus;
    }

    public void setSelectedBus(BusesInfo selectedBus) {
        this.selectedBus = selectedBus;
    }

    public ArrayList<BusesInfo> getBusesInfo() {
        return busesInfo;
    }

    public void setBusesInfo(ArrayList<BusesInfo> busesInfo) {
        this.busesInfo = busesInfo;
    }

    public void saveSelectedItemId() {
        sessionBean.setSelectedItemId(selectedBus.getBusID());
    }

    public void deleteSelectedBus() {
        try {
            busesDao.deleteBus(selectedBus.getBusID());
        } catch (Exception ex) {
            Logger.getLogger(BusesInfoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
