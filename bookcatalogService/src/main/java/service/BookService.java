package service;


import com.seprid.beans.BookServiceBean;

import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/book")
public class BookService {

    @EJB
    BookServiceBean serviceBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getBook(@QueryParam("Id") int id) {

        return serviceBean.getBook(id);
    }

    @PUT
    public void updateBook(JsonObject updatedBook) {

        serviceBean.updateBook(updatedBook);
    }

    @POST
    public void createBook(JsonObject newBook) {

        serviceBean.createBook(newBook);
    }

    @POST
    @Path("/deleteBook")
    public void deleteBook(JsonObject bookToDelete) {
        serviceBean.removeBook(bookToDelete);
    }

    @GET
    @Path("/allBooks")
    public JsonObject getAllBooks() {
        return serviceBean.getAllBooks();
    }
}
