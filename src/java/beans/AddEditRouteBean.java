/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import daos.RoutesDao;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.Route;
import org.primefaces.context.RequestContext;

/**
 *
 * @author MOH
 */
@Named(value = "addEditRouteBean")
@ViewScoped
public class AddEditRouteBean implements Serializable {

    private final RoutesDao routesDao = new RoutesDao();
    private int routeId;
    private String sourceAr;
    private String sourceEn;
    private String destinationAr;
    private String destinationEn;
    private String routeCode;
    private int routeActive;
    private boolean checkBoxValue;
    
    String error_message_header = "";
    String error_message_content = "";
    
    @Inject
    private SessionBean sessionBean;

    public AddEditRouteBean() {
    }

    @PostConstruct
    public void init() {
        try {
            routeId = sessionBean.getSelectedRouteId();

            if (routeId > 0) {
                Route routesArray = routesDao.getRoutes(routeId);

                sourceAr = routesArray.getSourceAr();
                sourceEn = routesArray.getSourceEn();
                destinationAr = routesArray.getDestinationAr();
                destinationEn = routesArray.getDestinationEn();
                routeCode = routesArray.getRouteCode();
                routeActive = routesArray.getRouteActive();
                if (routeActive == 1) {
                    checkBoxValue = true;
                } else {
                    checkBoxValue = false;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditRouteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isCheckBoxValue() {
        return checkBoxValue;
    }

    public void setCheckBoxValue(boolean checkBoxValue) {
        this.checkBoxValue = checkBoxValue;
    }

    public int getRouteActive() {
        return routeActive;
    }

    public void setRouteActive(int routeActive) {
        this.routeActive = routeActive;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getSourceAr() {
        return sourceAr;
    }

    public void setSourceAr(String sourceAr) {
        this.sourceAr = sourceAr;
    }

    public String getSourceEn() {
        return sourceEn;
    }

    public void setSourceEn(String sourceEn) {
        this.sourceEn = sourceEn;
    }

    public String getDestinationAr() {
        return destinationAr;
    }

    public void setDestinationAr(String destinationAr) {
        this.destinationAr = destinationAr;
    }

    public String getDestinationEn() {
        return destinationEn;
    }

    public void setDestinationEn(String destinationEn) {
        this.destinationEn = destinationEn;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public void addActive() {
        if (checkBoxValue == false) {
            routeActive = 0;
        } else {
            routeActive = 1;
        }
    }

    public void saveRoute() {
        try {

            Route routes = new Route();
            routes.setRouteId(routeId);
            routes.setSourceAr(sourceAr);
            routes.setSourceEn(sourceEn);
            routes.setDestinationAr(destinationAr);
            routes.setDestinationEn(destinationEn);
            routes.setRouteCode(routeCode);
            routes.setRouteActive(routeActive);
            if (sessionBean.getSelectedRouteId() > 0) {
                routesDao.updateRoute(routes);
            } else {
                routesDao.insertRoute(routes);
            }
        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, error_message_header, error_message_content));
            Logger.getLogger(AddEditRouteBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean.navigateManageRoutes();
    }

}
