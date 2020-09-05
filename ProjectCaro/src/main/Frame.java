package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import main.Graphics;
import main.MyImage;

public class Frame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width = Graphics.width;
	private int height = Graphics.height;

	private Graphics graphics;

	public static JLabel lbStatusO;
	public static JLabel lbStatusX;
	private JLabel lbNamePlayerO;
	private JLabel lbNamePlayerX;
	private JLabel lbScoreO;
	private JLabel lbScoreX;
	private ImageIcon iconPlayerO;
	private ImageIcon iconPlayerX;
	private int scoreO = 0, scoreX = 0;

	private String playerName1 = "Người chơi 1", playerName2 = "Người chơi 2";

	private MyImage myImage = new MyImage();
//	private PlayerName selectPlayerFrame;

	public Frame() {
		init();
	}

	private void init() {
		setTitle("Game caro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		initGraphics();
		setJMenuBar(createJMenuBar());
		add(createMainPainl());
		// JOptionPane.show

		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

//		selectPlayer();
	}

	private void initGraphics() {
		graphics = new Graphics();
		graphics.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				graphics.actionClick(e.getPoint());
				if (graphics.getWinner() > 0) {
					win(graphics.getWinner());
				}
			}
		});
	}

//	private void selectPlayer() {
//		if (selectPlayerFrame == null) {
//			selectPlayerFrame = new PlayerName(this);
//		}
//		selectPlayerFrame.setVisible(true);
//	}

//	public void updateStatus() {
//		playerName1 = selectPlayerFrame.getPlayerName1();
//		playerName2 = selectPlayerFrame.getPlayerName2();
//		caroGraphics.player = caroGraphics.playerRoot;
//		lbNamePlayerX.setText(playerName1);
//		lbNamePlayerO.setText(playerName2);
//		if (selectPlayerFrame.getStart() == 1) {
//			caroGraphics.playerRoot = true;
//		} else {
//			caroGraphics.playerRoot = false;
//		}
//		caroGraphics.player = caroGraphics.playerRoot;
//		caroGraphics.setStatus();
//		System.out.println("updated");
//	}

	private JMenuBar createJMenuBar() {
		JMenuBar mb = new JMenuBar();
		String[] game = { "Trò chơi mới", "Hiệp mới", "Hướng dẫn", "Giới thiệu", "", "Thoát" };
		mb.add(createJMenu("Menu", game, KeyEvent.VK_T));
//		String[] help = { "Hướng dẫn", "", "Giới thiệu" };
//		mb.add(createJMenu("Hướng dẫn", help, KeyEvent.VK_H));
		return mb;
	}

	private JMenu createJMenu(String menuName, String itemName[], int key) {
		JMenu m = new JMenu(menuName);
		m.addActionListener(this);
		m.setMnemonic(key);

		for (int i = 0; i < itemName.length; i++) {
			if (itemName[i].equals("")) {
				m.add(new JSeparator());
			} else {
				m.add(createJMenuItem(itemName[i]));
			}
		}

		return m;
	}

	private JMenuItem createJMenuItem(String itName) {
		JMenuItem mi = new JMenuItem(itName);
		mi.addActionListener(this);
		return mi;
	}

	private JPanel createMainPainl() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createPanelGraphics(), BorderLayout.CENTER);
		//panel.add(createSidebarPanel(true), BorderLayout.WEST);
		//panel.add(createSidebarPanel(false), BorderLayout.EAST);
		panel.add(createSidebarPanel2(false), BorderLayout.EAST);
		return panel;
	}

	private JPanel createPanelGraphics() {
		JPanel panelGraphics = new JPanel(null);
		panelGraphics.add(graphics, BorderLayout.CENTER);
		int bound = 10;
		graphics.setBounds(bound, bound, Graphics.width,
				Graphics.height);
		panelGraphics.setPreferredSize(new Dimension(Graphics.width + bound
				* 2, Graphics.height + bound * 2));

		panelGraphics.setBorder(new LineBorder(Color.black));
		//tạo viền xung quanh bàn cờ
	//	panelGraphics.setBackground(Color.black);
		return panelGraphics;
	}
//phần hiện thị thông tin người chơi// true hoặc false để hiển thị người chơi 1 hoặc 2
	private JPanel createSidebarPanel(boolean player) {
		JPanel panel = new JPanel(new BorderLayout());

		panel.add(createPanelStatus(player), BorderLayout.PAGE_START);

		panel.add(createPlayerPanel(player), BorderLayout.CENTER);

		panel.add(createPanelBottom(player), BorderLayout.PAGE_END);
		return panel;
	}
	/////nháp
	private JPanel createSidebarPanel2(boolean player) {
		JPanel panel = new JPanel(new BorderLayout());

		panel.add(createPanelStatus(player), BorderLayout.PAGE_START);

		panel.add(createPlayerPanel(player), BorderLayout.CENTER);

		panel.add(createPanelBottom(player), BorderLayout.PAGE_END);
		return panel;
	}

	////

	private JPanel createPanelStatus(boolean player) {
		JPanel panelStatus = new JPanel(new GridLayout(2, 1, 2, 2));
		JPanel panel1 = new JPanel();

		if (player) {
			lbStatusX = new JLabel();
			lbStatusX.setHorizontalAlignment(JLabel.CENTER);
			lbNamePlayerX = new JLabel("Người chơi 1");
			lbNamePlayerX.setHorizontalAlignment(JLabel.CENTER);

			lbScoreX = new JLabel("0");
			lbScoreX.setFont(lbScoreX.getFont().deriveFont(Font.PLAIN, 35f));
			lbScoreX.setForeground(Color.red);
			lbScoreX.setHorizontalAlignment(JLabel.CENTER);

			panel1.add(lbStatusX);
			panel1.add(lbNamePlayerX);
			panelStatus.add(panel1);
			panelStatus.add(lbScoreX);

		} else {
			JLabel lable = new JLabel("Lượt chơi");
			panel1.add(lable);

			panelStatus.add(panel1);
			
//			lbStatusO = new JLabel();
//			lbStatusO.setHorizontalAlignment(JLabel.CENTER);
//			lbNamePlayerO = new JLabel("Người chơi 1");
//			lbNamePlayerO.setHorizontalAlignment(JLabel.CENTER);
//
//			lbScoreO = new JLabel("0");
//			lbScoreO.setFont(lbScoreO.getFont().deriveFont(Font.PLAIN, 35f));
//			lbScoreO.setForeground(Color.blue);
//			lbScoreO.setHorizontalAlignment(JLabel.CENTER);
//
//			panel1.add(lbStatusO);
//			panel1.add(lbNamePlayerO);
//			panelStatus.add(panel1);
//			panelStatus.add(lbScoreO);
		}
		int bound = 1;
		//panelStatus.setBorder(new LineBorder(Color.green));
		panelStatus.setPreferredSize(new Dimension(width / 3, height / 6 - 25));
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(bound, bound, bound, bound));
		panel.add(panelStatus);
		return panel;
	}

	private JPanel createPlayerPanel(boolean player) {
		int boundw = 10;
		int boundh = 10;
		int h = height * 2 / 3 + boundh;
		int w = width / 3;
		/////////
		String imgPlayerO = "logoCaro.jpg";
		String imgPlayerX = "playerX.gif";
		iconPlayerO = new ImageIcon(myImage.resizeImg(
				myImage.getImageIcon(imgPlayerO), height/3 , height/3));
		iconPlayerX = new ImageIcon(myImage.resizeImg(
				myImage.getImageIcon(imgPlayerX), w - boundw, h - boundh));

		ImageIcon icon = player ? iconPlayerX : iconPlayerO;
		JLabel lbPlayer = new JLabel(icon);

		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(height/3, height/3));

		panel.add(lbPlayer, BorderLayout.CENTER);

		int bound = 1;
		//panel.setBorder(new LineBorder(Color.green));
		JPanel panel1 = new JPanel();
		panel1.setBorder(new EmptyBorder(bound, bound, bound, bound));
		panel1.add(panel);
		return panel1;
	}

	private JPanel createPanelBottom(boolean player) {
		String[] str1 = { "Đi lại", "Xin thua" };
		String[] str2 = { "Trò chơi mới","Chơi với máy","Chơi với người", "Hiệp mới", "Xin thua" };
		String[] str;
		if (player) {
			str = str1;
		} else {
			str = str2;
		}
		int size = str.length;
		JPanel panel = new JPanel(new GridLayout(size, 1, 5, 5));
		for (int i = 0; i < size; i++) {
			panel.add(createJButton(str[i]));
		}
		int bound = 1;
		//panel.setBorder(new LineBorder(Color.green));
		panel.setPreferredSize(new Dimension(width / 3, height / 3));
		JPanel panel1 = new JPanel();
		panel1.setBorder(new EmptyBorder(bound, bound, bound, bound));
		panel1.add(panel);
		return panel1;
	}

	private JButton createJButton(String btnName) {
		JButton btn = new JButton(btnName);
		btn.addActionListener(this);
		return btn;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command == "Trò chơi mới") {
			actionNewGame();
		}
		if (command == "Hiệp mới") {
			actionNewUnit();
		}
		if (command == "Thoát") {
			actionExit();
		}

		if (command == "Hướng dẫn") {
			actionHelp();
		}

		if (command == "Giới thiệu") {
			actionAbout();
		}

		if (command == "Đi lại") {
			actionUndo();
		}
		if (command == "Xin thua") {
			actionGiveIn();
		}
	}

	private void actionNewGame() {
		int select = showDialog("Các bạn thực sự muốn tạo trò chơi mới?",
				"Trò chơi mới");
		if (select == 0) {
			scoreO = 0;
			scoreX = 0;
			clear();
		}
	}

	private void actionNewUnit() {
		int select = showDialog("Các bạn thực sự muốn tạo hiệp mới?",
				"Hiệp mới");
		if (select == 0) {
			clear();
		}
	}

	private void actionExit() {
		int select = showDialog("Các bạn thực sự muốn thoát?", "Thoát");
		if (select == 0) {
			System.exit(0);
		}
	}

	private void actionHelp() {
		showDialog("Luật chơi là: đánh 5 quân liên tiếp liền kề nhau là thắng!",
				"Hướng dẫn");
	}

	private void actionAbout() {
		showDialog("Game caro này được thực hiện bởi: Trần Quốc Thuận và Trương Quý Đức."+ "\n"
				+ "Dưới sự hướng dẫn bởi gv: Khương Hải Châu.",
				"Giới thiệu");
	}

	private void actionUndo() {
		graphics.undo();
	}

	private void actionGiveIn() {
		int sO = 0, sX = 0;
		String playerName = "";
		if (graphics.player) {
			sO = 1;
			playerName = playerName1;
		} else {
			sX = 1;
			playerName = playerName2;
		}

		int select = showDialog(playerName + " thực sự muốn xin thua?",
				"Trò chơi mới");
		if (select == 0) {
			scoreO += sO;
			scoreX += sX;
			clear();
		}
	}

	private int showDialog(String message, String title) {
		int select = JOptionPane.showOptionDialog(null, message, title,
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				null, null);
		return select;
	}

	private void clear() {
		graphics.init();
	//	updateScore();
//		selectPlayer();
//		graphics.setStatus();
	}

	private void updateScore() {
		lbScoreO.setText(scoreO + "");
		lbScoreX.setText(scoreX + "");
	}

	private void win(int winer) {
		String playerName = "";
		if (winer == 1) {
			scoreX++;
			playerName = playerName1;
		} else {
			scoreO++;
			playerName = playerName2;
		}
		Object[] options = { "Trò chơi mới", "Hiệp mới", "Thoát" };
		int select = JOptionPane.showOptionDialog(this, "Chúc mừng "
				+ playerName + " đã chiến thắng trong hiệp đấu "
				+ (scoreO + scoreX), "A Silly Question",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[options.length - 1]);
		if (select == 2) {
			actionExit();
		} else if (select == 0) {
			scoreO = 0;
			scoreX = 0;
		}
		clear();
	}
}



