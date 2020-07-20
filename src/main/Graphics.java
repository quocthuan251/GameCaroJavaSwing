package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.UndoManager;

public class Graphics extends JPanel{
	private static final long serialVersionUID = 1L;
	public static final int sizeCell = 30;
	public static final int row = 18;
	public static final int col = 18;
	public static final int width = sizeCell * col +1;
	public static final int height = sizeCell * row +1;
	
	private int sizeImg = sizeCell -2;
	public boolean player, playerRoot;
	private Process process;
	
	private MyImage myImg = new MyImage();
	private Icon iconActive;
	private UndoManager undoManager = new UndoManager();
	private Vector<Point> pointVector;
	
	private int winner = 0;

	public Graphics(){
		makeIcon();
		
		setPreferredSize(new Dimension(width,height));
		init();
	}
	
	public void init() {
		winner = 0;
		process = new Process();
		player = playerRoot;
		pointVector = new Vector<Point>();
		repaint();
	}

	private void makeIcon() {
		setIconActive(new ImageIcon(myImg.resizeImg(myImg.getImageIcon("active.png"), 20, 20)));
	}

	public void paintComponent(java.awt.Graphics g){
		super.paintComponent(g);
		setBackground(new Color(238, 238, 238));
		for(int i = 0; i <= row; i++){
			g.drawLine(i * sizeCell,  0,  i * sizeCell, height -1);
			g.drawLine(0,  i * sizeCell, width -1, i * sizeCell);
		}
		drawImg(g);
		System.out.println("a");
	}
	
	private void drawImg(java.awt.Graphics g) {
		boolean player = playerRoot;
		for(int i = 0; i < pointVector.size(); i++){
			Image img = player ? myImg.getImgCross() : myImg.getImgNought();
			Point point = convertPointToCaro(convertPoint(pointVector.get(i)));
			g.drawImage(img, point.x, point.y, null);
			player =! player;
		}
	}

	private Point convertPoint(Point point){
		//System.out.println(point);
		int x, y;
		int deviation = 1;
		x = (point.x % sizeCell > deviation) ? (point.x/sizeCell * sizeCell + sizeCell/2): (point.x/sizeCell * sizeCell -sizeCell /2);
		y = (point.y %sizeCell > deviation) ? (point.y/sizeCell * sizeCell + sizeCell/2): (point.y/sizeCell * sizeCell - sizeCell/2);
		//System.out.println("x: " +x + "y: "+ y);
		return new Point(x, y);
	}
	
	private Point convertPointToMaxtrix(Point point) {
		return new Point(point.y / sizeCell, point.x / sizeCell);
	}
	
	private Point convertPointToCaro(Point point) {
		return new Point(point.x - sizeImg / 2, point.y - sizeImg / 2);
	}
	
	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}
	// khi nhấn click thì đổi lượt chơi cho người tiếp theo
	void actionClick(Point point){
		Point pointTemp = convertPoint(point);
		if(process.updateMatrix(player, convertPointToMaxtrix(pointTemp))){
			pointVector.addElement(point);
			undoManager.undoableEditHappened(new UndoableEditEvent(this, new UndoablePaintSquare(pointVector, point)));
			repaint();
			player =! player;
			if(process.getWin() > 0){
				winner = process.getWin();
			}
		}
	}
// chưa thực hiện được chức năng quay lại 
	public void undo(){
		player =! player;
		Point point = pointVector.get(pointVector.size() - 1);
		point = convertPointToMaxtrix(convertPoint(point));
		process.undoMatrix(point);
		repaint();
	}
	public boolean canUndo(){
		return undoManager.canUndo();
	}

	public Icon getIconActive() {
		return iconActive;
	}

	public void setIconActive(Icon iconActive) {
		this.iconActive = iconActive;
	}
}
