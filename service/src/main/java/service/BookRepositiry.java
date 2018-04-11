package service;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepositiry extends JpaRepository<Book, Long> {
}
