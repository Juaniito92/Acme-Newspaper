package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Newspaper;

@Repository
public interface NewspaperRepository extends JpaRepository<Newspaper, Integer>{

	@Query("select n from Newspaper n where n.publicationDate <= current_date")
	Collection<Newspaper> findPastNewspapers();
	
	@Query("select n from Newspaper n join n.articles a where n.publicationDate <= current_date and a.isFinal = false")
	Collection<Newspaper> findPastNewspapersWithNonFinalArticle();
	
	@Query("select n from Newspaper n where(n.title LIKE %?1% or n.description LIKE %?1%) and n.publicationDate <= current_date")
	Collection<Newspaper> findPerKeyword(String keyword);
	
	@Query("select n from Newspaper n where n.publisher.id = ?1")
	Collection<Newspaper> findByPublisherId(int publisherId);
	
	@Query("select n from Newspaper n where n.publicationDate > current_date")
	Collection<Newspaper> findNonPublished();
	
}
