package pl.edu.pg.eti.kask.labsart.scientist.controller;

import pl.edu.pg.eti.kask.labsart.scientist.dto.ScientistGetResponse;
import pl.edu.pg.eti.kask.labsart.scientist.dto.ScientistPostRequest;
import pl.edu.pg.eti.kask.labsart.scientist.dto.ScientistPutRequest;
import pl.edu.pg.eti.kask.labsart.scientist.dto.ScientistsGetResponse;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;
import pl.edu.pg.eti.kask.labsart.scientist.entity.UserRoles;
import pl.edu.pg.eti.kask.labsart.scientist.service.ScientistService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("")
@RolesAllowed(UserRoles.USER)
public class ScientistController {

    private ScientistService service;

    public ScientistController() {}

    @EJB
    public void setService(ScientistService service) {
        this.service = service;
    }

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser() {
        Optional<Scientist> user = service.findCallerPrincipal();
        if (user.isPresent()) {
            return Response.ok(ScientistGetResponse.entityToDtoMapper().apply(user.get())).build();
        } else {
            return Response.ok(new Object()).build();
        }
    }

    @GET
    @Path("/scientist")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        Response.ResponseBuilder response;

        try {
            response = Response.ok(ScientistsGetResponse.entityToDtoMapper().apply(service.findAll()));
        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong for this request");
        }

        return response.build();
    }

    @POST
    @Path("/scientist")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response postUser(ScientistPostRequest request) {
        Response.ResponseBuilder response;

        try {
            service.create(ScientistPostRequest.dtoToEntityMapper().apply(request));
            response = Response.ok();
        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong for this request");
        }

        return response.build();
    }

    @PUT
    @Path("/scientist/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putUser(@PathParam("login") String login, ScientistPutRequest request) {
        Response.ResponseBuilder response;

        try {
            service.updateNonNullId(ScientistPutRequest.dtoToEntityMapper().apply(request), login);
            response = Response.ok();
        } catch (NullPointerException e) {
            response = Response.status(Response.Status.NOT_FOUND).entity("Scientist not found for login: " + login);
        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong for Scientist: " + login);
        }

        return response.build();
    }

    @GET
    @Path("/scientist/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("login") String login) {
        Optional<Scientist> user = service.find(login);
        if (user.isPresent()) {
            return Response.ok(ScientistGetResponse.entityToDtoMapper().apply(user.get())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("/scientist/{login}")
    @DELETE
    @RolesAllowed(UserRoles.ADMIN)
    public Response deleteUser(@PathParam("login") String login) {
        Optional<Scientist> user = service.find(login);
        if (user.isPresent()) {
            service.delete(user.get());
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
