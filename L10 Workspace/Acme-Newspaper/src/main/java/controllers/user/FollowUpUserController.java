package controllers.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FollowUpService;
import controllers.AbstractController;
import domain.FollowUp;

@Controller
@RequestMapping("/followUp/user")
public class FollowUpUserController  extends AbstractController {
	// Services -------------------------------------------------------------

		@Autowired
		private FollowUpService	followUpService;
		
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
}
