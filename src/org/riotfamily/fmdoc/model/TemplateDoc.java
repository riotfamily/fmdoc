/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is fmdoc.
 *
 * The Initial Developer of the Original Code is
 * Neteye GmbH.
 * Portions created by the Initial Developer are Copyright (C) 2008
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *   Felix Gnass [fgnass at neteye dot de]
 *   Thorben Schroeder [stillepost at gmail dot com]
 * 
 * ***** END LICENSE BLOCK ***** */
package org.riotfamily.fmdoc.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;



import freemarker.core.Comment;
import freemarker.core.Macro;
import freemarker.core.TemplateElement;
import freemarker.template.Template;
import freemarker.template.TemplateModelException;

public class TemplateDoc {

	private String name;
	
	private TemplateElementDoc comment;
	
	private List variables;
	
	private List macros;
	
	private String href;

	public TemplateDoc(Template template, String href) throws TemplateModelException {
		this.name = template.getName();
		this.href = href;
		String text = null;
		TemplateElement root = template.getRootTreeNode();
		Enumeration children =  root.children();
		for (int i = 0; children.hasMoreElements(); i++) {
			TemplateElement child = (TemplateElement) children.nextElement();
			if (child instanceof Comment) {
				Comment c = (Comment) child;
				String s = c.getText(); 
				if (s.startsWith("-")) {
					s = s.substring(1);
					if (i == 0) {
						comment = new TemplateDescription(this, s);
					}
					else {
						text = s;
					}
				}
			}
			else if (child instanceof Macro) {
				Macro macro = (Macro) child;
				if (macro.isFunction()) {
					addMacro(new FunctionDoc(this, text, macro));
				}
				else {
					addMacro(new MacroDoc(this, text, macro));
				}
				text = null;
			}
			else {
				String s = child.getCanonicalForm();
				if (s.startsWith("<#assign")) {
					addVariable(new VariableDoc(this, text, s));
					text = null;
				}
			}
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getNamespace() {
		return comment.get("namespace");
	}
	
	public TemplateElementDoc getComment() {
		return comment;
	}

	public List getVariables() {
		return variables;
	}

	private void addVariable(TemplateElementDoc doc) {
		if (!doc.isInternal()) {
			if (variables == null) {
				variables = new ArrayList();
			}
			variables.add(doc);
		}
	}

	public List getMacros() {
		return macros;
	}

	private void addMacro(TemplateElementDoc doc) {
		if (!doc.isInternal()) {
			if (macros == null) {
				macros = new ArrayList();
			}
			macros.add(doc);
		}
	}
		
	public String getHref() {
		return href;
	}
	
	public void addToIndex(Index index) {
		index.add(comment);
		index.addAll(variables);
		index.addAll(macros);
	}
}