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
import models.Route;
import daos.RoutesDao;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author MOH
 */
@Named(value = "manageRoutesBean")
@ViewScoped
public class ManageRoutesBean implements Serializable {

    private Route selectedRoute;
    private final RoutesDao routesDao = new RoutesDao();
    private ArrayList<Route> routesArray;
    @Inject
    private SessionBean sessionBean;
    String error_message_header = "Error!";
    String error_message_content = "Please delete all stops and schedules related to this route.";

    public ManageRoutesBean() {
    }

    @PostConstruct
    public void init() {
        try {
            routesArray = routesDao.buildRoutes();

        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(ManageRoutesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Route getSelectedRoute() {
        return selectedRoute;
    }

    public void setSelectedRoute(Route selectedRoute) {
        this.selectedRoute = selectedRoute;
    }

    public ArrayList<Route> getRoutesArray() {
        return routesArray;
    }

    public void setRoutesArray(ArrayList<Route> routesArray) {
        this.routesArray = routesArray;
    }

    public void saveSelectedRouteId() {
        sessionBean.setSelectedRouteId(selectedRoute.getRouteId());
    }

    public void deleteSelectedRoute() {
        try {
            routesDao.deleteRoute(selectedRoute.getRouteId());
            sessionBean.navigateManageRoutes();

        } catch (Exception ex) {

            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(ManageRoutesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
