package com;


import com.seprid.oldClasses.Book;
import com.seprid.services.BookService;
import com.seprid.oldClasses.PathTemplate;
import com.seprid.oldClasses.SimpleLoginAndPasswordRequest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rest")
public class ApplicationConfig extends Application{
    private final Set<Class<?>> classes;

    public ApplicationConfig() {
        HashSet<Class<?>> c = new HashSet<>();
        c.add(Book.class);
        c.add(BookService.class);
        classes = Collections.unmodifiableSet(c);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}
