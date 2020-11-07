package pl.edu.pg.eti.kask.labsart.article.converters;

import pl.edu.pg.eti.kask.labsart.article.entity.Article;
import pl.edu.pg.eti.kask.labsart.citation.view.CitationCreate;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.List;
import java.util.stream.Collectors;

@FacesConverter(value = "articleCreateConverter")
public class ArticleCreateConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String articleTitle) {
        ValueExpression vex =
                ctx.getApplication().getExpressionFactory()
                        .createValueExpression(ctx.getELContext(),
                                "#{citationCreate}", CitationCreate.class);

        CitationCreate citation = (CitationCreate)vex.getValue(ctx.getELContext());
        List<Article> articles =  citation.getArticles().stream().filter(c -> c.getTitle().equals(articleTitle)).collect(Collectors.toList());
        return articles.size() > 0 ? articles.get(0) : null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object article) {
        return article == null ? "" : ((Article)article).getTitle();
    }

}
