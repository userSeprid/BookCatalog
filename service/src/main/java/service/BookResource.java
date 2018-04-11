package service;

import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class BookResource extends ResourceSupport {

    private final Book book;

    public BookResource(Book book) {
        this.book = book;

        this.add(linkTo(BookcatalogRestController.class).withRel("bookcatalog"));
        this.add(linkTo(methodOn(BookcatalogRestController.class).getBook(book.getId())).withSelfRel());

    }

    public Book getBook() {
        return book;
    }
}
