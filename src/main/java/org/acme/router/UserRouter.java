package org.acme.router;


import io.agroal.api.AgroalDataSource;
import org.acme.module.Users;
import org.acme.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.ArrayList;

@ApplicationScoped

@Path("/user")
public class UserRouter {
    /**
     *
     */
    @Inject
    AgroalDataSource agroalDataSource;

    /**
     * LOGIN
     */
    @Path("/login/{email}/{pass}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ArrayList<Object> login(@PathParam("email") String email, @PathParam("pass") String pass) throws SQLException {
        return UserService.login(agroalDataSource, email, pass);
    }

    /**
     * GET INFO хэрэглэгчийн мэдээлэл болон хэрэглэгчийн найзуудын мэдээлэл өгөх
     * /user/{id} нь хэрэглэгчийн мэдээлэл өгөх
     * /user/friedlist/{id} нь хэрэглэгчийн найзуудын мэдээлэл өгөх
     */
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ArrayList<Object> userId(@PathParam("id") String id) throws SQLException {
        return UserService.findById(agroalDataSource, id);
    }

    /**
     * Get freindlist
     * @param id хэрэглэгчийн id байна
     * @return хэрэглэгчийн мэдээлэл буцаана.
     * @throws SQLException
     */
    @Path("/friendlist/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ArrayList<Object> getFriendList(@PathParam("id") String id) throws SQLException {
        return UserService.getFriendList(agroalDataSource, id);
    }
}
