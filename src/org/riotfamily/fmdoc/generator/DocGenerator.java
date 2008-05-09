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
package org.riotfamily.fmdoc.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.riotfamily.fmdoc.model.Index;
import org.riotfamily.fmdoc.model.TemplateDoc;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DocGenerator {

	private File destdir;
	
	private Configuration config = new Configuration();

	private List docs = new ArrayList();
	
	public DocGenerator(File destdir) {
		this.destdir = destdir;
	}

	public void generate(String name, Reader in) throws TemplateException, IOException {
		
		Template template = new Template(name, in, config);
		
		name = name.replace(File.separatorChar, '/');
		int i = name.lastIndexOf('/');
		if (i != -1) {
			name = name.substring(i + 1);
		}
		String dest = name;
		i = dest.lastIndexOf('.');
		if (i != -1) {
			dest = dest.substring(0, i);
		}
		dest += ".html";
		
		TemplateDoc doc = new TemplateDoc(template, dest);
		if (doc.getNamespace() != null) {
			Map map = Collections.singletonMap("template", doc);
			
			Writer out = new FileWriter(new File(destdir, dest));
			
			process("doc", map, out);
			docs.add(doc);
		}
	}
	
	public void generateIndexFiles() throws TemplateException, IOException {
		Map model = new HashMap();
		model.put("templates", docs);
		model.put("index", new Index(docs));
		process("overview-summary", model);
		process("overview-frame", model);
		process("index", model);
		process("index-all", model);
		copy("stylesheet.css");
		
	}
	
	private void copy(String resource) throws IOException {
		InputStream in = getStream(resource);
		FileOutputStream out = new FileOutputStream(new File(destdir, resource));
		int byteCount = 0;
		byte[] buffer = new byte[4096];
		int bytesRead = -1;
		while ((bytesRead = in.read(buffer)) != -1) {
			out.write(buffer, 0, bytesRead);
			byteCount += bytesRead;
		}
		out.flush();
	}
	
	private InputStream getStream(String resource) {
		return getClass().getResourceAsStream("../view/" + resource);
	}
	
	private void process(String viewName, Map model) throws TemplateException, IOException {
		Writer out = new FileWriter(new File(destdir, viewName + ".html"));
		process(viewName, model, out);
	}
	
	private void process(String viewName, Map model, Writer out) throws TemplateException, IOException {
		Reader in = new InputStreamReader(getStream(viewName + ".ftl"));
		Template view = new Template(viewName, in, config);
		view.process(model, out);
	}
}
