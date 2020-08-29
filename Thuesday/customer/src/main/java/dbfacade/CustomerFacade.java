/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbfacade;

import entity.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Christoffer
 */
public class CustomerFacade {
    
    private static EntityManagerFactory emf;
    private static CustomerFacade instance;
    
    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("pu");
        CustomerFacade facade = CustomerFacade.getCustomerFacade(emf);

        EntityManager em = emf.createEntityManager();
        
        
        //add customer to DB
        facade.addCustomer("Henrik","nej");
        facade.addCustomer("Niels","har");
        facade.addCustomer("Lars","har");
        facade.addCustomer("Lasse","ja");
        facade.addCustomer("Lasse","har");
      
        
        
        //Number of customers in DB
        System.out.println(facade.getNumberOfCustomer());
        
        
        // List of customers with the lastname "har"
        List<Customer> list = facade.findByLastName("har");
        for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i).toString());
            
        }
        //findbyid id number 1
        Customer c21 = facade.findByID(1L);
        System.out.println(c21.toString());
        
        
        
        
        
    }


    private CustomerFacade() {}

    public static CustomerFacade getCustomerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CustomerFacade();
        }
        return instance;
    }
    
    
    public Customer findByID(Long id){
         EntityManager em = emf.createEntityManager();
        try{
            Customer c = em.find(Customer.class,id);
            return c;
        }finally {
            em.close();
        }
    }
    
    public List<Customer> findByLastName(String name){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Customer> query = em.createQuery("Select c FROM Customer c WHERE c.lastName = :name",Customer.class);
            return query.setParameter("name", name).getResultList();
        }finally{
            em.close();
        }
        
        
        
    }
    
    public Long getNumberOfCustomer(){
        EntityManager em = emf.createEntityManager();
        try{
            Query q = em.createQuery("SELECT COUNT(c) FROM Customer c");
            Long result = (Long) q.getSingleResult();
            return result;
        }finally{
            em.close();
        }

    }
    
    public List<Customer> getAllCustomer(){
         EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Customer> query;
             query = em.createQuery("Select p from Customer p",Customer.class);
            return query.getResultList();
        }finally {
            em.close();
        }
    }
    
    public Customer addCustomer(String first, String last){
        Customer c = new Customer(first, last);
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
            return c;
        }finally {
            em.close();
        }
    }

}
