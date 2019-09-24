package mysystem.model;

public class Doc extends BaseModel {
	// private long id;
	private String name;
	private TypeDoc type;
	private DocVersion actualVersion;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public TypeDoc getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TypeDoc type) {
		this.type = type;
	}

	/**
	 * @return the actualVersion
	 */
	public DocVersion getActualVersion() {
		return actualVersion;
	}

	/**
	 * @param actualVersion the actualVersion to set
	 */
	public void setActualVersion(DocVersion actualVersion) {
		this.actualVersion = actualVersion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Doc other = (Doc) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Doc [id=" + id + ", name=" + name + ", type=" + type.getShortName() + ", actualVersion="
				+ actualVersion.getId() + "]";
	}

	/**
	 * @param name
	 * @param type
	 * @param actualVersion
	 */
	public Doc(String name, TypeDoc type, DocVersion actualVersion) {
		this.name = name;
		this.type = type;
		this.actualVersion = actualVersion;
	}

	/**
	 * @param id
	 * @param name
	 * @param type
	 * @param actualVersion
	 */
	public Doc(long id, String name, TypeDoc type, DocVersion actualVersion) {
		this(name, type, actualVersion);
		this.id = id;

	}

}
