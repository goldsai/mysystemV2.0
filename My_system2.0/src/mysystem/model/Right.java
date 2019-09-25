package mysystem.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Right extends BaseModel {
	/**
	 * Хранит экземпляры класса.
	 */
	private static List<Right> storage = new ArrayList<>();

	public static Right getInstance(long id, String uri, String shortName, String longName, String desc) {
		Optional<Right> ins = storage.parallelStream().filter(s -> s.id == id).findAny();
		Right right = ins.orElse(null);
		if (right == null) {
			right = new Right( id,  uri,  shortName,  longName,  desc);
			storage.add(right);
		}
		return right;
	}
	// private long id;
	private String uri;
	private String shortName;
	private String longName;
	private String desc;

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @return the longName
	 */
	public String getLongName() {
		return longName;
	}

	/**
	 * @param longName the longName to set
	 */
	public void setLongName(String longName) {
		this.longName = longName;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((longName == null) ? 0 : longName.hashCode());
		result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
		Right other = (Right) obj;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (id != other.id)
			return false;
		if (longName == null) {
			if (other.longName != null)
				return false;
		} else if (!longName.equals(other.longName))
			return false;
		if (shortName == null) {
			if (other.shortName != null)
				return false;
		} else if (!shortName.equals(other.shortName))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Right [id=" + id + ", shortName=" + shortName + ", uri=" + uri + "]";
	}

	/**
	 * @param uri
	 * @param shortName
	 * @param longName
	 * @param desc
	 */
	private Right(String uri, String shortName, String longName, String desc) {
		super();
		this.uri = uri;
		this.shortName = shortName;
		this.longName = longName;
		this.desc = desc;
	}

	/**
	 * @param id
	 * @param uri
	 * @param shortName
	 * @param longName
	 * @param desc
	 */
	private Right(long id, String uri, String shortName, String longName, String desc) {
		this(uri, shortName, longName, desc);
		this.id = id;

	}

}
