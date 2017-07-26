package com.mas.bookstore.repository;

import com.mas.bookstore.model.Book;
import com.mas.bookstore.model.Language;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class BookRepositoryTest {


    @Inject
    private BookRepository bookRepository;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(BookRepository.class)
                .addClass(Book.class)
                .addClass(Language.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");

    }

    @Test
    public void create() {
        assertEquals(Long.valueOf(0), bookRepository.countAll());
        assertEquals(0, bookRepository.findAll().size());

        Book book = new Book("JavaEE", "A java ee book", 2.4f, "14324443", new Date(), 100, "http://nothing.com", Language.ENGLISH);
        book = bookRepository.createBook(book);
        Long bookId = book.getId();
        assertNotNull(bookId);

        Book bookFound = bookRepository.findBook(bookId);

        assertEquals(bookFound.getTitle(), "JavaEE");
        assertEquals(Long.valueOf(1), bookRepository.countAll());
        assertEquals(1, bookRepository.findAll().size());

        bookRepository.deleteBook(bookId);

        assertEquals(Long.valueOf(0), bookRepository.countAll());
        assertEquals(0, bookRepository.findAll().size());
    }

}
