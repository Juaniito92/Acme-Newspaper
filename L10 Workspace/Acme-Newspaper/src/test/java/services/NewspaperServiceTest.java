package services;

import java.util.Collection;

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

}
