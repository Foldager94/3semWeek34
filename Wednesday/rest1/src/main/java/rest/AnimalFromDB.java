package rest;

import com.google.gson.Gson;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import entity.Animal;
import java.util.Random;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.ws.rs.PathParam;


@Path("animals_db")
public class AnimalFromDB {

    @Context
    private UriInfo context;
    
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

    /**
     * Creates a new instance of AnimalFromDB
     */
    public AnimalFromDB() {
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAnimals2() {
        
        return "Nothing here";
    }
    
    @Path("animals")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAnimals() {
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a", Animal.class);
            List<Animal> animals = query.getResultList();
            return new Gson().toJson(animals);
        }finally {
            em.close();
        }
    }
    
    @Path("animalbyid/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAnimal(@PathParam("id") int id){
        EntityManager em = emf.createEntityManager();
        try{
            Query query = em.createQuery("SELECT a FROM Animal a where a.id = :id",Animal.class);
            Animal animal = (Animal) query.setParameter("id", id).getSingleResult();
            return new Gson().toJson(animal);
        }catch(NoResultException e){
            return null;
        }finally{
            em.close();
        } 
    }

    @Path("animalbytype/{type}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAnimal(@PathParam("type") String type){
        EntityManager em = emf.createEntityManager();
        try{
            Query query = em.createQuery("SELECT a FROM Animal a where a.type = :type");
            Animal animal = (Animal) query.setParameter("type", type).getSingleResult();
            return new Gson().toJson(animal);
        }catch(NoResultException e){
            return null;
        }finally{
            em.close();
        } 
    }
    @Path("random_animal")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getRandomAnimal(){
        EntityManager em = emf.createEntityManager();
        try{
            Query queryCount = em.createQuery("Select count(a) from Animal a");
            Long count = (Long)queryCount.getSingleResult();
            int intCount = count.intValue();
            
            Random random = new Random();
            int number = random.nextInt(intCount);
            
            Query query = em.createQuery("SELECT a FROM Animal a where a.id = :id",Animal.class);
            Animal animal = (Animal) query.setParameter("id", number).getSingleResult();
            return new Gson().toJson(animal);
        }catch(NoResultException e){
            return null;
        }finally{
            em.close();
        } 
    }
    
}
