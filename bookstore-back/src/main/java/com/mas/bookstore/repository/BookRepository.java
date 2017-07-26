package com.mas.bookstore.repository;

import com.mas.bookstore.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.*;

@Transactional(SUPPORTS)
public class BookRepository {

    @PersistenceContext(unitName = "bookStorePU")
    private EntityManager em;

    public List<Book> findAll(){
       TypedQuery<Book>  query = em.createQuery("SELECT b from Book b order by b.title desc", Book.class);
       return query.getResultList();
    }
    public Long countAll(){
        TypedQuery<Long>  query = em.createQuery("SELECT count(b) from Book b", Long.class);
       return query.getSingleResult();
    }

    public Book findBook(Long id){
       return em.find(Book.class, id);

    }

    @Transactional(REQUIRED)
    public Book createBook(Book book){
        em.persist(book);
        return book;
    }

    @Transactional(REQUIRED)
    public void deleteBook(Long id){
        em.remove(em.getReference(Book.class, id));
    }
}
