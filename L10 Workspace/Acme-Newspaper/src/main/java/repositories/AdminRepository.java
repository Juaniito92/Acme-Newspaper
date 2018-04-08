
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Admin;
import domain.Newspaper;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	@Query("select a from Admin a where a.userAccount.id=?1")
	Admin findAdminByUserAccountId(int uA);

	// C-1
	@Query("select avg(u.newspapers.size), sqrt(sum(u.newspapers.size*u.newspapers.size)/count(u.newspapers.size)-(avg(u.newspapers.size)*avg(u.newspapers.size))) from User u")
	Object[] avgSqtrUser();

	// C-2
	@Query("select avg(u.articles.size), sqrt(sum(u.articles.size*u.articles.size)/count(u.articles.size)-(avg(u.articles.size)*avg(u.articles.size))) from User u")
	Object[] avgSqtrArticlesByWriter();

	// C-3
	@Query("select avg(n.articles.size), sqrt(sum(n.articles.size*n.articles.size)/count(n.articles.size)-(avg(n.articles.size)*avg(n.articles.size))) from Newspaper n")
	Object[] avgSqtrArticlesByNewspaper();

	// C-4
	@Query("select n from Newspaper n group by n having n.articles.size > (select avg(n2.articles.size)*1.1 from Newspaper n2)")
	Collection<Newspaper> newspapersMoreAverage();

	// C-5
	@Query("select n from Newspaper n group by n having n.articles.size < (select avg(n2.articles.size)*1.1 from Newspaper n2)")
	Collection<Newspaper> newspapersFewerAverage();

	// C-6
	@Query("select count(u1)/((select count(u2) from User u2)+0.0) from User u1 where cast((select count(n) from Newspaper n where n.publisher=u1) as int)>0")
	Double ratioUserCreatedNewspaper();

	// C-7
	@Query("select count(u1)/((select count(u2) from User u2)+0.0) from User u1 where (select count(a) from Article a where a.writer=u1)>0")
	Double ratioUserWrittenArticle();
}
