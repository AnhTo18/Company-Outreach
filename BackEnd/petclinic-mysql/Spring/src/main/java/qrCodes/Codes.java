package qrCodes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.stereotype.Component;

@Component

// Spring jpa jars.
@Entity
@Table(name= "codes")
public class Codes {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	private int id;
	
	@Column(name = "Pointsx1000")
	@NotFound(action = NotFoundAction.IGNORE)
	private String Pointsx1000;
	
	@Column(name = "Pointsx500")
	@NotFound(action = NotFoundAction.IGNORE)
	private String Pointsx500;
	
	@Column(name = "Pointsx200")
	@NotFound(action = NotFoundAction.IGNORE)
	private String Pointsx200;
	
	@Column(name = "Pointsx100")
	@NotFound(action = NotFoundAction.IGNORE)
	private String Pointsx100;
	
	@Column(name = "Pointsx50")
	@NotFound(action = NotFoundAction.IGNORE)
	private String Pointsx50;
	
	@Column(name = "Loser")
	@NotFound(action = NotFoundAction.IGNORE)
	private String Loser;
	
	@Column(name = "Random")
	@NotFound(action = NotFoundAction.IGNORE)
	private String Random;

	public String getPointsx1000() {
		return Pointsx1000;
	}

	public void setPointsx1000(String Pointsx1000) {
		this.Pointsx1000 = Pointsx1000;
	}

	public String getPointsx500() {
		return Pointsx500;
	}

	public void setPointsx500(String Pointsx500) {
		this.Pointsx500 = Pointsx500;
	}

	public String getPointsx200() {
		return Pointsx200;
	}

	public void setPointsx200(String Pointsx200) {
		this.Pointsx200 = Pointsx200;
	}

	public String getPointsx100() {
		return Pointsx100;
	}

	public void setPointsx100(String Pointsx100) {
		this.Pointsx100 = Pointsx100;
	}

	public String getPointsx50() {
		return Pointsx50;
	}

	public void setPointsx50(String Pointsx50) {
		this.Pointsx50 = Pointsx50;
	}

	public String getLoser() {
		return Loser;
	}

	public void setLoser(String Loser) {
		this.Loser = Loser;
	}

	public String getRandom() {
		return Random;
	}

	public void setRandom(String Random) {
		this.Random = Random;
	}
	
	@Override
	public String toString() {
		return "Point_ID [Pointsx1000=" + Pointsx1000 + ", Pointsx500=" + Pointsx500 + Pointsx200 + ", Pointsx200=" + Pointsx100 + ", "
				+ "telephone=" + Pointsx100 + ", Pointsx50=" + Pointsx50 +", Loser=" + Loser + ", Random=" + Random + "]";
	}

}