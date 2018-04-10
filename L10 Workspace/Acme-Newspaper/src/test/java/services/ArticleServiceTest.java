package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Article;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ArticleServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ArticleService articleService;

	// Tests ------------------------------------------------------------------

	@Test
	public void driver() {
		final Object testingCreateAndEditData[][] = {
				
				// Casos positivos
				{ "user1", "newspaper3", false, null },
				{ "user2", "newspaper3", false, null },
				{ "user3", "newspaper3", false, null },
				{ "user1", "newspaper7", false, null },
				{ "user2", "newspaper7", false, null },
				// Casos negativos
				{ null, "newspaper3", false, IllegalArgumentException.class },		// Un anonimo no puede crear un articulo
				{ "user1", "newspaper3", false, IllegalArgumentException.class },	// No se puede crear un artículo para un periódico pasado
				{ "user2", "newspaperTest", false, NumberFormatException.class },	// No se puede crear un artículo para un pediódico que no existe
				{ "user3", "newspaper3", true, IllegalArgumentException.class },	// No se puede editar un artículo final
				{ "admin", "newspaper7", false, IllegalArgumentException.class },	// Un admin no puede crear un artículo				
		};

		for (int i = 0; i < testingCreateAndEditData.length; i++)
			this.templateCreateAndEdit((String) testingCreateAndEditData[i][0],
					(String) testingCreateAndEditData[i][1],
					(boolean) testingCreateAndEditData[i][2],
					(Class<?>) testingCreateAndEditData[i][3]);

	}

	// Ancillary methods ------------------------------------------------------

	protected void templateCreateAndEdit(String authenticate, String newspaperBeanName, boolean isFalse, Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			int newspaperId = super.getEntityId(newspaperBeanName);
			super.authenticate(authenticate);
			Article article = articleService.create(newspaperId);
			article.setTitle("Article title");
			article.setSummary("Article summary");
			article.setBody("Article body");
			article.setPictures("https://cdn1.iconfinder.com/data/icons/social-media-3/512/615556-Pencil_Document-256.png\r\nhttps://cdn1.iconfinder.com/data/icons/social-media-3/512/615556-Pencil_Document-256.png");
			article.setIsFinal(isFalse);
			Article saved = articleService.save(article);
			Article article2 = articleService.findOneToEdit(saved.getId());
			article2.setTitle("Article title 2");
			articleService.save(article2);
			articleService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			articleService.flush();
		}

		this.checkExceptions(expected, caught);
	}
	
}
