import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Polje extends JFrame {

	private static final long serialVersionUID = 1L;
	private ArrayList<kartica> cards;
	public ArrayList<kartica> seznamKartic;
	private kartica izbranaKartica;
	private kartica card1;
	private kartica card2;
	private Timer delay;
	private int clickCounter = 0;
	private int score = 0;
	ArrayList<Integer> karticaValue = new ArrayList<Integer>();

	Polje() throws FileNotFoundException {
	
		
		//Z JOptionPane vprasamo uporabnika koliko parov ?eli imeti v igri 
		
		String userInput = JOptionPane.showInputDialog("Vpiste poljubno stevilo parov!");
		int stParov = Integer.parseInt(userInput);

		
		ArrayList<kartica> seznamKartic = new ArrayList<kartica>();


		for (int x = 0; x < stParov; x++) {
			karticaValue.add(x);
			karticaValue.add(x);
			System.out.println(x);

		}
		
		/* Collections.shuffle nakljucno pomeva vse kartice po polju */
		Collections.shuffle(karticaValue);
		System.out.println(karticaValue);

		/*
		 * V tej zanki vsaki ustvarjeni kartici dodamo vrednost(int) in jo dodamo na
		 * seznam seznamKartic
		 */
		for (int value : karticaValue) {
			kartica cards = new kartica();
			cards.setId(value);
			cards.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					izbranaKartica = cards;
					obrniJih();
				}
			});
			seznamKartic.add(cards);
		}

		this.cards = seznamKartic;

		/* Nastavimo koliko casa so kartice odkrite */
		delay = new Timer(200, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					cekirajKartice(karticaValue);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		delay.setRepeats(false);

		Container panel = getContentPane();
		panel.setLayout(new GridLayout(3, 4));
        for (kartica c:cards){
            panel.add(c);
        }
        setTitle("IGRA SPOMINA 2018");

	}

	/**
	 * Desc: Metoda dodeli vsaki kartici ?tevilko po kateri jo prepoznamo. Params: /
	 * Pre: card1.getId()==card2.getId() Post: card1!=null && card1 !=izbranaKartica
	 * && card2==null Result: V primeru, da velja Pre kartici ostaneta obrnjeni. V
	 * primer,da se kartici ne ujemata, ju ponovno obrne z zamikom. Env: -
	 **/

	private void obrniJih() {
		if (card1 == null && card2 == null) {
			card1 = izbranaKartica;
			card1.setText(String.valueOf(card1.getId()));
			score = score + 2;
		} else if (card1 != null && card1 != izbranaKartica && card2 == null) {
			card2 = izbranaKartica;
			card2.setText(String.valueOf(card2.getId()));
			delay.start();
			score--;
		}
		clickCounter++;
	}

	/**
	 * Desc: Metoda preveri ?e se kartici ujemata. Params: / Pre:
	 * card1.getId()==card2.getId() Post: / Result: / Env: -
	 * @throws IOException 
	 * @param karticaValue
	 * */
	private void cekirajKartice(ArrayList<Integer> karticaValue) throws IOException {
		/* Ujemalni pogoj */
		if (card1.getId() == card2.getId()) {
			/* Izklu?imo obe kartici. */
			card1.setEnabled(false);
			card2.setEnabled(false);
			/* Potrdimo da se izbrana kartica ujema z drugo izbrano kartico. */
			card1.setMatched(true);
			card2.setMatched(true);
			/* ?e je rezultat/score negativen, mu nastavi vrednost 0. */
			if (this.score < 0) {
				score = 0;
			}
			if (this.aliJeKonec()) {
				JOptionPane.showMessageDialog(this, "Cestitke,zmagali ste!");
				JOptionPane.showMessageDialog(this, "Ctevilo premikov: " + clickCounter);
				JOptionPane.showMessageDialog(this, "Vas rezultat je: " + score);
				SaveFile(karticaValue);
				System.exit(0);
			}
		} else {
			card1.setText("");
			card2.setText("");
		}
		/* Ponastavimo kartice na "null". */
		card1 = null;
		card2 = null;
	}

	/**
	 * Desc: Metoda preveri vsako kartico, ?e ima svoj par(true). V primeru da je
	 * true, je igre konec,saj so vsi pari najdeni. Params: / Pre: / Post: / Result:
	 * true ali false Env: -
	 **/
	private boolean aliJeKonec() {
		for (kartica cards : this.cards) {
			if (!cards.getMatched()) {
				return false;
			}
		}
		return true;
	}

	/**
	 *
	 * @param karticaValue
	 * @throws FileNotFoundException 
	 * 
	 */
	private void SaveFile(ArrayList<Integer> karticaValue) throws FileNotFoundException{
		
		File f = new File("Saved.txt");
		if(f.exists()) { f.delete();}
	
		PrintWriter writer = new PrintWriter(new File("Saved.txt"));

	for (Integer aKarticaValue : karticaValue) {
        writer.println(aKarticaValue);
    }
	writer.close();
	}

public void LoadFile(){
	
	try{
		{
	    Scanner s = new Scanner(new File("Saved.txt"));
	    ArrayList<Integer> karticaValue = new ArrayList<Integer>();
	    while (s.hasNext()){
	        if(s.hasNextInt()){
	            karticaValue.add(s.nextInt());
	            s.nextLine();
	        	}
	    	}
	    s.close();
	    }
	}
		catch(Exception exc){
			exc.printStackTrace();
			}
}

}