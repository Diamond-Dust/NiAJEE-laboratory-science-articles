package pl.edu.pg.eti.kask.labsart.article.controller;

import pl.edu.pg.eti.kask.labsart.article.dto.GetArticleResponse;
import pl.edu.pg.eti.kask.labsart.article.dto.GetArticlesResponse;
import pl.edu.pg.eti.kask.labsart.article.dto.PostArticleRequest;
import pl.edu.pg.eti.kask.labsart.article.dto.PutArticleRequest;
import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.article.service.ArticleService;
import pl.edu.pg.eti.kask.labsart.scientist.entity.Scientist;
import pl.edu.pg.eti.kask.labsart.scientist.entity.UserRoles;
import pl.edu.pg.eti.kask.labsart.scientist.service.ScientistService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/article")
@RolesAllowed(UserRoles.USER)
public class ArticleController {
    private ArticleService service;
    private ScientistService scientistService;

    public ArticleController(){}

    @Inject
    public void setService(ArticleService service, ScientistService scientistService) {
        this.service = service;
        this.scientistService = scientistService;
    }

    //-----------------------------------------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllArticles() {
        Response.ResponseBuilder response;

        try {
            List<Article> articles;
            if(scientistService.isCaller(UserRoles.ADMIN)) {
                articles = service.findAll();
            } else {
                articles = service.findAllForCaller();
            }
            GetArticlesResponse articlesResponse = GetArticlesResponse.entityToDtoMapper().apply(articles);
            response = Response.ok(articlesResponse);
        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong for this request");
        }

        return response.build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getArticle(@PathParam("id") Long id) {
        Response.ResponseBuilder response;

        try {
            Optional<Article> article;
            if(scientistService.isCaller(UserRoles.ADMIN)) {
                article = service.find(id);
            } else {
                article = service.findForCaller(id);
            }
            if(article.isPresent())
                response = Response.ok(GetArticleResponse.entityToDtoMapper().apply(article.get()));
            else
                response = Response.status(Response.Status.NOT_FOUND).entity("Article not found for ID: " + id);
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
            service.deleteForCaller(id);
            response = Response.ok();
        } catch (EJBException e) {
            Exception ne = (Exception) e.getCause();
            if(ne.getClass().getSimpleName().equals("NullPointerException")) {
                response = Response.status(Response.Status.NOT_FOUND).entity("Article not found for ID: " + id);
            } else {
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong for ID: " + id);
            }
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
            if(scientistService.isCaller(UserRoles.ADMIN)) {
                service.updateNonNullId(PutArticleRequest.dtoToEntityMapper().apply(article), id);
            } else {
                service.updateNonNullIdForCaller(PutArticleRequest.dtoToEntityMapper().apply(article), id);
            }
            response = Response.ok();
        } catch (EJBException e) {
            Exception ne = (Exception) e.getCause();
            if(ne.getClass().getSimpleName().equals("NullPointerException")) {
                response = Response.status(Response.Status.NOT_FOUND).entity("Article not found for ID: " + id);
            } else {
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong for ID: " + id);
            }
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
