/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Christoffer
 */
public class EntityTested {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        Customer c1 = new Customer("Ya","Yeet");
        Customer c2 = new Customer("Yaa","Yeet");
        Customer c3 = new Customer("Yaaa","Yeet");
        Customer c4 = new Customer("Yaaaa","Yeet");
        
        
        try {
            em.getTransaction().begin();
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.persist(c4);
            em.getTransaction().commit();
            //Verify that books are managed and has been given a database id
            System.out.println(c1.getCreated());
            System.out.println(c2.getCreated());

        }finally{
            em.close();
        }
    }
    
    
    
    
}
