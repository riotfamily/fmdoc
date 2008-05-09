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

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class VariableDoc extends TemplateElementDoc {

	private static final Pattern DECLARATION = 
			Pattern.compile("<#assign\\s+(\\w+)\\s*=\\s*(.+)\\s*/?>");
	
	private String name;
	
	private String value;
	
	public VariableDoc(TemplateDoc templateDoc, String comment, 
			String declaration) {
		
		super(templateDoc, comment);
		Matcher m = DECLARATION.matcher(declaration);
		if (m.matches()) {
			name = m.group(1);
			value = m.group(2);
		}
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

}
