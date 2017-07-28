package com.mas.bookstore.repository;

import com.mas.bookstore.model.Book;
import com.mas.bookstore.util.NumberGenerator;
import com.mas.bookstore.util.TextUtil;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import static javax.transaction.Transactional.TxType.*;

@Transactional(SUPPORTS)
public class BookRepository {

    @PersistenceContext(unitName = "bookStorePU")
    private EntityManager em;

    @Inject
    private TextUtil textUtil;

    @Inject
    private NumberGenerator numberGenerator;

    public List<Book> findAll(){
       TypedQuery<Book>  query = em.createQuery("SELECT b from Book b order by b.title desc", Book.class);
       return query.getResultList();
    }
    public Long countAll(){
        TypedQuery<Long>  query = em.createQuery("SELECT count(b) from Book b", Long.class);
       return query.getSingleResult();
    }

    public Book findBook(@NotNull Long id) {
       return em.find(Book.class, id);

    }

    @Transactional(REQUIRED)
    public Book createBook(@NotNull Book book) {
        book.setTitle(textUtil.sanitize(book.getTitle()));
        book.setIsbn(numberGenerator.generateIsbnNumber());
        em.persist(book);
        return book;
    }

    @Transactional(REQUIRED)
    public void deleteBook(@NotNull Long id) {
        em.remove(em.getReference(Book.class, id));
    }
}
