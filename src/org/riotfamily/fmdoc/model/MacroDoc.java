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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import freemarker.core.Macro;

public class MacroDoc extends TemplateElementDoc {
	
	private Macro macro;
	
	private Map defaultValues;
	
	private List parameters;
	
	public MacroDoc(TemplateDoc templateDoc, String comment, Macro macro) {
		super(templateDoc, comment);
		this.macro = macro;
		lookUpDefaultValues();
		processParameters(getTags("param"));
	}
	
	protected Macro getMacro() {
		return macro;
	}

	/**
	 * The Macro class does not expose the map of default values so we
	 * have to obtain them via reflection.
	 */
	private void lookUpDefaultValues() {
		try {
			Field args = macro.getClass().getDeclaredField("args");
			args.setAccessible(true);
			defaultValues = (Map) args.get(macro); 
		}
		catch (Exception e) {
		}
	}
	
	protected String getDefaultValue(String arg) {
		if (defaultValues == null) {
			return null;
		}
		Object value = defaultValues.get(arg);
		return value != null ? value.toString() : null;
	}
	
	private void processParameters(List comments) {
		HashMap descriptions = new HashMap();
		if (comments != null) {
			Iterator it = comments.iterator();
			while (it.hasNext()) {
				String comment = (String) it.next();
				Matcher m = Pattern.compile("(\\w+)\\s+(.+)").matcher(comment);
				if (m.matches()) {
					descriptions.put(m.group(1), m.group(2));
				}		
			}
		}
		String[] names = macro.getArgumentNames();
		if (names != null && names.length > 0) {
			parameters = new ArrayList();
			for (int i = 0; i < names.length; i++) {
				String name = names[i];
				String description = (String) descriptions.get(name);
				parameters.add(new Parameter(name, description, getDefaultValue(name)));	
			}
		}
	}
		
	public String getName() {
		return macro.getName();
	}
	
	public String getSignature() {
        StringBuffer sb = new StringBuffer("&lt;@");
        sb.append(getName());
        sb.append(" ");
        String args[] = macro.getArgumentNames();
        int size = args.length;
        for (int i = 0; i < size; i++) {
        	String arg = args[i]; 
            sb.append(arg);
            Object defaultValue = getDefaultValue(arg);
            if (defaultValue != null) {
            	sb.append('=');
            	sb.append(defaultValue);
            }
            if (i != (size-1)) {
                sb.append(" ");
            }
        }
        if (macro.getCatchAll() != null) {
        	sb.append("	 ");
        	sb.append(macro.getCatchAll());
        	sb.append("...");
        }
        sb.append(" /&gt;");
        return sb.toString();
    }

	public boolean isFunction() {
		return macro.isFunction();
	}
	
	public List getParameters() {
		return parameters;
	}
	
	public static class Parameter {

		private String name;
		
		private String description;
		
		private String defaultValue;

		public Parameter(String name, String description, String defaultValue) {
			this.name = name;
			this.description = description;
			this.defaultValue = defaultValue;
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}

		public String getDefaultValue() {
			return defaultValue;
		}
		
		public boolean isRequired() {
			return defaultValue == null;
		}
	}
	
}
