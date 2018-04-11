package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Newspaper;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class NewspaperServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private NewspaperService newspaperService;
	
	@Autowired
	private ArticleService articleService;
	
	// Tests ------------------------------------------------------------------

	/*
	 * Caso de uso 4.2: List the newspapers that are published and browse their
	 * articles.
	 */

	@Test
	public void driverList() {
		final Object testingListData[][] = {

		// Casos positivos
		{ null, null } };

		for (int i = 0; i < testingListData.length; i++)
			this.templateList((String) testingListData[i][0],
					(Class<?>) testingListData[i][1]);

	}

	protected void templateList(String authenticate, Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(authenticate);
			Collection<Newspaper> newspapers = newspaperService.findAvalibleNewspapers();
			if(!newspapers.isEmpty()){
				Newspaper newspaper = newspaperService.findOne(newspapers.iterator().next().getId());
				if(!newspaper.getArticles().isEmpty()){
					articleService.findOne(newspaper.getArticles().iterator().next().getId());
				}
			}
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	//6.1 Create a newspaper
	@Test
	public void driverCreateAndSaveNewspaper() {
		final Object testingEditData[][] = {

				// Casos positivos
				{ "user1", "titulo", "descripción", "18/10/2019", "https://iconfinder.com/data.png", false, null },
				// Casos negativos
				//Se intenta crear un newspaper sin registrar
				{ null, "titulo", "descripción", "18/10/2019", "https://iconfinder.com/data.png", false, NullPointerException.class },
				//Se intenta crear un newspaper con admin
				{ "admin", "titulo", "descripción", "18/10/2019", "https://iconfinder.com/data.png", false, NullPointerException.class }, };

		for (int i = 0; i < testingEditData.length; i++)
			this.templateCreateAndSaveNewspaper((String) testingEditData[i][0],
					(String) testingEditData[i][1],
					(String) testingEditData[i][2],
					(String) testingEditData[i][3],
					(String) testingEditData[i][4],
					(boolean) testingEditData[i][5],
					(Class<?>) testingEditData[i][6]);
	}
	
	
	private void templateCreateAndSaveNewspaper(String userName, String title, String description, String publicationDate, String picture, boolean isPrivate, Class<?> expected) {

		Class<?> caught;
		caught = null;
		Newspaper newspaper;
		
		Date date = new Date();
		
		SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
		try{
			date = pattern.parse(publicationDate);
		}catch(ParseException e){
			e.getClass();
		}
		
		try{
			super.authenticate(userName);
			newspaper = this.newspaperService.create();
			newspaper.setTitle(title);
			newspaper.setDescription(description);
			newspaper.setPublicationDate(date);
			newspaper.setPicture(picture);
			newspaper.setIsPrivate(isPrivate);
			this.newspaperService.save(newspaper);
			this.newspaperService.flush();
			this.unauthenticate();
		}catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
		
	}
	

}
