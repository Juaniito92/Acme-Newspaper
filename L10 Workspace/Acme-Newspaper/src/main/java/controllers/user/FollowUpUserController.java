package controllers.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.FollowUpService;
import services.UserService;
import controllers.AbstractController;
import domain.Article;
import domain.FollowUp;
import domain.Newspaper;
import domain.User;
import forms.ArticleForm;
import forms.FollowUpForm;

@Controller
@RequestMapping("/followUp/user")
public class FollowUpUserController extends AbstractController {
	// Services -------------------------------------------------------------

	@Autowired
	private FollowUpService followUpService;

	@Autowired
	private UserService userService;

	@Autowired
	private ArticleService articleService;

	// Constructors ---------------------------------------------------------

	public FollowUpUserController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int articleId) {
		ModelAndView result;
		Collection<FollowUp> followUps;

		followUps = followUpService.findFollowUpsByArticle(articleId);

		result = new ModelAndView("followUp/list");
		result.addObject("followUp", followUps);
		result.addObject("requestURI", "followUp/user/list.do");

		return result;
	}

	// Create ---------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView create(final int articleId) {
		ModelAndView res;
		FollowUp followUp= new FollowUp();
		FollowUpForm followUpForm= new FollowUpForm();
		
		followUpForm.setArticleId(articleId);
		followUpForm = this.followUpService.construct(followUp);
		
		res = this.createEditModelAndView(followUpForm);
		return res;
		}
	
	// Save ----------------------------------------
	
		@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
		public ModelAndView save(final FollowUpForm followUpForm, final BindingResult binding){
			ModelAndView res;
			
			if(binding.hasErrors()){
				res= this.createEditModelAndView(followUpForm, "followUp.params.error");
			}else
				try{
					FollowUp followUp = this.followUpService.reconstruct(followUpForm, binding);
					this.followUpService.save(followUp);
					Integer id = followUp.getArticle().getId();
					
					res = new ModelAndView("redirect:/followUp/user/list.do?articleId="+id);
				
				}catch (final Throwable oops) {
					res = this.createEditModelAndView(followUpForm, "followUp.commit.error");
				}
			
			return res;
		}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(
			final FollowUpForm followUpForm) {

		ModelAndView result;

		result = this.createEditModelAndView(followUpForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(
			final FollowUpForm followUpForm, final String messageCode) {

		ModelAndView result;
		User principal = this.userService.findByPrincipal();
		result = new ModelAndView("followUp/edit");
		
		int artilceId = followUpForm.getArticleId();
		Article article= this.articleService.findOne(artilceId);
		Collection<Article> articles = new ArrayList<Article>();
		articles.add(article);
		
		result.addObject("followUpForm", followUpForm);
		result.addObject("article",articles);
		result.addObject("message", messageCode);
		result.addObject("requestURI","followUp/user/edit.do");

		return result;
	}

}
