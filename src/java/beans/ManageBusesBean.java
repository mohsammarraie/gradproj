/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import daos.BusesDao;
import daos.BusesDriversDao;
import daos.RouteSchedulesDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.Bus;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import models.BusDriver;
import org.primefaces.context.RequestContext;

/**
 *
 * @author MOH
 */
@Named(value = "manageBusesBean")
@ViewScoped
public class ManageBusesBean implements Serializable {

    private Bus selectedBus;
    private final BusesDao busesDao = new BusesDao();

    private ArrayList<Bus> busesArray;
    private ArrayList<BusDriver> busesDriversArray;
    String error_message_header = "";
    String error_message_content = "";
     private boolean flagDriver ;
     private  boolean flagSchedule;
    BusesDriversDao busesDriversDao = new BusesDriversDao();
    @Inject
    private SessionBean sessionBean;

    public ManageBusesBean() {
    }

    @PostConstruct
    public void init() {
        try {
            busesDriversArray = busesDriversDao.buildBusesDrivers();
            busesArray = busesDao.buildBuses();
        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, error_message_header, error_message_content));
            Logger.getLogger(ManageBusesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isFlagDriver() {
        return flagDriver;
    }

    public void setFlagDriver(boolean flagDriver) {
        this.flagDriver = flagDriver;
    }

    public boolean isFlagSchedule() {
        return flagSchedule;
    }

    public void setFlagSchedule(boolean flagSchedule) {
        this.flagSchedule = flagSchedule;
    }
    
    public ArrayList<BusDriver> getBusesDriversArray() {
        return busesDriversArray;
    }

    public void setBusesDriversArray(ArrayList<BusDriver> busesDriversArray) {
        this.busesDriversArray = busesDriversArray;
    }

    public Bus getSelectedBus() {
        return selectedBus;
    }

    public void setSelectedBus(Bus selectedBus) {
        this.selectedBus = selectedBus;
    }

    public ArrayList<Bus> getBusesArray() {
        return busesArray;
    }

    public void setBusesArray(ArrayList<Bus> busesArray) {
        this.busesArray = busesArray;
    }

    public void saveSelectedBusId() {
        sessionBean.setSelectedBusId(selectedBus.getBusId());
    }

    public void deleteSelectedBus() {
        try {
             flagDriver = busesDao.checkBusesDrivers(selectedBus.getBusId());

             flagSchedule = busesDao.checkBusesSchedules(selectedBus.getBusId());
             
            if (flagSchedule || flagDriver) {
              
                
                if (flagSchedule) {
                         //show error popup
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('popup_unassign_schedule_from_bus').show();");
                
                  
                } else {
                         //show error popup
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('popup_unassign_driver_from_bus').show();");
                
                  
                }
//                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            } else {
                busesDao.deleteBus(selectedBus.getBusId());
                sessionBean.navigateManageBuses();

            }

        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();
            if (error_message_content.contains("ORA-02292: integrity constraint (BUSES.BUSES_DRIVERS_FK1) violated - child record found")) {
                  //show error popup
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('popup_unassign_driver_from_bus').show();");
                
                //error_message_content = "Unable to delete bus with assigned driver. Please remove assigned driver first then try again.";

            }

            //RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(ManageBusesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteBusDrivers() {
        try {
            busesDriversDao.deleteBusDriver(selectedBus.getBusId());
            sessionBean.navigateManageBuses();

        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, error_message_header, error_message_content));
            Logger.getLogger(ManageBusesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //check if there is an assigned driver. if found then disable delete button on assign_driver_to_bus.xhtml
    public boolean checkRemoveDriverButton() {
        int i;
        boolean flag = false;
        for (i = 0; i < busesDriversArray.size(); i++) {
            if (selectedBus.getBusId() == busesDriversArray.get(i).getBusId()) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    public void checklBusesSchedulesDrivers() {
         flagSchedule = busesDao.checkBusesSchedules(selectedBus.getBusId());
         flagDriver = busesDao.checkBusesDrivers(selectedBus.getBusId());

        try {
            if (flagSchedule || flagDriver) {
                if (flagSchedule) {
                //show error popup
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('popup_unassign_schedule_from_bus').show();");
                } else {
                    //show error popup
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('popup_unassign_driver_from_bus').show();");
                }

            } else {
                sessionBean.navigateAddEditBus();
            }

        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, error_message_header, error_message_content));
            Logger.getLogger(ManageRouteStopsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
