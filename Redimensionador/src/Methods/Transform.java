package Methods;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Transform {
	public static void actualitzaTamanys(File f, int pW, int pH, JTextField tfamp, JTextField tflla) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(f);
			pW = bi.getWidth();
			pH = bi.getHeight();
			tfamp.setText(pW + "");
			tflla.setText(pH + "");
			if (pW != pH) {
				JOptionPane.showMessageDialog(null,
						"Atenció!! El programa encara es troba en desenvolupament i la opció de tamany relatiu no funciona al 100% be, et recomane que el calcules manualment.",
						"Atenció!!", JOptionPane.WARNING_MESSAGE);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static File imageIconToFile(ImageIcon icon, String p) throws IOException {
		Image img = icon.getImage();
		BufferedImage bi = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), 6);
		Graphics2D g2 = bi.createGraphics();
		g2.drawImage(img, 0, 0, null);
		g2.dispose();
		File f = new File(p);
		ImageIO.write(bi, "png", f);
		return f;
	}
}
