package com.zine.zinemobsamples.midlets;

import com.zine.zinemob.ZineMIDlet;
import com.zine.zinemob.drawableelement.DrawableElement;
import com.zine.zinemob.drawableelement.tilesmap.TilesMap;
import com.zine.zinemob.scene.Scene;
import com.zine.zinemob.scene.controller.SceneController;
import com.zine.zinemob.scene.controller.KeyboardListener;
import com.zine.zinemob.scene.controller.Updateble;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

public class TilesMapLineSegmentCollisionSample extends ZineMIDlet {
	
	private static class LineSegment extends DrawableElement {
		
		private int lineWidth = 100, lineHeight = 100;

		protected void drawElement(Graphics graphics) {
			graphics.setColor(255, 0, 0);
			graphics.drawLine(0, 0, lineWidth, lineHeight);
		}

		public int getLineWidth() {
			return lineWidth;
		}

		public void setLineWidth(int x2) {
			this.lineWidth = x2;
		}

		public int getLineHeight() {
			return lineHeight;
		}

		public void setLineHeight(int y2) {
			this.lineHeight = y2;
		}
	}
	
	private static class LineSegmentController extends SceneController implements KeyboardListener {
		
		private LineSegment myLineSegment;
		boolean positionMode = false;
		
		public LineSegmentController(LineSegment myLineSegment) {
			this.myLineSegment = myLineSegment;
		}

		public void onKeyPressed(int keyCode, int gameAction) {
			if (gameAction == GameCanvas.FIRE) {
				positionMode = !positionMode;
			}
		}

		public void onKeyRepeated(int keyCode, int gameAction) {
		}

		public void onKeyReleased(int keyCode, int gameAction) {
		}

		public void updateKeyStates(int keyStates) {
			int speed = 2;
			if (positionMode) {
				if ((keyStates & GameCanvas.DOWN_PRESSED) != 0) {
					myLineSegment.setPosition(myLineSegment.getX(), myLineSegment.getY() + speed);
				}
				if ((keyStates & GameCanvas.UP_PRESSED) != 0) {
					myLineSegment.setPosition(myLineSegment.getX(), myLineSegment.getY() - speed);
				}
				if ((keyStates & GameCanvas.LEFT_PRESSED) != 0) {
					myLineSegment.setPosition(myLineSegment.getX() - speed, myLineSegment.getY());
				}
				if ((keyStates & GameCanvas.RIGHT_PRESSED) != 0) {
					myLineSegment.setPosition(myLineSegment.getX() + speed, myLineSegment.getY());
				}
			} else {
				if ((keyStates & GameCanvas.DOWN_PRESSED) != 0) {
					myLineSegment.setLineHeight(myLineSegment.getLineHeight() + speed);
				}
				if ((keyStates & GameCanvas.UP_PRESSED) != 0) {
					myLineSegment.setLineHeight(myLineSegment.getLineHeight() - speed);
				}
				if ((keyStates & GameCanvas.LEFT_PRESSED) != 0) {
					myLineSegment.setLineWidth(myLineSegment.getLineWidth() - speed);
				}
				if ((keyStates & GameCanvas.RIGHT_PRESSED) != 0) {
					myLineSegment.setLineWidth(myLineSegment.getLineWidth() + speed);
				}
			}
		}
	}
	
	private static class CollisionController extends SceneController implements Updateble {
		
		private LineSegment lineSegment;
		private TilesMap tilesMap;
		
		public CollisionController(LineSegment lineSegment, TilesMap tilesMap) {
			this.lineSegment = lineSegment;
			this.tilesMap = tilesMap;
		}

		public void update() {
			clearTiledLayer();
			changeCollidedTiles();
		}
		
		private void clearTiledLayer() {
			tilesMap.getTiledLayer().fillCells(0, 0, 5, 5, 2);
		}
		
		private void changeCollidedTiles() {
			
			int[] cellIndexes = tilesMap.getCellIndexesAtLineSegment(lineSegment.getX(),
					lineSegment.getY(),
					lineSegment.getX() + lineSegment.getLineWidth(),
					lineSegment.getY() + lineSegment.getLineHeight(),
					true);
			
			for (int i=0; i<cellIndexes.length; i++) {
				tilesMap.getTiledLayer().setCell(tilesMap.getCellColumnByCellIndex(cellIndexes[i]), tilesMap.getCellRowByCellIndex(cellIndexes[i]), 1);
			}
		}
		
	}
	
	public void run() {
		
		try {
			TilesMap tilesMap = new TilesMap(5, 5, Image.createImage("/com/zine/zinemobsamples/res/tilesset.png"), 50, 50);
			LineSegment lineSegment = new LineSegment();
			LineSegmentController lineSegmentController = new LineSegmentController(lineSegment);
			CollisionController collisionController = new CollisionController(lineSegment, tilesMap);
			
			Scene scene = new Scene();
			scene.getScreenElement().addChild(tilesMap);
			scene.getScreenElement().addChild(lineSegment);
			scene.addController(lineSegmentController);
			scene.addController(collisionController);
			scene.run();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
