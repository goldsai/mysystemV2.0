package mysystem.model;

import java.util.Date;

public class DocVersion extends BaseModel {
	// private long id;
	private Date createDate;
	private Doc doc;
	private int labelDocVersion;
	private Message desc;

	/**
	 * @return the createDate
	 */
	public java.util.Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the doc
	 */
	public Doc getDoc() {
		return doc;
	}

	/**
	 * @param doc the doc to set
	 */
	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	/**
	 * @return the labelDocVersion
	 */
	public int getLabelDocVersion() {
		return labelDocVersion;
	}

	/**
	 * @param labelDocVersion the labelDocVersion to set
	 */
	public void setLabelDocVersion(int labelDocVersion) {
		this.labelDocVersion = labelDocVersion;
	}

	/**
	 * @return the desc
	 */
	public Message getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(Message desc) {
		this.desc = desc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + labelDocVersion;
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
		DocVersion other = (DocVersion) obj;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (id != other.id)
			return false;
		if (labelDocVersion != other.labelDocVersion)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DocVersion [id=" + id + ", createDate=" + createDate + ", doc=" + doc.getName() + ", labelDocVersion="
				+ labelDocVersion + "]";
	}

	/**
	 * @param createDate
	 * @param doc
	 * @param labelDocVersion
	 * @param desc
	 */
	public DocVersion(Date createDate, Doc doc, int labelDocVersion, Message desc) {
		this.createDate = createDate;
		this.doc = doc;
		this.labelDocVersion = labelDocVersion;
		this.desc = desc;
	}

	/**
	 * @param id
	 * @param createDate
	 * @param doc
	 * @param labelDocVersion
	 * @param desc
	 */
	public DocVersion(long id, Date createDate, Doc doc, int labelDocVersion, Message desc) {
		this(createDate, doc, labelDocVersion, desc);
		this.id = id;

	}

}
