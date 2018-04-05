
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import services.UserService;
import domain.Chirp;
import domain.User;
import forms.UserForm;

@Controller
@RequestMapping("/chirp")
public class ChirpController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private UserService		userService;

	@Autowired
	private ChirpService	chirpService;


	// Constructors ---------------------------------------------------------

	public ChirpController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Chirp> chirps;

		chirps = this.chirpService.findAll();

		result = new ModelAndView("chirp/list");
		result.addObject("chirps", chirps);
		result.addObject("requestURI", "chirp/list.do");

		return result;
	}

	@RequestMapping(value = "display", method = RequestMethod.GET)
	public ModelAndView listUser(@RequestParam final int chirpId) {
		ModelAndView result;
		Chirp chirp;

		chirp = this.chirpService.findOne(chirpId);

		result = new ModelAndView("user/display");
		result.addObject("chirp", chirp);
		result.addObject("requestURI", "chirp/display.do");

		return result;
	}

	// Editing ---------------------------------------------------------------

	@RequestMapping(value = "/user/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		User user;
		UserForm editUserForm;

		user = this.userService.findByPrincipal();
		editUserForm = this.userService.construct(user);

		result = this.createEditModelAndViewEdit(editUserForm);

		return result;
	}

	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editSave(@Valid final UserForm userForm, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors())
			res = this.createEditModelAndViewEdit(userForm, "user.params.error");
		else
			try {
				final User user = this.userService.reconstruct(userForm, binding);
				this.userService.save(user);
				res = new ModelAndView("redirect:../../");
			} catch (final Throwable oops) {
				res = this.createEditModelAndViewEdit(userForm, "user.commit.error");
			}
		System.out.println(binding);

		return res;
	}

	// Registering ----------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		UserForm userForm;

		userForm = new UserForm();
		res = this.createEditModelAndView(userForm);

		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final UserForm userForm, final BindingResult binding) {
		ModelAndView res;
		User user;

		if (binding.hasErrors())
			res = this.createEditModelAndView(userForm, "user.params.error");
		else if (!userForm.getRepeatPassword().equals(userForm.getPassword()))
			res = this.createEditModelAndView(userForm, "user.commit.errorPassword");
		else if (userForm.getTermsAndConditions() == false)
			res = this.createEditModelAndView(userForm, "user.params.errorTerms");
		else
			try {
				user = this.userService.reconstruct(userForm, binding);
				this.userService.save(user);
				res = new ModelAndView("redirect:../");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(userForm, "user.commit.error");
			}

		return res;
	}

	protected ModelAndView createEditModelAndView(final UserForm userForm) {
		ModelAndView result;

		result = this.createEditModelAndView(userForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final UserForm userForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("user/register");
		result.addObject("userForm", userForm);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createEditModelAndViewEdit(final UserForm userForm) {
		ModelAndView result;

		result = this.createEditModelAndViewEdit(userForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewEdit(final UserForm userForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("user/edit");
		result.addObject("userForm", userForm);
		result.addObject("message", message);

		return result;
	}
}