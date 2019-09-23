package mysystem.model;

import java.util.List;

public class User {
	private long id;
	private String login;
	private String pass;
	private List<Right> rights;

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

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + "]";
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * @return the rights
	 */
	public List<Right> getRights() {
		return rights;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((pass == null) ? 0 : pass.hashCode());
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (pass == null) {
			if (other.pass != null)
				return false;
		} else if (!pass.equals(other.pass))
			return false;
		return true;
	}

	/**
	 * @param id
	 * @param login
	 * @param pass
	 */
	public User(long id, String login, String pass) {
		this(login, pass);
		this.id = id;

	}

	/**
	 * @param login
	 * @param pass
	 */
	public User(String login, String pass) {
		super();

		this.login = login;
		this.pass = pass;
	}

	/**
	 * @param id
	 * @param login
	 * @param pass
	 * @param rights
	 */
	public User(long id, String login, String pass, List<Right> rights) {
		this(id, login, pass);
		this.rights = rights;
	}

	/**
	 * @param login
	 * @param pass
	 * @param rights
	 */
	public User(String login, String pass, List<Right> rights) {
		this(login, pass);
		this.rights = rights;
	}

}
