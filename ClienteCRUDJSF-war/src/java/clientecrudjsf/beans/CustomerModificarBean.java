/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientecrudjsf.beans;

import clientecrudjsf.ejb.CustomerFacade;
import clientecrudjsf.ejb.DiscountCodeFacade;
import clientecrudjsf.ejb.MicroMarketFacade;
import clientecrudjsf.entity.Customer;
import clientecrudjsf.entity.DiscountCode;
import clientecrudjsf.entity.MicroMarket;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Adrián
 */
@Named(value = "customerModificarBean")
@RequestScoped
public class CustomerModificarBean {

    @EJB
    private CustomerFacade customerFacade;

    @EJB
    private DiscountCodeFacade discountCodeFacade;

    @EJB
    private MicroMarketFacade microMarketFacade;

    @Inject
    private CustomerBean customerBean;

    /**
     * Creates a new instance of CustomerModificarBean
     */
    public CustomerModificarBean() {
    }

    @PostConstruct
    public void init() {
        this.customer = this.customerBean.getCustomerSeleccionado();
        if (this.customer.getCustomerId() != null) {
            this.zipSeleccionado = this.customer.getZip().getZipCode();
            this.descuentoSeleccionado = this.customer.getDiscountCode().getDiscountCode();
        }
        this.listaSuperMercados = this.microMarketFacade.findAll();
        this.listaCodigosDescuento = this.discountCodeFacade.findAll();
    }

    private Customer customer;
    private List<MicroMarket> listaSuperMercados;
    private List<DiscountCode> listaCodigosDescuento;
    private String zipSeleccionado;
    private String descuentoSeleccionado;

    public List<DiscountCode> getListaCodigosDescuento() {
        return listaCodigosDescuento;
    }

    public void setListaCodigosDescuento(List<DiscountCode> listaCodigosDescuento) {
        this.listaCodigosDescuento = listaCodigosDescuento;
    }

    public String getDescuentoSeleccionado() {
        return descuentoSeleccionado;
    }

    public void setDescuentoSeleccionado(String descuentoSeleccionado) {
        this.descuentoSeleccionado = descuentoSeleccionado;
    }

    public String getZipSeleccionado() {
        return zipSeleccionado;
    }

    public void setZipSeleccionado(String zipSeleccionado) {
        this.zipSeleccionado = zipSeleccionado;
    }

    public List<MicroMarket> getListaSuperMercados() {
        return listaSuperMercados;
    }

    public void setListaSuperMercados(List<MicroMarket> listaSuperMercados) {
        this.listaSuperMercados = listaSuperMercados;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String doGuardar() {
        this.customer.setZip(this.microMarketFacade.find(this.zipSeleccionado));
        this.customer.setDiscountCode(this.discountCodeFacade.find(this.descuentoSeleccionado));
        if (this.customer.getCustomerId() != null) {
            this.customerFacade.edit(customer);
            //this.customerBean.setCustomerSeleccionado(this.customer);
        } else {
            this.customer.setCustomerId(this.customerFacade.siguienteId());
            this.customerFacade.create(customer);
            this.customerBean.init();
        }
        return "listadoCustomer";
    }

}
