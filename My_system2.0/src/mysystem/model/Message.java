package mysystem.model;

import java.util.Date;

public class Message {
	private long id;
	private Date createDate;
	private String txt;
	private User author;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the txt
	 */
	public String getTxt() {
		return txt;
	}

	/**
	 * @param txt the txt to set
	 */
	public void setTxt(String txt) {
		this.txt = txt;
	}

	/**
	 * @return the author
	 */
	public User getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(User author) {
		this.author = author;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((txt == null) ? 0 : txt.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (id != other.id)
			return false;
		if (txt == null) {
			if (other.txt != null)
				return false;
		} else if (!txt.equals(other.txt))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", createDate=" + createDate + ", author=" + author.getLogin() + ", txt=" + txt
				+ "]";
	}

	/**
	 * @param createDate
	 * @param txt
	 * @param author
	 */
	public Message(Date createDate, String txt, User author) {
		this.createDate = createDate;
		this.txt = txt;
		this.author = author;
	}

	/**
	 * @param id
	 * @param createDate
	 * @param txt
	 * @param author
	 */
	public Message(long id, Date createDate, String txt, User author) {
		this(createDate, txt, author);
		this.id = id;

	}

}
