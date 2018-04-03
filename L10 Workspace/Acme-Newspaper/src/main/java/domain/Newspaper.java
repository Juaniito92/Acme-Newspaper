
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Newspaper extends DomainEntity {

	// Constructors

	public Newspaper() {
		super();
	}


	// Attributes

	private String	title;
	private String	description;
	private Date	publicationDate;
	private String	picture;
	private boolean	isPrivate;


	@NotBlank
	@SafeHtml
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@SafeHtml
	public Date getPublicationDate() {
		return this.publicationDate;
	}

	public void setPublicationDate(final Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	@URL
	@SafeHtml
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public boolean isPrivate() {
		return this.isPrivate;
	}

	public void setPrivate(final boolean isPrivate) {
		this.isPrivate = isPrivate;
	}


	// Relationships

	private User						publisher;
	private Collection<Article>			articles;
	private Collection<Subscription>	subscriptions;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getPublisher() {
		return this.publisher;
	}

	public void setPublisher(final User publisher) {
		this.publisher = publisher;
	}

	@Valid
	@NotNull
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "newspaper")
	public Collection<Article> getArticles() {
		return this.articles;
	}

	public void setArticles(final Collection<Article> articles) {
		this.articles = articles;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "newspaper")
	public Collection<Subscription> getSubscriptions() {
		return this.subscriptions;
	}

	public void setSubscriptions(final Collection<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

}
