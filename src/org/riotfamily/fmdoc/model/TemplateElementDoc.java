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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class TemplateElementDoc implements Comparable {
	
	/* Pattern to strip leading dashes from a comment line */
	private static final Pattern LINE_START = 
			Pattern.compile("^\\s*-+\\s?", Pattern.MULTILINE);

	/* Pattern to extract doc-tags like @since */
	private static final Pattern DOC_TAG = 
			Pattern.compile("(?:^|\\n)\\s*@(\\w+)\\s+([\\w\\W]+?)?\\s*(?=\\n@|$)");
	
	/* Pattern to strip everything starting at the first doc-tag */
	private static final Pattern TAGS = 
			Pattern.compile("^\\s*@[\\w\\W]*", Pattern.MULTILINE);
	
	/* Pattern to extract the first sentence of the description */
	private static final Pattern SHORT_DESCRIPTION = 
			Pattern.compile("^(?:<.*?>)?([\\W\\w]*?(\\.\\s(?=[^a-z])|\\.$|$))");
	
	private TemplateDoc templateDoc;
	
	/** The text up to the first doc-tag */
	private String description;
	
	/** The first sentence of the description */
	private String shortDescription;
	
	/** Map of Lists of Strings, keyed by tag name */
	private Map tags = new HashMap();
	
	public TemplateElementDoc(TemplateDoc templateDoc, String comment) {
		this.templateDoc = templateDoc;
		if (comment != null) {
			comment = LINE_START.matcher(comment).replaceAll("");
			description = TAGS.matcher(comment).replaceAll("");
			Matcher m = SHORT_DESCRIPTION.matcher(description);
			if (m.find()) {
				shortDescription = m.group(1);
			}
			m = DOC_TAG.matcher(comment);
			while (m.find()) {
				String s = m.group(2);
				if (s != null) {
					s = s.replace('\n', ' ');
				}
				else {
					s = "";
				}
				addTag(m.group(1), s);
			}
		}
	}
	
	protected TemplateDoc getTemplateDoc() {
		return templateDoc;
	}
	
	private void addTag(String name, String comment) {
		List list = getTags(name);
		if (list == null) {
			list = new ArrayList();
			tags.put(name, list);
		}
		list.add(comment);
	}
	
	protected List getTags(String name) {
		return (List) tags.get(name);
	}
	
	public String get(String name) {
		List list = getTags(name);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (String) list.get(0);
	}
	
	public String getDescription() {
		return description;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public boolean isInternal() {
		return get("internal") != null;
	}
	
	public List getSeeAlso() {
		return getTags("see");
	}
	
	public abstract String getType();
	
	protected abstract String getName();
	
	public String getSortName() {
		String sortName = getName();
		return sortName != null ? sortName : " ";
	}
	
	public String getHref() {
		String href = templateDoc.getHref();
		if (getName() != null) {
			href += '#' + getName();
		}
		return href;
	}
	
	public int compareTo(Object obj) {
		TemplateElementDoc other = (TemplateElementDoc) obj;
		return getSortName().compareTo(other.getSortName());
	}
}
