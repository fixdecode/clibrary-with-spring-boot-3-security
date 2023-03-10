package com.dailycodework.clibrary.book;

import com.dailycodework.clibrary.exception.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {
    private final BookRepository bookRepository;

    @Override
    public List<BookRecord> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(book -> new BookRecord(
                book.getTitle(), book.getAuthor(), book.getEdition(), book.getISBN()))
                .collect(Collectors.toList());
    }

    @Override
    public Book add(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> findById(Long bookId) {
        return Optional.ofNullable(bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("No book found with the id : " + bookId)));
    }

    @Override
    public void delete(Long bookId) {
        Optional<Book> theBook = findById(bookId);
        theBook.ifPresent(book -> bookRepository.deleteById(book.getId()));
    }

    @Override
    public Book update(Book book) {
        return bookRepository.save(book);
    }
}
