import javax.swing.JButton;

public class kartica extends JButton {

	private static final long serialVersionUID = 1L;

	private int idKartica;


	private boolean seUjema;

	/**
	 * Desc: Metoda dodeli vsaki kartici �tevilko po kateri jo prepoznamo od 0 do
	 * x. Params: int idKartica Pre: / Post: / Result: / Env: -
	 **/

	public void setId(Object value) {
		this.idKartica =(int) value;
	}

	/**
	 * Desc: Metoda nam vrne posamezno id �tevilko kartice Params: / Pre: / Post:
	 * / Result: idKartica Env: -
	 **/

	public int getId() {
		return this.idKartica;
	}
	

	/**************************************************/
	/**
	 * Desc: Metoda nastavi,�e sta se kartici ujemali oz se nista ujemali Params:
	 * seUjema Pre: / Post: / Result: true ali false Env: -
	 **/

	public void setMatched(boolean SeUjema) {
		this.seUjema = SeUjema;
	}

	/**************************************************/
	/**
	 * Desc: Metoda preveri �e se vse kartice ujemajo/imajo par Params: / Pre: /
	 * Post: / Result: true ali false Env: -
	 **/

	public boolean getMatched() {
		return this.seUjema;
	}

}
