#!/usr/bin/env groovy
//
//  Derived from jooby by The Jooby Project
//  Found at https://github.com/jooby-project/jooby/
//  Original work was licensed as Apache 2.0
//  http://www.apache.org/licenses/LICENSE-2.0.html
//
//  usage: groovy bom.groovy ../pom.xml > pom.xml
import groovy.xml.*

def pomTemplatePath = properties['pomTemplate'];
def pomPath = properties['parentPom'];
def outputPomPath = properties['outputPom'];

def xml = new XmlSlurper().parse(new File(pomPath));
def sw = new StringWriter()
def b = new MarkupBuilder(sw)
def template = new java.io.File(pomTemplatePath).getText("UTF-8")

b.dependencies {
  for (m in xml.modules.module) {
		  if (m.text().endsWith('bom')) continue;
		  dependency {
				  groupId(project.groupId);
				  artifactId(m.text());
				  version('${com.test.version}');
		  }
  }
}

def outputPomFile = new File(outputPomPath);
outputPomFile.write template.replace("@version", xml.version.text()).replace("@dependencies", sw.toString())
