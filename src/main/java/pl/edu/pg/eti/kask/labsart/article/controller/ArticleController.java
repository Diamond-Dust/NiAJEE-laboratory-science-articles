package pl.edu.pg.eti.kask.labsart.article.controller;

import pl.edu.pg.eti.kask.labsart.article.dto.GetArticleResponse;
import pl.edu.pg.eti.kask.labsart.article.dto.GetArticlesResponse;
import pl.edu.pg.eti.kask.labsart.article.dto.PostArticleRequest;
import pl.edu.pg.eti.kask.labsart.article.dto.PutArticleRequest;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.article.service.ArticleService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/article")
public class ArticleController {
    private ArticleService service;

    public ArticleController(){}

    @Inject
    public void setService(ArticleService service) {
        this.service = service;
    }

    //-----------------------------------------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllArticles() {
        Response.ResponseBuilder response = Response.ok(GetArticlesResponse.entityToDtoMapper().apply(service.findAll()));
        return response.build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getArticle(@PathParam("id") Long id) {
        Response.ResponseBuilder response;

        Optional<Article> article = service.find(id);
        if(article.isPresent())
            response = Response.ok(GetArticleResponse.entityToDtoMapper().apply(article.get()));
        else
            response = Response.status(Response.Status.NOT_FOUND).entity("Article not found for ID: " + id);

        return response.build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAllArticles() {
        Response.ResponseBuilder response;

        try {
            service.deleteAll();
            response = Response.ok();
        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong for this request");
        }

        return response.build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response deleteArticle(@PathParam("id") Long id) {
        Response.ResponseBuilder response;

        try {
            service.delete(id);
            response = Response.ok();
        } catch (NullPointerException e) {
            response = Response.status(Response.Status.NOT_FOUND).entity("Article not found for ID: " + id);
        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong for ID: " + id);
        }

        return response.build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response updateArticle(@PathParam("id") Long id, PutArticleRequest article) {
        Response.ResponseBuilder response;

        try {
            service.updateNonNullId(PutArticleRequest.dtoToEntityMapper().apply(article), id);
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
    public Response uploadArticle(PostArticleRequest article) {
        Response.ResponseBuilder response;

        try {
            service.createWithoutId(PostArticleRequest.dtoToEntityMapper().apply(article));
            response = Response.ok();
        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong for this request");
        }

        return response.build();
    }
}
