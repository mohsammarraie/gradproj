package beans;

import java.io.Serializable;
import java.sql.Connection;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @Author: Dr. Firas Al-Hawari
 *
 */
@Named(value = "sessionBean")
@SessionScoped
public class SessionBean implements Serializable {

    private String username;
    private String password;

    // Session attributes

    private Connection connection; 
    
    private int selectedItemId; 
    private int selectedBusId; 
    private String selectedStudentId;
    private int selectedDriverId;
    private int selectedRouteId;
    private int selectedRouteStopId;
    private int selectedStopId;
    private int selectedScheduleId;
    
    
    private int menuIndex = 0;

    public SessionBean() {
    }
    
    
    public int getSelectedRouteStopId() {
        return selectedRouteStopId;
    }

    public void setSelectedRouteStopId(int selectedRouteStopId) {
        this.selectedRouteStopId = selectedRouteStopId;
    }

    public int getSelectedStopId() {
        return selectedStopId;
    }

    public void setSelectedStopId(int selectedStopId) {
        this.selectedStopId = selectedStopId;
    }

    public int getSelectedScheduleId() {
        return selectedScheduleId;
    }

    public void setSelectedScheduleId(int selectedScheduleId) {
        this.selectedScheduleId = selectedScheduleId;
    }
    
    
    public int getSelectedRouteId() {
        return selectedRouteId;
    }

    public void setSelectedRouteId(int selectedRouteId) {
        this.selectedRouteId = selectedRouteId;
    }
    
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public int getSelectedItemId() {
        return selectedItemId;
    }

    public void setSelectedItemId(int selectedItemId) {
        this.selectedItemId = selectedItemId;
    }

    public int getSelectedBusId() {
        return selectedBusId;
    }

    public void setSelectedBusId(int selectedBusId) {
        this.selectedBusId = selectedBusId;
    } 
    
    public String getSelectedStudentId() {

        return selectedStudentId;
    }

    public void setSelectedStudentId(String selectedStudentId) {
        this.selectedStudentId = selectedStudentId;
    }

    public int getSelectedDriverId() {
        return selectedDriverId;
    }

    public void setSelectedDriverId(int selectedDriverId) {
        this.selectedDriverId = selectedDriverId;
    }

    public int getMenuIndex() {
        return menuIndex;
    }

    public void setMenuIndex(int menuIndex) {
        this.menuIndex = menuIndex;
    }

    public void login() throws Exception {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean success = true;

        try {

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {

        }

        if (success) {
            navigate("/first_page");
        }
    }

    public void logout() throws Exception {
        try {
            // Release and close database resources and connections 
            if (connection != null) {
                if (!connection.getAutoCommit()) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }

                connection.close();
                connection = null;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            setPassword(null);
            setUsername(null);

            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getExternalContext().invalidateSession();
        }
    }

    //pages navigations
    public void navigateManageBuses() {
        navigate("/buses/manage_buses.xhtml");
    }

    public void navigateAddEditBus() {
        navigate("/buses/add_edit_bus.xhtml");
    }

    public void navigateAssignDriverToBus() {
        navigate("/buses/assign_driver_to_bus.xhtml");
    }

    public void navigateManageDrivers() {
        navigate("/drivers/manage_drivers.xhtml");
    }

    public void navigateAddEditDriver() {
        navigate("/drivers/add_edit_driver.xhtml");
    }

    public void navigateManageStudents() {
        navigate("/students/manage_students.xhtml");
    }

    public void navigateAddEditStudent() {
        navigate("/students/add_edit_student.xhtml");
    }

    public void navigateManageRoutes() {
        navigate("/routes/manage_routes.xhtml");
    }

    public void navigateAddEditRoutes() {
        navigate("/routes/add_edit_route.xhtml");
    }

    public void navigateManageStops() {
        navigate("/routes/manage_stops.xhtml");
    }

    public void navigateAddEditStop() {
        navigate("/routes/add_edit_stop.xhtml");
    }

    public void navigateManageRouteStops() {
        navigate("/routes/manage_route_stops.xhtml");
    }

    public void navigateAddEditRouteStop() {
        navigate("/routes/add_edit_route_stop.xhtml");
    }

    public void navigateManageRouteSchedules() {
        navigate("/routes/manage_route_schedules.xhtml");
    }

    public void navigateAddEditRouteSchedules() {
        navigate("/routes/add_edit_route_schedules.xhtml");
    }

    public void navigateAssignBusToSchedule() {
        navigate("/routes/assign_bus_to_schedule.xhtml");
    }

    public void navigateBusPosition() {
        navigate("/bus_position.xhtml");
    }

    
    public void navigate(String url) {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (facesContext != null) {
            NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
            navigationHandler.handleNavigation(facesContext, null, url + "?faces-redirect=true");
        }
    }
     //end of pages navigations
}
