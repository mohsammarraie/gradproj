/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author MOH
 */
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
 
@ManagedBean
public class SelectBooleanView {
  
    private boolean value;
 

 
    public boolean isValue() {
        return value;
    }
 
    public void setValue(boolean value) {
        this.value = value;
    }
     
    public void addMessage() {
        String summary = value ? "Checked" : "Unchecked";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }
}