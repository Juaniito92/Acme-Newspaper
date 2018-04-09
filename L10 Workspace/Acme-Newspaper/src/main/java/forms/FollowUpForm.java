package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

public class FollowUpForm {

	// Constructors

	public FollowUpForm() {
		super();
	}

	// Attributes
	private int id;
	private String title;
	private String summary;
	private String text;
	private String pictures;

	private int articleId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPictures() {
		return this.pictures;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int newspaperId) {
		this.articleId = newspaperId;
	}
}
