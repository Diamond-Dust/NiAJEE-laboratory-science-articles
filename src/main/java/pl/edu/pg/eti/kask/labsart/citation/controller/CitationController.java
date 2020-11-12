package pl.edu.pg.eti.kask.labsart.citation.controller;

import pl.edu.pg.eti.kask.labsart.article.dto.PostArticleRequest;
import pl.edu.pg.eti.kask.labsart.citation.dto.GetCitationResponse;
import pl.edu.pg.eti.kask.labsart.citation.dto.GetCitationsResponse;
import pl.edu.pg.eti.kask.labsart.citation.dto.PostCitationRequest;
import pl.edu.pg.eti.kask.labsart.citation.dto.PutCitationRequest;
import pl.edu.pg.eti.kask.labsart.citation.entity.Citation;
import pl.edu.pg.eti.kask.labsart.citation.service.CitationService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/article/{articleId}/citation")
public class CitationController {
    private CitationService service;

    public CitationController(){}

    @Inject
    public void setService(CitationService service) {
        this.service = service;
    }

    //-----------------------------------------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCitations(@PathParam("articleId") Long articleId) {
        Response.ResponseBuilder response = Response.ok(GetCitationsResponse.entityToDtoMapper().apply(service.findAllForArticle(articleId)));
        return response.build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getCitation(@PathParam("articleId") Long articleId, @PathParam("id") Long id) {
        Response.ResponseBuilder response;

        Optional<Citation> citation = service.find(id);
        if(citation.isPresent())
            response = Response.ok(GetCitationResponse.entityToDtoMapper().apply(citation.get()));
        else
            response = Response.status(Response.Status.NOT_FOUND).entity("Citation not found for ID: " + id);

        return response.build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAllCitations(@PathParam("articleId") Long articleId) {
        Response.ResponseBuilder response;

        try {
            service.deleteAllForArticle(articleId);
            response = Response.ok();
        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong for this request");
        }

        return response.build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response deleteCitation(@PathParam("articleId") Long articleId, @PathParam("id") Long id) {
        Response.ResponseBuilder response;

        try {
            service.deleteCitationForArticle(id, articleId);
            response = Response.ok();
        } catch (NullPointerException e) {
            response = Response.status(Response.Status.NOT_FOUND).entity("Citation not found for ID: " + id);
        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong for ID: " + id);
        }

        return response.build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response updateCitation(@PathParam("articleId") Long articleId, @PathParam("id") Long id, PutCitationRequest citation) {
        Response.ResponseBuilder response;

        try {
            service.updateNonNullId(PutCitationRequest.dtoToEntityMapper().apply(citation), id, articleId);
            response = Response.ok();
        } catch (NullPointerException e) {
            response = Response.status(Response.Status.NOT_FOUND).entity("Citation not found for ID: " + id);
        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong for ID: " + id);
        }

        return response.build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response uploadCitation(@PathParam("articleId") Long articleId, PostCitationRequest citation) {
        Response.ResponseBuilder response;

        try {
            service.createWithoutId(PostCitationRequest.dtoToEntityMapper().apply(citation), articleId);
            response = Response.ok();
        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong for this request");
        }

        return response.build();
    }
}
