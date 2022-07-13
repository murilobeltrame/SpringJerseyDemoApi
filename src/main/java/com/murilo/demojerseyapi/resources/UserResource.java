package com.murilo.demojerseyapi.resources;

import com.murilo.demojerseyapi.models.User;
import com.murilo.demojerseyapi.models.Users;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="users")
@Path("/users")
public class UserResource {
    private static Map<Integer, User> db = new HashMap<>();

    @GET
    @Produces("application/json")
    public Users getAllUser(){
        var users = new Users();
        users.setUsers(new ArrayList<>(db.values()));
        return users;
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createUser(User user) throws URISyntaxException {
        if (user.getFirstName() == null || user.getLastName() == null) {
            return Response.status(400).entity("Please provide mandatory inputs").build();
        }
        user.setId(db.values().size()+1);
        user.setUri("/users/"+user.getId());
        db.put(user.getId(), user);
        return Response.status(201).contentLocation(new URI(user.getUri())).entity(user).build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getUserById(@PathParam("id")int id)throws URISyntaxException {
        var foundUser = db.get(id);
        if (foundUser == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(foundUser).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public Response updateUser(@PathParam("id")int id, User user) throws URISyntaxException {
        var foundUser = db.get(id);
        if (foundUser == null) {
            return Response.status(404).build();
        }
        if (user.getId() != id) {
            return Response.status(400).entity("Identification dont match").build();
        }
        if (user.getFirstName() == null || user.getLastName() == null) {
            return Response.status(400).entity("Please provide mandatory inputs").build();
        }
        foundUser.setFirstName(user.getFirstName());
        foundUser.setLastName(user.getLastName());
        db.put(foundUser.getId(), foundUser);
        return Response.status(204).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id")int id) throws URISyntaxException {
        var foundUser = db.get(id);
        if (foundUser == null) {
            return Response.status(404).build();
        }
        db.remove(id);
        return Response.status(204).build();
    }

    static {
        var john = new User();
        john.setId(1);
        john.setFirstName("John");
        john.setLastName("Wick");
        john.setUri("/users/1");

        var harry = new User();
        harry.setId(2);
        harry.setFirstName("Harry");
        harry.setLastName("Potter");
        harry.setUri("/users/2");

        db.put(john.getId(), john);
        db.put(harry.getId(), harry);
    }
}
