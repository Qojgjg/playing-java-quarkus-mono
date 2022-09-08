package inveox.poc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class TestSQL_Lite {

    @Inject
    EntityManager em; 

    @Inject
    PullingService counter;  


    @Path("/all")
    @GET
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public String hello2() {
        
        Query lQuery =   em.createQuery("FROM Category");
        List<Category> result = lQuery.getResultList();
        for (Category c:result){
            System.out.println(c);
        }

        return "Hello from RESTEasy Reactive with counter in "+counter.get();
    }
    
    public static void main(String[] args) {
        connect();
    }    
        
    public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:/Users/sebastianscotti/java_ws/hapi-fhir/mydatabase.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}

