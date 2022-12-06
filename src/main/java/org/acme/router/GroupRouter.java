package org.acme.router;


import io.agroal.api.AgroalDataSource;
import org.acme.service.GroupService;
import org.acme.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.ArrayList;

@ApplicationScoped

@Path("/group")
public class GroupRouter {
    @Inject
    AgroalDataSource agroalDataSource;

    /**
     * /group/{id} нь хэрэглэгчтжэй холбоотой group  ийн загсаалт өгөх
     * /groupinfo/{id} нь group ийн  мэдээлэл өгөх
     */

    @Path("/groups/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ArrayList<Object> getGroups(@PathParam("id") String id) throws SQLException {
        return GroupService.getGroups(agroalDataSource, id);
    }
    @Path("/groupinfo/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ArrayList<Object> getGroupInfo(@PathParam("id") String id) throws SQLException {
        return GroupService.getGroupInfo(agroalDataSource, id);
    }

}
