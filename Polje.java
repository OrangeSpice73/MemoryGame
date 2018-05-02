
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Polje extends JFrame {

	private static final long serialVersionUID = 1L;
	public ArrayList<kartica> cards;
	public ArrayList<Integer> karticaValue;
	public ArrayList<kartica> seznamKartic;
	public kartica izbranaKartica;
	private kartica card1;
	private kartica card2;
	private Timer delay;
	public int clickCounter = 0;
	private int score = 0;

	public Polje() {

		/* Z JOptionPane vprašamo uporabnika koliko parov želi imeti v igri */
		String userInput = JOptionPane.showInputDialog("Vpište poljubno število parov!");
		int stParov = Integer.parseInt(userInput);

		/* Deklariramo seznamKartic in seznam vrednosti posamezne kartice */
		ArrayList<kartica> seznamKartic = new ArrayList<kartica>();
		ArrayList<Integer> karticaValue = new ArrayList<Integer>();

		/* Shranjevanje števil kartic v seznam KarticaValue */
		for (int x = 0; x < stParov; x++) {
			karticaValue.add(x);
			karticaValue.add(x);
			System.out.println(x);

		}

		/* Collections.shuffle nakljuèno pomeša vse kartice po polju */
		Collections.shuffle(karticaValue);

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
					cekirajKartice();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		delay.setRepeats(false);
		
		/*
		 * Nastavimo naše polje. in vstavimo vsako kartico v naše polje.
		 **
		 *JPanel mainPanel = new JPanel();
  mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

  JPanel paintPanel = new JPanel();
  JPanel textPanel = new JPanel();

  mainPanel.add(paintPanel);
  mainPanel.add(textPanel);
		 *
		 *
		 */
		Container panel = getContentPane();
		panel.setLayout(new GridLayout(3, 4));
        for (kartica c:cards){
            panel.add(c);
        }
        setTitle("IGRA SPOMINA 2018");

	}
	


	/**
	 * Desc: Metoda dodeli vsaki kartici številko po kateri jo prepoznamo. Params: /
	 * Pre: card1.getId()==card2.getId() Post: card1!=null && card1 !=izbranaKartica
	 * && card2==null Result: V primeru, da velja Pre kartici ostaneta obrnjeni. V
	 * primer,da se kartici ne ujemata, ju ponovno obrne z zamikom. Env: -
	 **/

	public void obrniJih() {
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
	 * Desc: Metoda preveri èe se kartici ujemata. Params: / Pre:
	 * card1.getId()==card2.getId() Post: / Result: / Env: -
	 * @throws IOException 
	 **/
	public void cekirajKartice() throws IOException {
		/* Ujemalni pogoj */
		if (card1.getId() == card2.getId()) {
			/* Izkluèimo obe kartici. */
			card1.setEnabled(false);
			card2.setEnabled(false);
			/* Potrdimo da se izbrana kartica ujema z drugo izbrano kartico. */
			card1.setMatched(true);
			card2.setMatched(true);
			/* Èe je rezultat/score negativen, mu nastavi vrednost 0. */
			if (this.score < 0) {
				score = 0;
			}
			if (this.aliJeKonec()) {
				JOptionPane.showMessageDialog(this, "Èestitke,zmagali ste!");
				JOptionPane.showMessageDialog(this, "Število premikov: " + clickCounter);
				JOptionPane.showMessageDialog(this, "Vaš rezultat je: " + score);
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
	 * Desc: Metoda preveri vsako kartico, èe ima svoj par(true). V primeru da je
	 * true, je igre konec,saj so vsi pari najdeni. Params: / Pre: / Post: / Result:
	 * true ali false Env: -
	 **/
	public boolean aliJeKonec() {
		for (kartica cards : this.cards) {
			if (cards.getMatched() == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 *  var writer = PrintWriter("Saved.txt")
        For loop ->>>>>>>>>
        writer.print(karticaValue[i])
		end of for loop <----------
		writer.close()
	 * @param karticaValue
	 * @throws FileNotFoundException 
	 * 
	 */
public void SaveFile(ArrayList<Integer> karticaValue) throws FileNotFoundException{
	
	PrintWriter writer = new PrintWriter(new File("Saved.txt"));
	String tempFile;
	
	/**
	try{
	FileOutputStream saveFile = new FileOutputStream("Saved.txt");
	ObjectOutputStream save = new ObjectOutputStream(saveFile);
		for (int i=0; i<karticaValue.size();i++) {
			
			save.writeObject(karticaValue+" ");
			System.out.println(karticaValue);
			}
		save.close();
		((ObjectOutput) save).flush();
		}
	catch(Exception e){
		e.printStackTrace();
		}
	}
**/
	for(int i=0;i<karticaValue.size();i++) {
	//writer.println(karticaValue.get(i));
	System.out.println(karticaValue.size());
	}
	writer.close();
	}

public void LoadFile(){
	try{
		BufferedReader loadingFile = new BufferedReader(new FileReader("Saved.txt"));
		String line = loadingFile.readLine();	
		 while(line != null)
		    {
		         String[] tokens = line.split(" ");
		         karticaValue.add(Integer.parseInt(tokens[1]));
		         line = loadingFile.readLine();
		}
		loadingFile.close();
		((ObjectOutput) loadingFile).flush();}
		catch(Exception exc){
			exc.printStackTrace();
			}
	
}


}