package Methods;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Seek {
	public static String buscaAndroidStudioFolder(String androidPath) {
		JFileChooser jfc;
		String returned;
		if (androidPath == "") {
			jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int jc = jfc.showOpenDialog(null);
			if (jc == jfc.APPROVE_OPTION) {
				returned =  jfc.getSelectedFile().getAbsolutePath();
			}else {
				JOptionPane.showMessageDialog(null, "Se esperaba que afegires un arxiu!");
				throw new NullPointerException();
			}
		return returned;
		} else {
			int dec = JOptionPane.showConfirmDialog(null,
					"Ara tens la carpeta de Android ací: " + androidPath + ". Vols canviar-la?");
			if (dec == 0) {
				jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int jc = jfc.showOpenDialog(null);
				if (jc == jfc.APPROVE_OPTION) {
					returned = jfc.getSelectedFile().getAbsolutePath();
				}else {
					JOptionPane.showMessageDialog(null, "Se esperaba que afegires un arxiu!");
					throw new NullPointerException();
				}
				return returned;
			} else if (dec == 1) {
				return androidPath;
			}
			return androidPath;
		} 
	}
	public static File buscaAndroidProject(JTextField jtf, String androidPath, String jfcDir) {
		JFileChooser jfc;
		jfc = new JFileChooser(androidPath);
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int jc = jfc.showOpenDialog(null);
		if (jc == jfc.APPROVE_OPTION) {
			jtf.setText(jfc.getSelectedFile().getAbsolutePath().toString());
			jfcDir = jfc.getCurrentDirectory().getAbsolutePath();
			File f = new File(jfc.getSelectedFile().getAbsolutePath());
			String[] files = f.list();
			for (int i = 0; i < files.length; i++) {
				if (files[i].equals("settings.gradle")) {
					return f;
				}
			}
			JOptionPane.showMessageDialog(null, "No es un projecte Android!!");
			jtf.setText("");
			throw new NullPointerException();

		} else {
			JOptionPane.showMessageDialog(null, "Se esperaba que afegires un arxiu!");
			throw new NullPointerException();
		}
	}
	public static File buscaCarpeta(JTextField jtf, File f1) throws NullPointerException {
		JOptionPane.showMessageDialog(null, "Selecciona la carpeta on vols que es guarde.");

		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int jc = jfc.showOpenDialog(null);
		if (jc == jfc.APPROVE_OPTION) {
			jtf.setText(jfc.getCurrentDirectory().toString() + "\\" + jfc.getSelectedFile().getName());
			String nom = JOptionPane.showInputDialog("Com es dirà la teva imatge?",
					f1.getName().substring(0, f1.getName().indexOf('.')));
			nom = nom + ".png";
			File f = new File(
					jfc.getCurrentDirectory().toString() + "\\" + jfc.getSelectedFile().getName() + "\\" + nom);
			return f;

		} else {
			JOptionPane.showMessageDialog(null, "Se esperaba que afegires una carpeta!");
			throw new NullPointerException();
		}

	}
	/**
	 * Busquem el fitxer que volem copiar
	 * 
	 * @param jtf
	 * @return
	 * @throws NullPointerException
	 */
	public static File buscaFitxer(JTextField jtf, String jfcDir) throws NullPointerException {
		JFileChooser jfc;
		if (jfcDir == null) {
			jfc = new JFileChooser(System.getProperty("user.home"));
		} else {
			jfc = new JFileChooser(jfcDir);
		}
		FileNameExtensionFilter filtre = new FileNameExtensionFilter(".jpg, .png, .gif, .bmp", "png", "gif", "jpg",
				"bmp");
		jfc.setFileFilter(filtre);
		int jc = jfc.showOpenDialog(null);
		if (jc == jfc.APPROVE_OPTION) {
			jtf.setText(jfc.getSelectedFile().getAbsolutePath().toString());
			jfcDir = jfc.getCurrentDirectory().getAbsolutePath();
			File f = new File(jfc.getSelectedFile().getAbsolutePath());
			return f;
		} else {
			JOptionPane.showMessageDialog(null, "Se esperaba que afegires un arxiu!");
			throw new NullPointerException();
		}
	}

}
