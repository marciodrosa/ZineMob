package com.sinosarts.zinemob.drawableelement;

import javax.microedition.lcdui.Graphics;
import com.sinosarts.zinemob.core.ZineManager;
import com.sinosarts.zinemob.core.ValuesInterpolation;

public class CurtainEffect extends DrawableElement {
	
	public static final byte OPEN = 0;
	public static final byte CLOSE = 1;

	public static final byte CENTER = 0;
	public static final byte CENTER_H = 1;
	public static final byte CENTER_V = 2;
	public static final byte TOP = 3;
	public static final byte BOTTOM = 4;
	public static final byte LEFT = 5;
	public static final byte RIGHT = 6;
	public static final byte TOP_LEFT = 7;
	public static final byte TOP_RIGHT = 8;
	public static final byte BOTTOM_LEFT = 9;
	public static final byte BOTTOM_RIGHT = 10;

	private int color = 0x000000;
	private byte mode = OPEN;
	private byte movement = TOP;
	private int delay = 0;
	private boolean inverse = false;

	private int frames = 10;
	private int frameCount = 0;

	private int curtainW = ZineManager.getInstance().getWidth();
	private int curtainH = 20;

	private int rowCount, collumnCount;

	private ValuesInterpolation x1, x2, y1, y2;

	public CurtainEffect() {
		updateRowAndCollumnCount();
		initCurtainAnnimation();
	}

	public CurtainEffect(byte mode, byte movement, int vCount, int hCount) {
		this.mode = mode;
		this.movement = movement;
		setHorizontalCount(hCount);
		setVerticalCount(vCount);
	}

	private void initCurtainAnnimation() {
		
		int left = -(curtainW/2);
		int right = (curtainW/2) + 1;
		int top = -(curtainH/2);
		int bottom = (curtainH/2) + 1;

		switch (movement) {
			case CENTER:
				this.x1 = new ValuesInterpolation(0, left, frames);
				this.x2 = new ValuesInterpolation(0, right, frames);
				this.y1 = new ValuesInterpolation(0, top, frames);
				this.y2 = new ValuesInterpolation(0, bottom, frames);
				break;
			case CENTER_H:
				this.x1 = new ValuesInterpolation(0, left, frames);
				this.x2 = new ValuesInterpolation(0, right, frames);
				this.y1 = new ValuesInterpolation(top, top, frames);
				this.y2 = new ValuesInterpolation(bottom, bottom, frames);
				break;
			case CENTER_V:
				this.x1 = new ValuesInterpolation(left, left, frames);
				this.x2 = new ValuesInterpolation(right, right, frames);
				this.y1 = new ValuesInterpolation(0, top, frames);
				this.y2 = new ValuesInterpolation(0, bottom, frames);
				break;
			case TOP:
				this.x1 = new ValuesInterpolation(left, left, frames);
				this.x2 = new ValuesInterpolation(right, right, frames);
				this.y1 = new ValuesInterpolation(top, top, frames);
				this.y2 = new ValuesInterpolation(top, bottom, frames);
				break;
			case BOTTOM:
				this.x1 = new ValuesInterpolation(left, left, frames);
				this.x2 = new ValuesInterpolation(right, right, frames);
				this.y1 = new ValuesInterpolation(bottom, top, frames);
				this.y2 = new ValuesInterpolation(bottom, bottom, frames);
				break;
			case LEFT:
				this.x1 = new ValuesInterpolation(left, left, frames);
				this.x2 = new ValuesInterpolation(left, right, frames);
				this.y1 = new ValuesInterpolation(top, top, frames);
				this.y2 = new ValuesInterpolation(bottom, bottom, frames);
				break;
			case RIGHT:
				this.x1 = new ValuesInterpolation(right, left, frames);
				this.x2 = new ValuesInterpolation(right, right, frames);
				this.y1 = new ValuesInterpolation(top, top, frames);
				this.y2 = new ValuesInterpolation(bottom, bottom, frames);
				break;
			case TOP_LEFT:
				this.x1 = new ValuesInterpolation(left, left, frames);
				this.x2 = new ValuesInterpolation(left, right, frames);
				this.y1 = new ValuesInterpolation(top, top, frames);
				this.y2 = new ValuesInterpolation(top, bottom, frames);
				break;
			case TOP_RIGHT:
				this.x1 = new ValuesInterpolation(left, right, frames);
				this.x2 = new ValuesInterpolation(right, right, frames);
				this.y1 = new ValuesInterpolation(top, top, frames);
				this.y2 = new ValuesInterpolation(top, bottom, frames);
				break;
			case BOTTOM_LEFT:
				this.x1 = new ValuesInterpolation(left, left, frames);
				this.x2 = new ValuesInterpolation(left, right, frames);
				this.y1 = new ValuesInterpolation(bottom, top, frames);
				this.y2 = new ValuesInterpolation(bottom, bottom, frames);
				break;
			case BOTTOM_RIGHT:
				this.x1 = new ValuesInterpolation(right, left, frames);
				this.x2 = new ValuesInterpolation(right, right, frames);
				this.y1 = new ValuesInterpolation(bottom, top, frames);
				this.y2 = new ValuesInterpolation(bottom, bottom, frames);
				break;
		}

		// se está abrindo ao invés de fechando, inverte a animação:
		if (mode == OPEN) {
			int temp = x1.getInitialValue();
			x1.setInitialValue(x1.getFinalValue());
			x1.setFinalValue(temp);

			temp = x2.getInitialValue();
			x2.setInitialValue(x2.getFinalValue());
			x2.setFinalValue(temp);

			temp = y1.getInitialValue();
			y1.setInitialValue(y1.getFinalValue());
			y1.setFinalValue(temp);

			temp = y2.getInitialValue();
			y2.setInitialValue(y2.getFinalValue());
			y2.setFinalValue(temp);
		}

	}

	private void updateRowAndCollumnCount() {
		rowCount = (ZineManager.getInstance().getHeight() / curtainH);
		collumnCount = (ZineManager.getInstance().getWidth() / curtainW);

		if (rowCount % ZineManager.getInstance().getHeight() > 0)
			rowCount++;
		if (collumnCount % ZineManager.getInstance().getWidth() > 0)
			collumnCount++;
	}

	public void reset() {
		frameCount = 0;
	}

	public boolean wasFinished() {
		int delayIndex = (collumnCount+rowCount)*delay;
		return frameCount > (frames + delayIndex );
	}
	
	protected void drawNode (Graphics g) {

		g.setColor(color);

		boolean inverseCurtain = false;

		for (int i=0; i<rowCount; i++) {
			for (int j=0; j<collumnCount; j++) {

				if (inverse) {
					if ((j%2 == 0 && i%2 == 1) || (j%2 == 1 && i%2 == 0))
						inverseCurtain = true;
					else
						inverseCurtain = false;
				}

				int index = frameCount - ((i + j) * delay);
				
				int x = x1.getValueAt(index);
				int y = y1.getValueAt(index);
				int w = x2.getValueAt(index) - x;
				int h = y2.getValueAt(index) - y;
				x += (curtainW/2) + (j * curtainW);
				y += (curtainH/2) + (i * curtainH);

				if (inverseCurtain) {
					x += curtainW - w;
					y += curtainH - h;
				}

				g.fillRect(x, y, w, h);
			}
		}

		frameCount++;
	}

	/**
	 * @return the color
	 */
	public int getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(int color) {
		this.color = color;
	}

	/**
	 * @return the mode
	 */
	public byte getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(byte mode) {
		this.mode = mode;
		initCurtainAnnimation();
	}

	/**
	 * @return the movement
	 */
	public byte getMovement() {
		return movement;
	}

	/**
	 * @param movement the movement to set
	 */
	public void setMovement(byte movement) {
		this.movement = movement;
		initCurtainAnnimation();
	}

	/**
	 * @return the delay
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * @param delay the delay to set
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}

	/**
	 * @return the inverse
	 */
	public boolean isInverse() {
		return inverse;
	}

	/**
	 * @param inverse the inverse to set
	 */
	public void setInverse(boolean inverse) {
		this.inverse = inverse;
	}

	/**
	 * @return the courtainW
	 */
	public int getCourtainWidth() {
		return curtainW;
	}

	/**
	 * @param courtainW the courtainW to set
	 */
	public void setCourtainWidth(int courtainW) {
		this.curtainW = courtainW;
		updateRowAndCollumnCount();
		initCurtainAnnimation();
	}

	/**
	 * @return the courtainH
	 */
	public int getCourtainHeight() {
		return curtainH;
	}

	/**
	 * @param courtainH the courtainH to set
	 */
	public void setCourtainHeight(int courtainH) {
		this.curtainH = courtainH;
		updateRowAndCollumnCount();
		initCurtainAnnimation();
	}

	public void setVerticalCount(int count) {
		curtainH = ZineManager.getInstance().getHeight() / count;
		updateRowAndCollumnCount();
		initCurtainAnnimation();
	}

	public void setHorizontalCount(int count) {
		curtainW = ZineManager.getInstance().getWidth() / count;
		updateRowAndCollumnCount();
		initCurtainAnnimation();
	}

	/**
	 * @return the frames
	 */
	public int getFrames() {
		return frames;
	}

	/**
	 * @param frames the frames to set
	 */
	public void setFrames(int frames) {
		this.frames = frames;
		initCurtainAnnimation();
	}

}
