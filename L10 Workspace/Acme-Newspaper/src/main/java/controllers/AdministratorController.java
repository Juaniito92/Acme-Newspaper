/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdminService;
import domain.Newspaper;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdminService	adminService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/action-1")
	public ModelAndView action1() {
		ModelAndView result;

		result = new ModelAndView("administrator/action-1");

		return result;
	}

	// Action-2 ---------------------------------------------------------------

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("administrator/action-2");

		return result;
	}

	// display
	// --------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = new ModelAndView("administrator/display");

		Object avgSqtrUser[];
		Object avgSqtrArticlesByWriter[];
		Object avgSqtrArticlesByNewspaper[];
		Collection<Newspaper> newspapersMoreAverage;
		Collection<Newspaper> newspapersFewerAverage;
		Double ratioUserCreatedNewspaper;
		Double ratioUserWrittenArticle;

		avgSqtrUser = this.adminService.avgSqtrUser();
		avgSqtrArticlesByWriter = this.adminService.avgSqtrArticlesByWriter();
		avgSqtrArticlesByNewspaper = this.adminService.avgSqtrArticlesByNewspaper();
		newspapersMoreAverage = this.adminService.newspapersMoreAverage();
		newspapersFewerAverage = this.adminService.newspapersFewerAverage();
		ratioUserCreatedNewspaper = this.adminService.ratioUserCreatedNewspaper();
		ratioUserWrittenArticle = this.adminService.ratioUserWrittenArticle();

		result.addObject("avgSqtrUser", avgSqtrUser);
		result.addObject("avgSqtrArticlesByWriter", avgSqtrArticlesByWriter);
		result.addObject("avgSqtrArticlesByNewspaper", avgSqtrArticlesByNewspaper);
		result.addObject("newspapersMoreAverage", newspapersMoreAverage);
		result.addObject("newspapersFewerAverage", newspapersFewerAverage);
		result.addObject("ratioUserCreatedNewspaper", ratioUserCreatedNewspaper);
		result.addObject("ratioUserWrittenArticle", ratioUserWrittenArticle);

		return result;
	}

}
