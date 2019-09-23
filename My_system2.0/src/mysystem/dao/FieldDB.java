package mysystem.dao;

public class FieldDB {
	private String name;
	private TypeFieldDB type;
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
	public TypeFieldDB getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(TypeFieldDB type) {
		this.type = type;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		FieldDB other = (FieldDB) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "FieldDB [name=" + name + ", type=" + type + "]";
	}
	/**
	 * @param name
	 * @param type
	 */
	public FieldDB(String name, TypeFieldDB type) {
		super();
		this.name = name;
		this.type = type;
	}
	
}
