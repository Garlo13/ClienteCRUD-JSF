/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientecrudjsf.beans;

import clientecrudjsf.ejb.CustomerFacade;
import clientecrudjsf.entity.Customer;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Adrián
 */
@Named(value = "CustomerBean")
@SessionScoped
public class CustomerBean implements Serializable{

    @EJB
    private CustomerFacade customerFacade;
    
    /**
     * Creates a new instance of NewJSFManagedBean
     */
    public CustomerBean() {
    }
    
    @PostConstruct
    public void init(){
        listaCustomer = this.customerFacade.findAll();
    }
    
    private List<Customer> listaCustomer;
    private Customer customerSeleccionado;

    public Customer getCustomerSeleccionado() {
        return customerSeleccionado;
    }

    public void setCustomerSeleccionado(Customer customerSeleccionado) {
        this.customerSeleccionado = customerSeleccionado;
    }

    public List<Customer> getListaCustomer() {
        return listaCustomer;
    }

    public void setListaCustomer(List<Customer> listaCustomer) {
        this.listaCustomer = listaCustomer;
    }
    
    public String doEditar(Customer customer){
        this.customerSeleccionado = customer;
        return "customereditar";
    }
    
    public String doNuevo(){
        Customer nuevoCustomer = new Customer();
        this.customerSeleccionado = nuevoCustomer;
        return "customereditar";
    }
    
    public String doBorrar(Customer customer){
        this.customerFacade.remove(customer);
        this.init();
        return "listadoCustomer";
    }
    
}
