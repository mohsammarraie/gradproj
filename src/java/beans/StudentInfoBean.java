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
import models.Student;
import daos.StudentsDao;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
/**
 *
 * @author MOH
 */
@Named(value = "studentInfoBean")
@ViewScoped
public class StudentInfoBean implements Serializable{
    private final StudentsDao studentsDao = new StudentsDao();
    private ArrayList<Student> studentInfoArray;
    private String studentId;

    public StudentInfoBean() {
        
    }
    
    
        @Inject
    private SessionBean sessionBean;
        
      @PostConstruct
    public void init() {
        try {
            studentId = sessionBean.getStudentUserId();

            studentInfoArray = studentsDao.buildStudentInfo(studentId);
        } catch (Exception ex) {
         
            Logger.getLogger(ManageStudentsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public ArrayList<Student> getStudentInfoArray() {
        return studentInfoArray;
    }

    public void setStudentInfoArray(ArrayList<Student> studentInfoArray) {
        this.studentInfoArray = studentInfoArray;
    }
    
    
}
