package repositories;

import java.util.Collection;
import java.util.Date;

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

	// B-1
	// The average number of follow-ups per article.
	@Query("select avg(a.followUps.size) from Article a")
	Double avgFollowupsPerArticle();

	// B-2
	// The average number of follow-ups per article up to one week after the
	// correspondingnewspaper’s been published
	@Query("select avg(a.followUps.size) from Article a where a.newspaper.publicationDate<?1")
	Double avgNumberOfFollowUpsPerArticleAfter1Week(Date f);

	// B-3
	// The average number of follow-ups per article up to two weeks after the
	// corresponding newspaper’s been published.
	@Query("select avg(a.followUps.size) from Article a where a.newspaper.publicationDate<?1")
	Double avgNumberOfFollowUpsPerArticleAfter2Week(Date since);

	// B-4
	// The average and the standard deviation of the number of chirps per user
	@Query("select avg(u.chirps.size),stddev(u.chirps.size) from User u")
	Object[] avgStddevNumberOfChirpPerUser();

	// B-5
	// The ratio of users who have posted above 75% the average number of chirps
	// per user.
	@Query("select count(m)/(select count(n) from User n) from User m where m.chirps.size > (select avg(v.chirps.size)*0.75 from User v))")
	Double ratioUsersMorePostedChirpsOfAveragePerUser();

}
