package Vistes;

import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import Oth.MainVersion;

import java.awt.Toolkit;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Color;

/**
 * Shiny Resize
 * 
 * @author Manel
 *
 */
public class Win extends JFrame {
	private final String APATH = "\\app\\src\\main\\res\\";
	private JPanel contentPane, imgPanel;
	private String[] androidImgFolders = { "mipmap-hdpi", "mipmap-mdpi", "mipmap-xhdpi", "mipmap-xxhdpi" },
			androidImgPaths;
	private int Height[] = { 72, 48, 96, 144 };
	private int Width[] = { 72, 48, 96, 144 };
	private int pW, pH, auxw, auxh, auxz;
	private JButton btnBuscaDir, btnRed, btnAndPro;
	private JLabel lblImg;
	private JTextField tfImg;
	private JTextField tfDir;
	private boolean isAndroid, selected, imgRelative;
	private String jfcDir, androidPath, ic_launcher;
	private JRadioButton android, pers;
	private File f1, f2;
	private JTextField tfamp;
	private JTextField tflla;
	private JMenuBar menuBar;
	private JMenu mnArxiu;
	private JMenuItem mntmObre;

	public void repainter() {
		btnBuscaDir.repaint();
		btnRed.repaint();
		lblImg.repaint();
		tfImg.repaint();
		tfDir.repaint();

		android.repaint();
		pers.repaint();

		tfamp.repaint();
		tflla.repaint();
		contentPane.repaint();
		imgPanel.repaint();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Win frame = new Win();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Win() {
		androidPath = "";
		setTitle("Shiny Resize (v " + MainVersion.V + ")");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Win.class.getResource("/img/logo.png")));
		imgRelative = true;
		selected = false;
		this.setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 510, 352);

		menuBar = new JMenuBar();
		menuBar.setBackground(new Color(224, 255, 255));
		setJMenuBar(menuBar);

		mnArxiu = new JMenu("Arxiu");
		mnArxiu.setBackground(new Color(230, 230, 250));
		menuBar.add(mnArxiu);

		mntmObre = new JMenuItem("Obre...");
		mntmObre.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				f1 = buscaFitxer(tfImg);
				try {
					redimensionaAlMenu(lblImg, imgPanel, f1);
					if (pers.isEnabled()) {
						actualitzaTamanys(f1);
					} else {
						android.setEnabled(true);
						pers.setEnabled(true);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		mnArxiu.add(mntmObre);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(173, 216, 230));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		imgPanel = new JPanel();
		imgPanel.setBackground(SystemColor.activeCaptionBorder);
		imgPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		imgPanel.setBounds(10, 11, 200, 200);
		contentPane.add(imgPanel);
		imgPanel.setLayout(null);

		lblImg = new JLabel("");
		lblImg.setBounds(0, 0, 46, 14);
		imgPanel.add(lblImg);

		JPanel descPanel = new JPanel();
		descPanel.setBackground(SystemColor.activeCaptionBorder);
		descPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		descPanel.setBounds(10, 222, 200, 73);
		contentPane.add(descPanel);
		descPanel.setLayout(null);

		btnRed = new JButton("Redimensiona");
		btnRed.setEnabled(false);
		btnRed.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (f1 != null && f2 != null && isAndroid) {
					try {

						redimensionaAndroid();

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (f1 != null && f2 != null && !isAndroid && pW > 0 && pH > 0) {
					try {
						redimensionaPersonal(f2.getAbsolutePath(), pW, pH);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Assegura't de que està tot be!");
				}
				JOptionPane.showMessageDialog(null, "La imatge ha estat adaptada.");
			}
		});
		btnRed.setBounds(10, 11, 180, 51);
		descPanel.add(btnRed);

		tfImg = new JTextField();
		tfImg.setBackground(SystemColor.activeCaptionBorder);
		tfImg.setEditable(false);
		tfImg.setBounds(220, 29, 274, 20);
		contentPane.add(tfImg);
		tfImg.setColumns(10);

		JLabel lblSeleccionaUnaImatge = new JLabel("Direcció de la imatge:");
		lblSeleccionaUnaImatge.setBounds(219, 4, 239, 14);
		contentPane.add(lblSeleccionaUnaImatge);

		JPanel typePanel = new JPanel();
		typePanel.setBackground(SystemColor.activeCaptionBorder);
		typePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		typePanel.setBounds(368, 113, 126, 73);
		contentPane.add(typePanel);
		typePanel.setLayout(null);

		android = new JRadioButton("Android");
		android.setEnabled(false);
		android.setBackground(SystemColor.activeCaptionBorder);
		android.setBounds(6, 7, 109, 23);
		android.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				tfDir.setText("");
				pers.setSelected(false);
				tfamp.setText("");
				tflla.setText("");
				tfamp.setEditable(false);
				tflla.setEditable(false);
				isAndroid = true;
				selected = true;
				if (androidPath == "") {
					btnAndPro.setEnabled(true);
				} else {
					btnBuscaDir.setEnabled(true);
				}

			}
		});
		typePanel.add(android);

		pers = new JRadioButton("Personal");
		pers.setBackground(SystemColor.activeCaptionBorder);
		pers.setEnabled(false);
		pers.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tfDir.setText("");
				selected = true;
				isAndroid = false;
				tfamp.setEditable(true);
				tflla.setEditable(true);
				android.setSelected(false);
				btnBuscaDir.setEnabled(true);
				actualitzaTamanys(f1);

			}
		});
		pers.setBounds(6, 36, 109, 23);
		typePanel.add(pers);

		JLabel lblSeleccionaPerA = new JLabel("Selecciona per a que vols redimensionar:");
		lblSeleccionaPerA.setBounds(220, 60, 239, 14);
		contentPane.add(lblSeleccionaPerA);

		tfDir = new JTextField();
		tfDir.setBackground(SystemColor.activeCaptionBorder);
		tfDir.setEditable(false);
		tfDir.setBounds(220, 234, 274, 20);
		contentPane.add(tfDir);
		tfDir.setColumns(10);

		JLabel lblNewLabel = new JLabel("Selecciona la carpeta/projecte on ho vols desar:");
		lblNewLabel.setBounds(221, 197, 273, 14);
		contentPane.add(lblNewLabel);

		btnBuscaDir = new JButton("Selecciona");
		btnBuscaDir.setEnabled(false);
		btnBuscaDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnRed.setEnabled(true);
				if (isAndroid && selected) {
					f2 = buscaAndroidProject(tfDir);
				} else if (!isAndroid && selected) {
					System.out.println("Sida");
					f2 = buscaCarpeta(tfDir);
				} else if (!selected) {
					JOptionPane.showMessageDialog(null, "Selecciona per a que vols redimensionar!");
				}

			}
		});
		btnBuscaDir.setBounds(312, 268, 184, 23);
		contentPane.add(btnBuscaDir);

		JLabel lblAmple = new JLabel("Ample:");
		lblAmple.setBounds(220, 126, 46, 14);
		contentPane.add(lblAmple);

		JLabel lblLlarg = new JLabel("Llarg:");
		lblLlarg.setBounds(312, 126, 46, 14);
		contentPane.add(lblLlarg);

		tfamp = new JTextField();
		tfamp.setEditable(false);
		tfamp.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// Aci es controla aixo.
				if (imgRelative && Integer.parseInt(tfamp.getText()) != pW) {
					auxw = Integer.parseInt(tfamp.getText());
					auxh = Integer.parseInt(tflla.getText());
					pH = calculaRelative(auxw, auxh, auxz);
					tflla.setText(pH + "");
				}
				pW = Integer.parseInt(tfamp.getText());
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				if (imgRelative) {
					auxz = Integer.parseInt(tfamp.getText());
				}
			}
		});
		tfamp.setBounds(220, 151, 46, 20);
		contentPane.add(tfamp);
		tfamp.setColumns(10);

		tflla = new JTextField();
		tflla.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// Aci es controla aixo.
				if (imgRelative && Integer.parseInt(tflla.getText()) != pH) {
					auxw = Integer.parseInt(tfamp.getText());
					auxh = Integer.parseInt(tflla.getText());
					pW = calculaRelative(auxh, auxw, auxz);
					tfamp.setText(pW + "");
				}
				pH = Integer.parseInt(tflla.getText());
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				if (imgRelative) {
					auxz = Integer.parseInt(tflla.getText());
				}
			}
		});
		tflla.setEditable(false);
		tflla.setBounds(310, 151, 46, 20);
		contentPane.add(tflla);
		tflla.setColumns(10);

		JButton relButton = new JButton("");
		relButton.setToolTipText("Selecciona si vols que es conserve la relaci\u00F3 de mides.");
		relButton.setBackground(SystemColor.activeCaption);
		if (imgRelative) {
			relButton.setIcon(new ImageIcon(Win.class.getResource("/img/relSi.png")));
		} else {
			relButton.setIcon(new ImageIcon(Win.class.getResource("/img/relNo.png")));
		}
		relButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!imgRelative) {
					relButton.setIcon(new ImageIcon(Win.class.getResource("/img/relSi.png")));
					imgRelative = true;
				} else {
					relButton.setIcon(new ImageIcon(Win.class.getResource("/img/relNo.png")));
					imgRelative = false;
				}
				System.out.println(imgRelative + "");

			}
		});
		relButton.setBounds(276, 151, 25, 20);
		contentPane.add(relButton);

		btnAndPro = new JButton("");
		btnAndPro.setEnabled(false);
		btnAndPro.setIcon(new ImageIcon(Win.class.getResource("/img/IconaAnd.png")));
		btnAndPro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				androidPath = buscaAndroidStudioFolder();
				if(androidPath!=""){
					btnBuscaDir.setEnabled(true);
				}else{
					JOptionPane.showMessageDialog(null, "Fins que no hi poses una carpeta de Android no podràs seleccionar ningun projecte!");
				}
			}
		});
		btnAndPro.setBounds(220, 265, 25, 26);
		contentPane.add(btnAndPro);
		repainter();
	}

	public void actualitzaTamanys(File f) {
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void transforma(File f, File fand) throws FileNotFoundException, IOException {

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

	public int calculaRelative(int x, int y, int z) {
		int res = (x * z) / y;

		return res;
	}

	public void redimensionaPersonal(String path, int width, int height) throws IOException {
		// String name = JOptionPane.showInputDialog(null, "Posa-li un nom al
		// teu arxiu.");
		File f = new File(path/* +"\\"+name+".png" */);
		System.out.println(path/* +"\\"+name+".png, si soc jo" */);
		if (f.exists()) {
			f.delete();
			f = new File(path/* +"\\"+name+".png" */);
		}
		redimensiona(f1, width, height, f.getAbsolutePath());

	}

	public void redimensiona(File f, int wi, int he, String path) throws IOException {
		BufferedImage bi = null;
		bi = ImageIO.read(f);
		int w = bi.getWidth();
		int h = bi.getHeight();
		int newW = wi;
		int newH = he;
		BufferedImage nbi = new BufferedImage(newW, newH, bi.getType());
		System.out.println(bi.getType());
		Graphics2D g = nbi.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(bi, 0, 0, newW, newH, 0, 0, w, h, null);
		g.dispose();

		ImageIcon iinbi = new ImageIcon(nbi);

		File fIcon = imageIconToFile(iinbi, path);
		File fFinal = new File(fIcon.getAbsolutePath());
		// Problema
		System.out.println(fIcon.getAbsolutePath());
		transforma(fIcon, fFinal);

	}
	// Estic trabat acii

	public File imageIconToFile(ImageIcon icon, String p) throws IOException {
		Image img = icon.getImage();

		// BufferedImage bi = new BufferedImage(img.getWidth(null),
		// img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		BufferedImage bi = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), 6);
		Graphics2D g2 = bi.createGraphics();
		g2.drawImage(img, 0, 0, null);
		g2.dispose();
		File f = new File(p);
		ImageIO.write(bi, "png", f);
		System.out.println();
		return f;
	}

	public void redimensionaAlMenu(JLabel label, JPanel panel, File f) throws IOException {
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

	/**
	 * Busquem el fitxer que volem copiar
	 * 
	 * @param jtf
	 * @return
	 * @throws NullPointerException
	 */
	public File buscaFitxer(JTextField jtf) throws NullPointerException {
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

	public File buscaCarpeta(JTextField jtf) throws NullPointerException {
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

	public void redimensionaAndroid() throws FileNotFoundException, IOException {
		androidImgPaths = new String[androidImgFolders.length];
		for (int i = 0; i < androidImgFolders.length; i++) {
			androidImgFolders[i] = f2.getAbsolutePath() + APATH + androidImgFolders[i];
			File f = new File(androidImgFolders[i]);
			String[] list = f.list();
			for (int k = 0; k < list.length; k++) {
				if (list[k].equals("ic_launcher.png")) {
					ic_launcher = list[k];
					System.out.println(ic_launcher);
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

	public File buscaAndroidProject(JTextField jtf) {
		JFileChooser jfc;
//		if (androidPath == null) {
//			androidPath = JOptionPane.showInputDialog(null, "Introdueix el path on tens els projectes de Android.");
//		}
		androidPath = androidPath;
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

	public String buscaAndroidStudioFolder() {
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
}
