
import java.awt.Dimension;
import javax.swing.JFrame;

public class UstvariIgro extends JFrame  {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		Polje UstvariIgro = new Polje();
		UstvariIgro.setPreferredSize(new Dimension(500,500));
		UstvariIgro.setLocation(500, 250);
		UstvariIgro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UstvariIgro.pack();
		UstvariIgro.setVisible(true);
	}
}