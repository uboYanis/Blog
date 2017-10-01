package hachee;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hachee {
	private String code;

	public Hachee(String md5) {
		Passe(md5);
	}

	private void Passe(String pass) {
		byte[] passBytes = pass.getBytes();
		try {
			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			algorithm.reset();
			algorithm.update(passBytes);
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] messageDigest = md.digest(passBytes);
			BigInteger number = new BigInteger(1, messageDigest);
			this.code = number.toString(16);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public String codeGet() {
		return code;
	}

}
