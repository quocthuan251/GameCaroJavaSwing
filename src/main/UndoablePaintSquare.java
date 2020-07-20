package main;

import java.awt.Point;
import java.util.Vector;

import javax.swing.undo.AbstractUndoableEdit;

public class UndoablePaintSquare extends AbstractUndoableEdit {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Vector<Point> points;
	protected Point point;
	public UndoablePaintSquare(Vector<Point> points, Point point) {
		super();
		this.points = points;
		this.point = point;
	}
	
	public String getPresentationName(){
		return "Square Addition";
	}
	
	public void undo(){
		super.undo();
		points.remove(point);
	}

}
