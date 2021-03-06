package com.gui.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import com.chess.move.Position;
import com.gui.UIUtils;
import com.gui.interfaces.Clickable;
import com.gui.interfaces.Scrollable;
import com.main.Utils;

public class UITextArea extends UIObject implements Clickable, Scrollable {
	protected String text;
	private String[] displayText;
	private Position[] displayPositions;
	private int beginIndex, endIndex;
	private int scrollBarY, scrollBarWidth, scrollBarHeight;
	protected int hgap, vgap;
	protected boolean hovering = false;
	protected boolean lineWrap = true;
	protected Color scrollBarColor;

	public UITextArea() {
		this("");
	}

	public UITextArea(String text) {
		this(text, 10, 10);
	}

	public UITextArea(String text, int hgap, int vgap) {
		this.text = text;
		this.hgap = hgap;
		this.vgap = vgap;
		this.beginIndex = 0;
		this.endIndex = -1;
		this.scrollBarWidth = 5;
		scrollBarColor = new Color(63, 63, 63, 171);

		calculateTextPositions();
	}

	@Override
	public void render(Graphics g) {
		if (visible) {
			drawBackground();

			try {
				for (int i = 0; i <= endIndex - beginIndex; i++) {
					UIUtils.drawString(g, displayText[i + beginIndex], displayPositions[i].x, displayPositions[i].y,
							false, textColor, font);
				}
			} catch (Exception e) {
			}

			g.setColor(scrollBarColor);
			g.fillRoundRect(this.x + this.width - scrollBarWidth, scrollBarY, scrollBarWidth, scrollBarHeight, arcWidth,
					arcHeight);

			drawBorder();
		}
	}

	private void calculateTextPositions() {
		if (text != null && !text.equals("") && g != null) {
			float textHeight = (float) UIUtils.getStringHeight(font, g);
			int maxLines = (int) (height / (textHeight + vgap));
			int maxTextWidth = width - hgap * 2;

			if (lineWrap) {
				displayText = UIUtils.splitStringWhole(text, maxTextWidth, " ", font, g);
			} else {
				displayText = text.split("\n");

				for (int i = 0; i < displayText.length; i++) {
					displayText[i] = UIUtils.shortenText(displayText[i], maxTextWidth, font, g);
				}
			}

			beginIndex = Utils.clamp(beginIndex, 0, displayText.length - 1);

			if (displayText.length <= maxLines) {
				beginIndex = 0;
				endIndex = displayText.length - 1;

				scrollBarY = this.y;
				scrollBarHeight = this.height;
			} else {
				if (beginIndex + maxLines >= displayText.length) {
					beginIndex = displayText.length - maxLines;
					endIndex = displayText.length - 1;
				} else {
					endIndex = beginIndex + maxLines - 1;
				}

				scrollBarHeight = Math.round((float) maxLines / (float) displayText.length * (float) this.height);
				scrollBarY = Math.round((float) beginIndex / (float) displayText.length * (float) this.height) + this.y;
			}

			displayPositions = new Position[endIndex - beginIndex + 1];

			for (int i = 0; i <= endIndex - beginIndex; i++) {
				int y = Math.round(this.y + (textHeight + vgap) * (i + 1));

				switch (horizontalAlignment) {
				case LEFT:
					displayPositions[i] = new Position(x + hgap, y);
					break;
				case CENTER:
					displayPositions[i] = new Position(x + Math
							.round((width - (float) UIUtils.getStringWidth(displayText[i + beginIndex], font, g)) / 2f),
							y);
					break;
				case RIGHT:
					displayPositions[i] = new Position(
							x + width - hgap
									- Math.round((float) UIUtils.getStringWidth(displayText[i + beginIndex], font, g)),
							y);
					break;
				}
			}
		} else {
			displayText = new String[0];
			displayPositions = new Position[0];

			beginIndex = 0;
			endIndex = -1;
		}

		repaint();
	}

	@Override
	public void onMouseMove(MouseEvent e) {
		if (boundsContain(e.getX(), e.getY()) && enabled && visible)
			hovering = true;
		else
			hovering = false;
	}

	@Override
	public void onMouseRelease(MouseEvent e) {
	}

	@Override
	public void onMouseScroll(MouseWheelEvent e) {
		if (hovering && enabled && visible) {
			beginIndex += e.getWheelRotation();
			propertyChanged();
		}
	}

	public void clear() {
		text = "";
		propertyChanged();
	}

	@Override
	protected void propertyChanged() {
		calculateTextPositions();
		repaint();
	}

	// ===== Getters ===== \\
	public String getText() {
		return text;
	}

	public int getHgap() {
		return hgap;
	}

	public int getVgap() {
		return vgap;
	}

	public int getScrollBarWidth() {
		return scrollBarWidth;
	}

	public Color getScrollBarColor() {
		return scrollBarColor;
	}

	public boolean lineWrap() {
		return lineWrap;
	}

	// ===== Setters ===== \\
	public void setHgap(int hgap) {
		if (this.hgap != hgap) {
			this.hgap = hgap;
			propertyChanged();
		}
	}

	public void setVgap(int vgap) {
		if (this.vgap != vgap) {
			this.vgap = vgap;
			propertyChanged();
		}
	}

	public void setText(String text) {
		if (this.text == null || !this.text.equals(text)) {
			this.text = text;
			propertyChanged();
		}
	}

	public void setScrollBarWidth(int scrollBarWidth) {
		if (this.scrollBarWidth != scrollBarWidth) {
			this.scrollBarWidth = scrollBarWidth;
			propertyChanged();
		}
	}

	public void setScrollBarColor(Color scrollBarColor) {
		if (!this.scrollBarColor.equals(scrollBarColor)) {
			this.scrollBarColor = scrollBarColor;
			propertyChanged();
		}
	}

	public void setLineWrap(boolean lineWrap) {
		if (this.lineWrap != lineWrap) {
			this.lineWrap = lineWrap;
			propertyChanged();
		}
	}
}