package com.gui.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.gui.UIUtils;
import com.gui.interfaces.Clickable;
import com.gui.interfaces.Typeable;

public class UITextField extends UIObject implements Clickable, Typeable {
	private String acceptedKeys = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ,;.:-_#'+*~!\"�$%&/\\(){}[]=�?<>|������";
	private String text = "";
	private String end = "";
	private int textX, textY;
	private boolean hovering = false;
	private boolean selected = false;

	public UITextField() {
		border = Color.black;
	}

	@Override
	public void render(Graphics g) {
		if (visible) {
			drawBackground();
			UIUtils.drawString(g, text + end, textX, textY, true, textColor, font);
			drawBorder();
		}
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		if (!selected)
			return;

		if (acceptedKeys.indexOf(e.getKeyChar()) != -1) {
			if (UIUtils.getStringWidth(text + e.getKeyChar() + end, font, g) > width - 4)
				return;

			text += e.getKeyChar();
			propertyChanged();
		} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && !text.isEmpty()) {
			text = text.substring(0, text.length() - 1);
			propertyChanged();
		}
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
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
		if (e.getButton() == MouseEvent.BUTTON1 && hovering && enabled && visible) {
			selected = true;
			end = "_";
		} else {
			selected = false;
			end = "";
		}

		propertyChanged();
	}

	protected void calculateTextPos() {
		double textWidth;
		if (g != null)
			textWidth = UIUtils.getStringWidth(text + end, font, g);
		else
			textWidth = UIUtils.getStringWidth(text + end, font);

		textX = (int) (x + textWidth / 2);
		textY = y + height / 2;
		if (border != null)
			textX += 2;
	}

	@Override
	public void propertyChanged() {
		calculateTextPos();
		repaint();
	}

	// ===== Getter ===== \\
	public String getText() {
		return text;
	}

	public String getAcceptedKeys() {
		return acceptedKeys;
	}

	// ===== Setter ===== \\
	public void setText(String text) {
		if (this.text == null || !this.text.equals(text)) {
			this.text = text;
			propertyChanged();
		}
	}

	public void setAcceptedKeys(String acceptedKeys) {
		this.acceptedKeys = acceptedKeys;
	}
}