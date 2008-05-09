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


import freemarker.core.Macro;

public class FunctionDoc extends MacroDoc {

	public FunctionDoc(TemplateDoc templateDoc, String comment, Macro macro) {
		super(templateDoc, comment, macro);
	}

	public String getSignature() {
        StringBuffer sb = new StringBuffer();
        sb.append(getName());
        sb.append("(");
        String args[] = getMacro().getArgumentNames();
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
                sb.append(", ");
            }
        }
        if (getMacro().getCatchAll() != null) {
        	sb.append(", ");
        	sb.append(getMacro().getCatchAll());
        	sb.append("...");
        }
        sb.append(')');
        return sb.toString();
    }
}
