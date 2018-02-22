/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.Stop;
import daos.RouteStopsDao;
import daos.StopsDao;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import models.RouteStop;
import org.primefaces.context.RequestContext;

/**
 *
 * @author MOH
 */
@Named(value = "addEditRouteStopBean")
@ViewScoped
public class AddEditRouteStopBean implements Serializable {

    private ArrayList<RouteStop> routeStopsArray;
    private ArrayList<Stop> stopsArray;
    private final RouteStopsDao routeStopsDao = new RouteStopsDao();
    private final StopsDao stopsDao = new StopsDao();
    private int routeStopId;
    private int stopId;
    private String stopNameAr;
    private String stopNameEn;
    private int stopOrder;
    RouteStop routeStops = new RouteStop();
    String error_message_header = "";
    String error_message_content = "";

    @Inject
    private SessionBean sessionBean;

    public AddEditRouteStopBean() {
    }

    @PostConstruct
    public void init() {
        try {
            routeStopId = sessionBean.getSelectedRouteStopId();
            routeStopsArray = routeStopsDao.buildRouteStops(routeStopId);
            stopsArray = stopsDao.buildStops();
            if (routeStopId > 0) {
                routeStops = routeStopsDao.getRouteStops(routeStopId);
                stopId = routeStops.getStopId();
                stopNameAr = routeStops.getStopNameAr();
                stopNameEn = routeStops.getStopNameEn();
                stopOrder = routeStops.getStopOrder();

            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditStopBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Stop> getStopsArray() {
        return stopsArray;
    }

    public void setStopsArray(ArrayList<Stop> stopsArray) {
        this.stopsArray = stopsArray;
    }

    public int getStopOrder() {
        return stopOrder;
    }

    public void setStopOrder(int stopOrder) {
        this.stopOrder = stopOrder;
    }

    public ArrayList<RouteStop> getRouteStopsArray() {
        return routeStopsArray;
    }

    public int getRouteStopId() {
        return routeStopId;
    }

    public void setRouteStopId(int routeStopId) {
        this.routeStopId = routeStopId;
    }

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public String getStopNameAr() {
        return stopNameAr;
    }

    public void setStopNameAr(String stopNameAr) {
        this.stopNameAr = stopNameAr;
    }

    public String getStopNameEn() {
        return stopNameEn;
    }

    public void setStopNameEN(String stopNameEn) {
        this.stopNameEn = stopNameEn;
    }

    public void saveRouteStop() {
        try {

            routeStops.setStopId(routeStopId);

            if (sessionBean.getSelectedRouteStopId() > 0) {
                routeStopsDao.updateRouteStop(routeStopId, stopId, sessionBean.getSelectedRouteId(), stopOrder);
            } else {
                routeStopsDao.insertRouteStop(routeStopId, sessionBean.getSelectedRouteId(), stopOrder);

            }
            sessionBean.navigateManageRouteStops();
        } catch (Exception ex) {

             //show error popup
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('popup_add_edit_route_stop').show();");
            Logger.getLogger(AddEditRouteStopBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
