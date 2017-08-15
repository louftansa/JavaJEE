package com.mas.bookstore.rest;

import com.mas.bookstore.model.Book;
import com.mas.bookstore.model.Language;
import com.mas.bookstore.repository.BookRepository;
import com.mas.bookstore.util.IsbnGenerator;
import com.mas.bookstore.util.NumberGenerator;
import com.mas.bookstore.util.TextUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Date;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(Arquillian.class)
@RunAsClient
public class BookEndPointTest {

    @Test
    public void createBook(@ArquillianResteasyResource("api/books") WebTarget webTarget){

        // Test count
        Response response = webTarget.path("count").request().get();
        assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
        response.close();

        // Test find all
        response = webTarget.request(APPLICATION_JSON).get();
        assertEquals(OK.getStatusCode(), response.getStatus());
        response.close();

        // Test creates book
        Book book = new Book("14324443", "JavaEE  book","http://nothing.com", Language.ENGLISH, 2.4f,100,   new Date(),   "A java ee book");
        response = webTarget.request(APPLICATION_JSON).post (Entity.entity(book, APPLICATION_JSON));
        assertEquals(CREATED.getStatusCode(), response.getStatus());
        // Checks the created book
        String location = response.getHeaderString("location");
        assertNotNull(location);
    }

    @Test
    public void deleteBook() throws Exception {
    }

    @Deployment(testable = false)
    public static Archive<?> createDeploymentPackage() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(Book.class)
                .addClass(Language.class)
                .addClass(BookRepository.class)
                .addClass(NumberGenerator.class)
                .addClass(IsbnGenerator.class)
                .addClass(TextUtil.class)
                .addClass(BookEndPoint.class)
                .addClass(JAXRSConfiguration.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
    }


}
