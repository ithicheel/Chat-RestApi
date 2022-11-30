package org.acme;

import io.agroal.api.AgroalDataSource;
import org.acme.module.Book;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@Path("/hello")
public class GreetingResource {

    @Inject
    AgroalDataSource agroalDataSource;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Book> hello() throws SQLException {
        Connection con = agroalDataSource.getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from javalibrary.book");
        ArrayList<Book> books = new ArrayList<>();
        System.out.println(resultSet);
        while (resultSet.next()){
            Book book = new Book(
                    resultSet.getString("book_id"),
                    resultSet.getString("book_title"),
                    resultSet.getString("book_date")
            );
            books.add(book);

        }

        return books;
    }
    @Path("/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Book> gg(@PathParam("id") String id) throws SQLException {
        Connection con = agroalDataSource.getConnection();
        Statement statement = con.createStatement();
        ArrayList<Book> books = new ArrayList<>();
        ResultSet rs = statement.executeQuery("select * from javalibrary.book where book_id=" + id);
        System.out.println("select * from javalibrary.book where book_id=" + id);
        while (rs.next()){
            Book book = new Book(
                    rs.getString("book_id"),
                    rs.getString("book_title"),
                    rs.getString("book_date")
            );
            books.add(book);
        }
        return books;
    }
}