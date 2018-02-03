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
import models.Students;
import daos.StudentsDao;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
/**
 *
 * @author Taha Al-khaffaf
 */
@Named(value = "manageStudentsBean")
@ViewScoped
public class ManageStudentsBean implements Serializable {

    private Students selectedStudent;
    private final StudentsDao studentsDao = new StudentsDao();
    private ArrayList<Students> studentsArray;
    String error_message_header;
    String error_message_content;
    @Inject
    private SessionBean sessionBean;

    public ManageStudentsBean() {
    }

    @PostConstruct
    public void init() {
        try {
            studentsArray = studentsDao.buildStudents();
        } catch (Exception ex) {
            Logger.getLogger(ManageBusesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Students getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Students selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

    public ArrayList<Students> getStudentsArray() {
        return studentsArray;
    }

    public void setStudentsArray(ArrayList<Students> studentsArray) {
        this.studentsArray = studentsArray;
    }

    public void saveSelectedStudentId() {
        sessionBean.setSelectedStudentId(selectedStudent.getStudentId());
    }

    public void deleteSelectedStudent() {
        try {
            studentsDao.deleteStudent(selectedStudent.getStudentId());
            sessionBean.navigateManageStudents();
        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(ManageStudentsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
