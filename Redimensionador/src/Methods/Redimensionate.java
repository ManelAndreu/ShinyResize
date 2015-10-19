package Methods;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Redimensionate {
	
	public static void transforma(File f, File fand) throws FileNotFoundException, IOException {
		ImageInputStream in = new FileImageInputStream(f);
		ImageOutputStream out = new FileImageOutputStream(fand);
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();

	}

	public static void redimensionaAlMenu(JLabel label, JPanel panel, File f) throws IOException {
		BufferedImage bi = null;
		bi = ImageIO.read(f);
		int w = bi.getWidth();
		int h = bi.getHeight();
		int newW = panel.getWidth();
		int newH = panel.getHeight();
		BufferedImage nbi = new BufferedImage(newW, newH, bi.getType());
		Graphics2D g = nbi.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(bi, 0, 0, newW, newH, 0, 0, w, h, null);
		g.dispose();
		ImageIcon iinbi = new ImageIcon(nbi);
		Icon iNbi = (Icon) (iinbi);
		label.setIcon(iNbi);
		label.setVisible(true);
		label.setBounds(0, 0, panel.getWidth(), panel.getHeight());
	}

	public static void redimensionaPersonal(String path, int width, int height, File f1) throws IOException {
		File f = new File(path);
		if (f.exists()) {
			f.delete();
			f = new File(path);
		}
		redimensiona(f1, width, height, f.getAbsolutePath());
	}

	public static void redimensionaAndroid(String[] androidImgPaths, String[] androidImgFolders, String ic_launcher,
			File f1, File f2, String APATH, int[] Width, int[] Height) throws FileNotFoundException, IOException {
		androidImgPaths = new String[androidImgFolders.length];
		for (int i = 0; i < androidImgFolders.length; i++) {
			androidImgFolders[i] = f2.getAbsolutePath() + APATH + androidImgFolders[i];
			File f = new File(androidImgFolders[i]);
			String[] list = f.list();
			for (int k = 0; k < list.length; k++) {
				if (list[k].equals("ic_launcher.png")) {
					ic_launcher = list[k];
				}
			}
			if (ic_launcher == null) {
				ic_launcher = JOptionPane.showInputDialog(null,
						"Introdueix el nom de la icona que te al manifest de la app (incluint extensió).");
			}

			String path = androidImgFolders[i] + "\\" + ic_launcher;
			androidImgPaths[i] = path;
			f = new File(path);
			f.delete();
		}
		for (int i = 0; i < androidImgFolders.length; i++) {
			File androidF = new File(androidImgPaths[i]);
			redimensiona(f1, Width[i], Height[i], androidImgFolders[i] + "\\" + ic_launcher);
		}
	}

	public static void redimensiona(File f, int wi, int he, String path) throws IOException {
		BufferedImage bi = null;
		bi = ImageIO.read(f);
		int w = bi.getWidth();
		int h = bi.getHeight();
		int newW = wi;
		int newH = he;
		BufferedImage nbi = new BufferedImage(newW, newH, bi.getType());
		Graphics2D g = nbi.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(bi, 0, 0, newW, newH, 0, 0, w, h, null);
		g.dispose();
		ImageIcon iinbi = new ImageIcon(nbi);
		File fIcon = Transform.imageIconToFile(iinbi, path);
		File fFinal = new File(fIcon.getAbsolutePath());
		transforma(fIcon, fFinal);

	}
}
