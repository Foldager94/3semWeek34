package dbfacade;

import entity.Book;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Christoffer
 */
public class BookFacade {
        private static EntityManagerFactory emf;
    private static BookFacade instance;

    public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");      
    BookFacade facade = BookFacade.getBookFacade(emf);
    Book b1 = facade.addBook("Author 1");
    Book b2 = facade.addBook("Author 2");
    //Find book by ID
    System.out.println("Book1: "+facade.findBook(b1.getId()).getAuthor());
    System.out.println("Book2: "+facade.findBook(b2.getId()).getAuthor());
    //Find all books
    System.out.println("Number of books: "+facade.getAllBooks().size());
    
    }
    
    
    
    private BookFacade() {}
    
    
    // Singleton metode der tjekker om BookFacade er instancieret.
    public static BookFacade getBookFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BookFacade();
        }
        return instance;
    }
    
    //Tilføjer en bog til databasen. persist(object) er hvad der sendes. Book sendes retur med id tilføjet fra DB
    public Book addBook(String author){
        Book book = new Book(author);
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
            return book;
        }finally {
            em.close();
        }
    }
    
    //Finder en bog ved hjælp af et Long id i databasen. Bogen sendes retur
    public Book findBook(Long id){
         EntityManager em = emf.createEntityManager();
        try{
            Book book = em.find(Book.class,id);
            return book;
        }finally {
            em.close();
        }
    }
    
    //Laver en liste over alle bøger i databasen. Listen sendes retur
    public List<Book> getAllBooks(){
         EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Book> query = 
                       em.createQuery("Select book from Book book",Book.class);
            return query.getResultList();
        }finally {
            em.close();
        }
    }

}
