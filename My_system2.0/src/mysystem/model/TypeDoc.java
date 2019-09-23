package mysystem.model;

public class TypeDoc {
	private long id;
	private String dirPath;
	private String shortName;
	private String longName;
	private String desc;
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
	 * @return the dirPath
	 */
	public String getDirPath() {
		return dirPath;
	}
	/**
	 * @param dirPath the dirPath to set
	 */
	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
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
		result = prime * result + ((dirPath == null) ? 0 : dirPath.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((longName == null) ? 0 : longName.hashCode());
		result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
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
		TypeDoc other = (TypeDoc) obj;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (dirPath == null) {
			if (other.dirPath != null)
				return false;
		} else if (!dirPath.equals(other.dirPath))
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
		return true;
	}
	@Override
	public String toString() {
		return "TypeDoc [id=" + id + ", shortName=" + shortName+ ", dirPath=" + dirPath + "]";
	}
	/**
	 * @param dirPath
	 * @param shortName
	 * @param longName
	 * @param desc
	 */
	public TypeDoc(String dirPath, String shortName, String longName, String desc) {
		this.dirPath = dirPath;
		this.shortName = shortName;
		this.longName = longName;
		this.desc = desc;
	}
	/**
	 * @param id
	 * @param dirPath
	 * @param shortName
	 * @param longName
	 * @param desc
	 */
	public TypeDoc(long id, String dirPath, String shortName, String longName, String desc) {
		this(dirPath, shortName,longName,desc);
		this.id = id;
		
	}
	
}
