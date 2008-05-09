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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Index {

	private Map docs = new HashMap();
	
	public Index(Collection templateDocs) {
		Iterator it = templateDocs.iterator();
		while (it.hasNext()) {
			TemplateDoc doc = (TemplateDoc) it.next();
			doc.addToIndex(this);
		}
	}
	
	public Map getDocs() {
		return docs;
	}
	
	public Set getLetters() {
		return new TreeSet(docs.keySet());
	}
	
	public void addAll(Collection c) {
		if (c != null) {
			Iterator it = c.iterator();
			while (it.hasNext()) {
				TemplateElementDoc doc = (TemplateElementDoc) it.next();
				add(doc);
			}
		}
	}
	
	public void add(TemplateElementDoc doc) {
		if (doc != null) {
			String letter = doc.getSortName().substring(0, 1).toUpperCase();
			List list = (List) docs.get(letter);
			if (list == null) {
				list = new ArrayList();
				docs.put(letter, list);
			}
			list.add(doc);
			Collections.sort(list);
		}
	}
}
