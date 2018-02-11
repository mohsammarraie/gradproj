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
import models.Stop;
import daos.StopsDao;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author MOH
 */
@Named(value = "manageStopsBean")
@ViewScoped
public class ManageStopsBean implements Serializable {

    private Stop selectedStop;
    private final StopsDao stopsDao = new StopsDao();
    private ArrayList<Stop> stopsArray;
    
    String error_message_header = "";
    String error_message_content = "";
    
    @Inject
    private SessionBean sessionBean;

    public ManageStopsBean() {
    }

    @PostConstruct
    public void init() {
        try {
            stopsArray = stopsDao.buildStops();
        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(ManageStopsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Stop getSelectedStop() {
        return selectedStop;
    }

    public void setSelectedStop(Stop selectedStop) {
        this.selectedStop = selectedStop;
    }

    public ArrayList<Stop> getStopsArray() {
        return stopsArray;
    }

    public void setStopsArray(ArrayList<Stop> stopsArray) {
        this.stopsArray = stopsArray;
    }

    public void saveSelectedStopId() {
        sessionBean.setSelectedStopId(selectedStop.getStopId());
    }

    public void deleteSelectedStop() {
        try {
            stopsDao.deleteStop(selectedStop.getStopId());
            sessionBean.navigateManageStops();

        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(ManageStopsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
