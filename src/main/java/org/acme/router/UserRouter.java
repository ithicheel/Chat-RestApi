package org.acme.router;


import io.agroal.api.AgroalDataSource;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.acme.service.UserService;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.ArrayList;


@ApplicationScoped

@Path("/user")
public class UserRouter {
    @Inject
    AgroalDataSource agroalDataSource;

    private static final Logger logger = Logger.getLogger(UserRouter.class);

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
     * GET INFO
     */

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ArrayList<Object> userId(@PathParam("id") String id) throws SQLException {
        return UserService.findById(agroalDataSource, id);
    }
    @Path("/friendlist/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ArrayList<Object> getFriendList(@PathParam("id") String id) throws SQLException {
        return UserService.getFriendList(agroalDataSource, id);
    }




    @Path("/groupAdd/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<JsonObject> addGroupChat(@PathParam("id") String id, @PathParam("userId") String userID) throws SQLException{


        JsonObject jret = new JsonObject();

        try {
            UserService.addGroupChatPeople(agroalDataSource, id);
            jret.put("msg", "success");
            jret.put("code", "000");


        } catch (Exception e){
            logger.infov("error");
            jret.put("msg", "failed");
            jret.put("code", "999");
        }

        return RestResponse.ResponseBuilder.ok(jret).build();
    }

    @Path("/groupRemove/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<JsonObject> removeGroupChat(@PathParam("id") String id){
        JsonObject jret = new JsonObject();

        try{
            UserService.removeGroupChatMember(agroalDataSource, id);
            jret.put("msg", "success");
            jret.put("code", "000");

        } catch (Exception e){

            logger.infov("error");
            jret.put("msg", "failed");
            jret.put("code", "999");
        }


        return RestResponse.ResponseBuilder.ok(jret).build();
    }




    @Path("/groupChatInfo")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<JsonObject> getGroupChatInfo(@PathParam("id") String id) throws SQLException {
        UserService usr = new UserService();
        return usr.getGroupChatInfo(agroalDataSource, id);
    }



    @Path("/groupChatMembers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<JsonObject> getGroupChatMembers(@PathParam("id") String id) throws SQLException {

        UserService usr = new UserService();
        return usr.getGroupChatPeoples(agroalDataSource, id);
    }





}
