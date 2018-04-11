package service;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookcatalog")
public class BookcatalogRestController {

    private final BookRepositiry bookRepositiry;
    public BookcatalogRestController(BookRepositiry bookRepositiry) {
        this.bookRepositiry = bookRepositiry;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bookId}")
    public BookResource getBook(@PathVariable("bookId") Long bookId) {
        validateBook(bookId);
        return new BookResource(bookRepositiry.findOne(bookId));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable("bookId") Long bookId, @RequestBody Book input) {
        validateBook(bookId);
        bookRepositiry.save(input);
        Link forNewBook = new BookResource(input).getLink("self");
        return ResponseEntity.created(URI.create(forNewBook.getHref())).build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{bookId}")
    public ResponseEntity<?> createBook(@PathVariable("bookId") Long bookId, @RequestBody Book input) {
        bookRepositiry.save(input);
        Link forNewBook = new BookResource(input).getLink("self");
        return ResponseEntity.created(URI.create(forNewBook.getHref())).build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{bookId}")
    public void deleteBook(@PathVariable("bookId") Long bookId) {
        validateBook(bookId);
        bookRepositiry.delete(bookId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/allBooks")
    public Resources<BookResource> getAllBooks(@PathVariable("bookId") Long bookId) {
        List<BookResource> bookResourceList = bookRepositiry.findAll().stream().map(BookResource::new).collect(Collectors.toList());
        return new Resources<>(bookResourceList);
    }

    private void validateBook(Long bookId){
        if (bookRepositiry.findOne(bookId) == null) throw new BookNotFoundExpeption(String.valueOf(bookId));
    }

}
