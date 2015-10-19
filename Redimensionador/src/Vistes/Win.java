package Vistes;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import Methods.*;
import Oth.MainVersion;

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

	private void repainter() {
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
				f1 = Seek.buscaFitxer(tfImg, jfcDir);
				try {
					Redimensionate.redimensionaAlMenu(lblImg, imgPanel, f1);
					if (pers.isEnabled()) {
						Transform.actualitzaTamanys(f1, pW, pH, tfamp, tflla);
					} else {
						android.setEnabled(true);
						pers.setEnabled(true);
					}
				} catch (IOException e) {
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

						Redimensionate.redimensionaAndroid(androidImgPaths, androidImgFolders, ic_launcher, f1, f2,
								APATH, Width, Height);

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else if (f1 != null && f2 != null && !isAndroid && pW > 0 && pH > 0) {
					try {
						Redimensionate.redimensionaPersonal(f2.getAbsolutePath(), pW, pH, f1);
					} catch (IOException e1) {
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
				Transform.actualitzaTamanys(f1, pW, pH, tfamp, tflla);

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
					f2 = Seek.buscaAndroidProject(tfDir, androidPath, jfcDir);
				} else if (!isAndroid && selected) {
					f2 = Seek.buscaCarpeta(tfDir, f1);
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
				if (imgRelative && Integer.parseInt(tfamp.getText()) != pW) {
					auxw = Integer.parseInt(tfamp.getText());
					auxh = Integer.parseInt(tflla.getText());
					pH = Calculate.calculaRelative(auxw, auxh, auxz);
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
				if (imgRelative && Integer.parseInt(tflla.getText()) != pH) {
					auxw = Integer.parseInt(tfamp.getText());
					auxh = Integer.parseInt(tflla.getText());
					pW = Calculate.calculaRelative(auxh, auxw, auxz);
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
			}
		});
		relButton.setBounds(276, 151, 25, 20);
		contentPane.add(relButton);

		btnAndPro = new JButton("");
		btnAndPro.setEnabled(false);
		btnAndPro.setIcon(new ImageIcon(Win.class.getResource("/img/IconaAnd.png")));
		btnAndPro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				androidPath = Seek.buscaAndroidStudioFolder(androidPath);
				if (androidPath != "") {
					btnBuscaDir.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null,
							"Fins que no hi poses una carpeta de Android no podràs seleccionar ningun projecte!");
				}
			}
		});
		btnAndPro.setBounds(220, 265, 25, 26);
		contentPane.add(btnAndPro);
		repainter();
	}
}
