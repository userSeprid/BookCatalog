package com.seprid.beans;


import com.seprid.entity.Book;

import javax.ejb.Stateless;
import javax.json.*;
import javax.json.stream.JsonGenerator;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;


@Stateless
public class BookServiceBean {

    @PersistenceContext(unitName = "myP")
    private EntityManager em;


    public JsonObject getBook(int id) {
        return converterBookToJson(em.find(Book.class, id));
    }


    public void updateBook(JsonObject bookData) {

        Book book = converterJsonToBook(bookData);
        em.merge(book);


    }


    public void createBook(JsonObject bookData) {
        Book book = converterJsonToBook(bookData);
        book.setId(null);
        em.persist(book);
    }


    public void removeBook(JsonObject bookData) {
        Book book = converterJsonToBook(bookData);
        em.remove(em.find(Book.class, book.getId()));
    }

    public JsonObject converterBookToJson(Book book) {
        return Json.createObjectBuilder()
                .add("id", book.getId())
                .add("description", book.getDescription())
                .add("illustrations", book.getIllustrations())
                .add("isbn", book.getIsbn())
                .add("nbOfPages", book.getNbOfPage())
                .add("price", book.getPrice())
                .add("title", book.getTitle())
                .build();
    }

    public Book converterJsonToBook(JsonObject json) {

        Book book = new Book(json.get("description").toString(),
                Boolean.valueOf(json.get("illustrations").toString()),
                json.get("isbn").toString(),
                Integer.parseInt(json.get("nbOfPages").toString()),
                Integer.parseInt(json.get("price").toString()),
                json.get("title").toString());
        book.setId(Integer.parseInt(json.get("id").toString()));
        return book;
    }

    public String getAllBooksTitles() {
        ArrayList<Book> booksList = (ArrayList<Book>) em.createNamedQuery("getAllBooks").getResultList();
        StringWriter stringWriter = new StringWriter();
        for (Book b :
                booksList) {
            stringWriter.append(b.getTitle() + "|");
        }
        return stringWriter.toString();
    }

    public JsonObject getAllBooks() {
        ArrayList<Book> booksList = (ArrayList<Book>) em.createNamedQuery("getAllBooks").getResultList();
        StringWriter stringWriter = new StringWriter();
        JsonGenerator generator = Json.createGenerator(stringWriter);
        generator.writeStartObject();
        for (Book b :
                booksList) {
            generator.writeStartObject();
            generator.write("id", b.getId());
            generator.write("description", b.getDescription());
            generator.write("illustrations", b.getIllustrations());
            generator.write("isbn", b.getIsbn());
            generator.write("nbOfPages", b.getNbOfPage());
            generator.write("price", b.getPrice());
            generator.write("title", b.getTitle());
            generator.writeEnd();
        }
        generator.writeEnd().close();
        JsonObject parsed;
        try (JsonReader reader = Json.createReader(new StringReader(stringWriter.toString()))) {
            parsed = reader.readObject();
        }
        return parsed;
    }
}
