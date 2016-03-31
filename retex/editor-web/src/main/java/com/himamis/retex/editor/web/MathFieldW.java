/**
 * This file is part of the ReTeX library - https://github.com/himamis/ReTeX
 *
 * Copyright (C) 2015 Balazs Bencze
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * A copy of the GNU General Public License can be found in the file
 * LICENSE.txt provided with the source distribution of this program (see
 * the META-INF directory in the source jar). This license can also be
 * found on the GNU website at http://www.gnu.org/licenses/gpl.html.
 *
 * If you did not receive a copy of the GNU General Public License along
 * with this program, contact the lead developer, or write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */
package com.himamis.retex.editor.web;


import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.HTML;
import com.himamis.retex.editor.share.editor.MathField;
import com.himamis.retex.editor.share.editor.MathFieldInternal;
import com.himamis.retex.editor.share.event.ClickListener;
import com.himamis.retex.editor.share.event.FocusListener;
import com.himamis.retex.editor.share.event.KeyEvent;
import com.himamis.retex.editor.share.event.KeyListener;
import com.himamis.retex.editor.share.meta.MetaModel;
import com.himamis.retex.editor.share.meta.MetaModelParser;
import com.himamis.retex.editor.share.model.MathFormula;
import com.himamis.retex.renderer.share.TeXIcon;
import com.himamis.retex.renderer.share.platform.Resource;
import com.himamis.retex.renderer.web.JlmLib;

public class MathFieldW implements MathField {

	private static final MetaModel metaModel;

	static {
		metaModel = new MetaModelParser().parse(new Resource().loadResource(
				"/com/himamis/retex/editor/desktop/meta/Octave.xml"));
	}


	private MathFieldInternal mathFieldInternal;
	private HTML html;
	private Context2d ctx;

	public MathFieldW(Element el, Context2d context) {
		html = HTML.wrap(el);
		el.setTabIndex(1);
		this.ctx = context;
		mathFieldInternal = new MathFieldInternal(this);
		mathFieldInternal.setFormula(MathFormula.newFormula(metaModel));
	}

	@Override
	public void setTeXIcon(TeXIcon icon) {
		ctx.setFillStyle("rgb(255,255,255)");
		ctx.fillRect(0, 0, ctx.getCanvas().getWidth(),
				ctx.getCanvas().getHeight());
		JlmLib.draw(icon, ctx, 0, 0, "#000000", "#FFFFFF", null);
	}

	@Override
	public void setFocusListener(FocusListener focusListener) {
		// addFocusListener(new FocusListenerAdapterW(focusListener));
	}

	@Override
	public void setClickListener(ClickListener clickListener) {
		// addMouseListener(new ClickListenerAdapterW(clickListener));
	}

	@Override
	public void setKeyListener(final KeyListener keyListener) {
		html.addDomHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent event) {
				keyListener.onKeyTyped(
						new KeyEvent(event.getNativeEvent().getKeyCode(), 0,
								event.getCharCode()));

			}
		}, KeyPressEvent.getType());
		html.addDomHandler(new KeyUpHandler() {

			public void onKeyUp(KeyUpEvent event) {
				int code = event.getNativeEvent().getKeyCode();
				keyListener.onKeyPressed(
						new KeyEvent(code, 0,
								getChar(event.getNativeEvent())));
				if (code == 8 || code == 27) {
					event.preventDefault();
				}

			}
		}, KeyUpEvent.getType());
		html.addDomHandler(new KeyDownHandler() {

			public void onKeyDown(KeyDownEvent event) {
				int code = event.getNativeEvent().getKeyCode();
				keyListener.onKeyReleased(
						new KeyEvent(code, 0,
								getChar(event.getNativeEvent())));
				if (code == 8 || code == 27) {
					event.preventDefault();
				}

			}
		}, KeyDownEvent.getType());

	}

	protected char getChar(NativeEvent nativeEvent) {
		return 0;
	}

	@Override
	public boolean hasParent() {
		return false;
	}

	@Override
	public void requestViewFocus() {
		html.getElement().focus();
	}

	@Override
	public void requestLayout() {

	}

	public MetaModel getMetaModel() {
		return metaModel;
	}

	public void repaint() {
		// TODO Auto-generated method stub

	}

	public boolean hasFocus() {
		return true;
	}
}