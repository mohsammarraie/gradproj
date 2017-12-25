///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package beans;
//import daos.RouteSchedulesDao;
//import java.io.Serializable;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.annotation.PostConstruct;
//import javax.inject.Named;
//import javax.faces.view.ViewScoped;
//import javax.inject.Inject;
//import models.Stops;
//import daos.RouteStopsDao;
//import daos.StopsDao;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.Date;
//import javax.faces.application.FacesMessage;
//import models.RouteSchedules;
//import models.RouteStops;
//import org.primefaces.context.RequestContext;
///**
// *
// * @author MOH
// */
//@Named(value = "addEditRouteScheduleBean")
//@ViewScoped
//public class AddEditRouteScheduleBean implements Serializable{
//     
//    private ArrayList<RouteSchedules> routeSchedulesArray;
//    private ArrayList<String> routeScheduleStops;
//    private int scheduleId;
//    private int stopId;
//    private int routeId;
//    private String stopNameAr;
//    private String stopNameEn;
//    private int stopOrder;
//    private ArrayList<Date> addTimeArray;
//    private Date addTime;
//    private final RouteSchedulesDao routeSchedulesDao = new RouteSchedulesDao();
//    RouteSchedules routeSchedules = new RouteSchedules();
//    String error_message_header = "";
//    String error_message_content = "";
//    Stops stops = new Stops();
//    @Inject
//    private SessionBean sessionBean;
//    
//    public AddEditRouteScheduleBean() {        
//    }
//        
//    @PostConstruct
//    public void init(){                
//        try {
//            scheduleId = sessionBean.getSelectedRouteScheduleId();
//            routeId = sessionBean.getSelectedRouteId();
//            routeSchedulesArray = routeSchedulesDao.buildRouteSchedules(routeId);
//            int i;
//            for(i=0;i<routeSchedulesArray.size();i++){
//            routeScheduleStops.add(routeSchedulesArray.get(i).getStopNameEn());
//            }
////            if(scheduleId > 0){
//                
//                routeSchedules = routeSchedulesDao.getRouteSchedules(routeId);
////                stopId = routeSchedules.getStopId();
////                stopNameAr = routeSchedules.getStopNameAr();
////                stopNameEn = routeSchedules.getStopNameEn();
////                addTimeArray =routeSchedules.getScheduleTime();
//                
////            }
//        } catch (Exception ex) {
//            Logger.getLogger(AddEditStopBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public ArrayList<String> getRouteScheduleStops() {
//        return routeScheduleStops;
//    }
//
//    public void setRouteScheduleStops(ArrayList<String> routeScheduleStops) {
//        this.routeScheduleStops = routeScheduleStops;
//    }
//    
//    public Date getAddTime() {
//        return addTime;
//    }
//
//    public void setAddTime(Date addTime) {
//        this.addTime = addTime;
//    }
//    
//    public ArrayList<RouteSchedules> getRouteSchedulesArray() {
//        return routeSchedulesArray;
//    }
//
//    public void setRouteSchedulesArray(ArrayList<RouteSchedules> routeSchedulesArray) {
//        this.routeSchedulesArray = routeSchedulesArray;
//    }
//
//    public int getRouteId() {
//        return routeId;
//    }
//
//    public void setRouteId(int routeId) {
//        this.routeId = routeId;
//    }
//    
////    public Date getAddTime() {
////        return addTimeArray;
////    }
////
////    public void setAddTime(Date addTimeArray) {
////        this.addTimeArray = addTimeArray;
////    }
//
//    public ArrayList<Date> getAddTimeArray() {
//        return addTimeArray;
//    }
//
//    public void setAddTimeArray(ArrayList<Date> addTimeArray) {
//        this.addTimeArray = addTimeArray;
//    }
//
//
//    public int getStopOrder() {
//        return stopOrder;
//    }
//
//    public void setStopOrder(int stopOrder) {
//        this.stopOrder = stopOrder;
//    }
//
//    public int getScheduleId() {
//        return scheduleId;
//    }
//
//    public void setScheduleId(int scheduleId) {
//        this.scheduleId = scheduleId;
//    }
//
//    public int getStopId() {
//        return stopId;
//    }
//
//    public void setStopId(int stopId) {
//        this.stopId = stopId;
//    }
//
//    public String getStopNameAr() {
//        return stopNameAr;
//    }
//
//    public void setStopNameAr(String stopNameAr) {
//        this.stopNameAr = stopNameAr;
//    }
//
//    public String getStopNameEn() {
//        return stopNameEn;
//    }
//
//    public void setStopNameEN(String stopNameEn) {
//        this.stopNameEn = stopNameEn;
//    }
//    
//
//        public void saveRouteSchedule() {
//        try {
//            int i;
//            for (i = 0; i < routeSchedulesArray.size(); i++) {
//                routeSchedules.setScheduleId(scheduleId);
////             routeSchedules.setStopId(stopId);
//                stopId = routeSchedulesArray.get(i).getStopId();
//                addTime = routeSchedulesArray.get(i).getAddTime();
//                routeSchedules.setScheduleTime(new Timestamp(addTime.getTime()));
//
////             addTimeArray.add(addTime);
//                //           routeSchedulesDao.updateRouteSchedule(routeSchedules, stopId, sessionBean.getSelectedRouteId(),sessionBean.getSelectedRouteScheduleId()); 
//                //                new Timestamp(routeSchedulesArray.get(scheduleId).getScheduleTime().getTime())
//                //            if (sessionBean.getSelectedRouteScheduleId() > 0) {
//                //                routeSchedulesDao.updateRouteSchedule(routeSchedules, stopId, sessionBean.getSelectedRouteId(),sessionBean.getSelectedRouteScheduleId()); 
//                //                
//                //            } else {
//                routeSchedulesDao.insertRouteSchedule(stopId, sessionBean.getSelectedRouteId(), scheduleId, routeSchedules);
//                //            }
//            }
//            sessionBean.navigate("manage_route_schedules");
//
//        } catch (Exception ex) {
//
//            error_message_header = "Error!";
//            error_message_content = ex.getMessage();
//
//            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
//            Logger.getLogger(AddEditStopBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//    
//}
