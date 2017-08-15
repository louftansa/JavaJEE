package com.mas.bookstore.rest;

import com.mas.bookstore.model.Book;
import com.mas.bookstore.repository.BookRepository;

import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.*;


@Path("/books")
public class BookEndPoint {

    @Inject
    private BookRepository bookRepository;

    @GET
    @Produces(APPLICATION_JSON)
    public Response getBooks() {
        List<Book> books = bookRepository.findAll();
        if(books.isEmpty()){
            Response.noContent().build();
        }
        return Response.ok(books).build();
    }

    @GET
    @Path("/count")
    public Response countBook() {
        Long nbOfBooks = bookRepository.countAll();
        if(nbOfBooks == 0){
            return Response.noContent().build();
        }
        return Response.ok(nbOfBooks).build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/{id : \\d+}")
    public Response getBook(@PathParam("id")@Min(1) Long id) {
        Book book = bookRepository.findBook(id);
        if (book == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }else{
            return Response.ok(book).build();
        }
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response createBook(Book book, @Context UriInfo uriInfo) {
        book = bookRepository.createBook(book);
        URI createdURI = uriInfo.getBaseUriBuilder().path(book.getId().toString()).build();
        return Response.created(createdURI).build();
    }


    @DELETE
    @Path("/{id : \\d+}")
    public Response deleteBook(@PathParam("id")@Min(1) Long id) {
        bookRepository.deleteBook(id);
        return Response.noContent().build();
    }


}
