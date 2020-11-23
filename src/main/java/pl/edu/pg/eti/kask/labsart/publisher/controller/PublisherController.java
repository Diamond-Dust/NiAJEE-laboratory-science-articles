package pl.edu.pg.eti.kask.labsart.publisher.controller;

import pl.edu.pg.eti.kask.labsart.publisher.dto.GetPublisherResponse;
import pl.edu.pg.eti.kask.labsart.publisher.dto.GetPublishersResponse;
import pl.edu.pg.eti.kask.labsart.publisher.dto.PostPublisherRequest;
import pl.edu.pg.eti.kask.labsart.publisher.dto.PutPublisherRequest;
import pl.edu.pg.eti.kask.labsart.publisher.entity.Publisher;
import pl.edu.pg.eti.kask.labsart.publisher.service.PublisherService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/publisher")
public class PublisherController {

    private PublisherService service;

    public PublisherController(){}

    @Inject
    public void setService(PublisherService service) {
        this.service = service;
    }

    //-----------------------------------------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPublishers() {
        Response.ResponseBuilder response;

        List<Publisher> publishers = service.findAll();
        GetPublishersResponse publishersResponse = GetPublishersResponse.entityToDtoMapper().apply(publishers);

        response = Response.ok(publishersResponse);

        return response.build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getPublisher(@PathParam("id") Long id) {
        Response.ResponseBuilder response;

        Optional<Publisher> publisher = service.find(id);
        if(publisher.isPresent())
            response = Response.ok(GetPublisherResponse.entityToDtoMapper().apply(publisher.get()));
        else
            response = Response.status(Response.Status.NOT_FOUND).entity("Publisher not found for ID: " + id);

        return response.build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response deletePublisher(@PathParam("id") Long id) {
        Response.ResponseBuilder response;

        try {
            service.delete(id);
            response = Response.ok();
        } catch (NullPointerException e) {
            response = Response.status(Response.Status.NOT_FOUND).entity("Publisher not found for ID: " + id);
        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong for ID: " + id);
        }

        return response.build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response updatePublisher(@PathParam("id") Long id, PutPublisherRequest request) {
        Response.ResponseBuilder response;

        try {
            service.updateNonNullId(PutPublisherRequest.dtoToEntityMapper().apply(request), id);
            response = Response.ok();
        } catch (NullPointerException e) {
            response = Response.status(Response.Status.NOT_FOUND).entity("Article not found for ID: " + id);
        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong for ID: " + id);
        }

        return response.build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response uploadPublisher(PostPublisherRequest request) {
        Response.ResponseBuilder response;

        try {
            service.createWithoutId(PostPublisherRequest.dtoToEntityMapper().apply(request));
            response = Response.ok();
        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong for this request");
        }

        return response.build();
    }
}
