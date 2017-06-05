/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientecrudjsf.ejb;

import clientecrudjsf.entity.Customer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Adrián
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "ClienteCRUDJSF-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }
    
    public Integer siguienteId(){
        Query q;
        q = em.createQuery("select max(c.customerId) from Customer c");
        Integer entero = (Integer) q.getSingleResult();
        if (entero == null) {
            entero = 1;
        } else {
            entero += 1;
        }
        return entero; 
    }
    
}
