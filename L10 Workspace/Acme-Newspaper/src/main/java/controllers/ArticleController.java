package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.NewspaperService;
import domain.Article;

@Controller
@RequestMapping("/article")
public class ArticleController extends AbstractController{
	
	// Services --------------------------------
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private NewspaperService newspaperService;
	
	// Constructors ----------------------------
	public ArticleController(){
		super();
	}
	
	// Listing ----------------------------------
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list(@RequestParam(required=false) Integer newspaperId, 
							@RequestParam(required=false) String keyword){
		ModelAndView res;
		Date actual = new Date(System.currentTimeMillis()-1); 
		Collection<Article> articles;
		Collection<Article> articlesToShow = new ArrayList<Article>();
		
		if(newspaperId!=null){
			articles = this.newspaperService.findOne(newspaperId).getArticles();
			for(Article a : articles){
				// Muestra los articulos finales y cuya fecha sea anterior o igual al momento actual
				if(a.getIsFinal() && 
						(a.getPublicationMoment().before(actual) || a.getPublicationMoment().equals(actual))){
					articlesToShow.add(a);
				}
				
			}
		}
		if(keyword!=null){
			articles = this.articleService.findAll();
			for(Article a : articles){
				if(a.getIsFinal() && 
						(a.getPublicationMoment().before(actual) || a.getPublicationMoment().equals(actual))){
					// Muestra los articulos que contienen la cadena de texto en el titulo, resumen o cuerpo
					if(a.getTitle().contains(keyword)||a.getSummary().contains(keyword)||a.getBody().contains(keyword)){
						articlesToShow.add(a);
					}
				}
			}
		}
		
		res = new ModelAndView("article/list");
		res.addObject("article",articlesToShow);
		res.addObject("requestURI", "article/list.do");
		
		return res;
	}
	
	// Display ----------------------------------
	@RequestMapping(value="/display", method=RequestMethod.GET)
	public ModelAndView display(@RequestParam final int articleId){
		ModelAndView res;
		Article article;
		
		article = this.articleService.findOne(articleId);
		
		res = new ModelAndView("article/display");
		res.addObject("article",article);
		res.addObject("requestURI","article/display.do");
		
		return res;
	}
	
	// Searching --------------------------------

}
