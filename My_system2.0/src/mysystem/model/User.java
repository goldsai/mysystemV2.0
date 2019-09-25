package mysystem.model;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class User extends BaseModel {
	/**
	 * Хранит экземпляры класса.
	 */
	private static List<User> storage = new ArrayList<>();

	public static User storageFind(long id) {
		Optional<User> ins = storage.parallelStream().filter(s -> s.id == id).findAny();
		return ins.orElse(null);
	}
	public static User getInstance(long id, String login, String pass, List<Right> rights) {
		
		User user = storageFind(id);
		if (user == null) {
			user = new User(id, login, pass, rights);
			storage.add(user);
		}
		return user;
	}

	// private long id;
	private String login;
	private String pass;
	private List<Right> rights;

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

	private static final byte[] salt = { -102, 14, -54, -98, -120, 116, 97, -116, -119, -114, -101, 91, 57, -104, 9,
			91 };

	public void setPassHesh(String pass) throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec spec = new PBEKeySpec(pass.toCharArray(), salt, 65536, 128);
		this.pass = new String(factory.generateSecret(spec).getEncoded());
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
	private User(long id, String login, String pass) {
		this(login, pass);
		this.id = id;

	}

	/**
	 * @param login
	 * @param pass
	 */
	private User(String login, String pass) {
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
	private User(long id, String login, String pass, List<Right> rights) {
		this(id, login, pass);
		this.rights = rights;
	}

	/**
	 * @param login
	 * @param pass
	 * @param rights
	 */
	private User(String login, String pass, List<Right> rights) {
		this(login, pass);
		this.rights = rights;
	}

}
