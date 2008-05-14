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
package org.riotfamily.fmdoc.ant;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.LogLevel;
import org.apache.tools.ant.types.Resource;
import org.riotfamily.fmdoc.generator.DocGenerator;
import org.riotfamily.fmdoc.generator.Logger;

public class FreeMarkerDocTask extends Task {
	
	private DocGenerator generator;
	
	private File destdir;
	
	private FileSet fileSet;
	
	private Logger log = new AntLogger();
	
	public void setDestdir(File destdir) {
		this.destdir = destdir;
	}
	
	public void addFileSet(FileSet fileSet) {
		this.fileSet = fileSet;
	}
	
	public void execute() throws BuildException {
		try {
			generator = new DocGenerator(destdir);
			Iterator it = fileSet.iterator();
			while (it.hasNext()) {
				Resource res = (Resource) it.next();
				Reader in = new InputStreamReader(res.getInputStream());
				String name = res.getName();
				generator.generate(name, in, log);
			}
			generator.generateIndexFiles();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
	}
	
	private class AntLogger implements Logger {

		public void info(String msg) {
			log(msg, LogLevel.INFO.getLevel());
		}
		
		public void warn(String msg) {
			log(msg, LogLevel.WARN.getLevel());
		}
		
		public void error(String msg) {
			log(msg, LogLevel.ERR.getLevel());
		}
		
	}
}
