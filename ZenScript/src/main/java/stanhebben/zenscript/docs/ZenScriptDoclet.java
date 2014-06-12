package stanhebben.zenscript.docs;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Type;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import stanhebben.zenscript.util.StringUtil;

/**
 * 
 * Example usage: (from minetweaker API source dir):
 * 
 * javadoc -doclet stanhebben.zenscript.docs.ZenScriptDoclet -docletpath  ..\..\..\..\ZenScript\build\classes\main -cp ..\..\..\..\ZenScript\build\libs\ZenScript.jar -sourcepath . -subpackages minetweaker
 * 
 * @author Stan Hebben
 */
public class ZenScriptDoclet {
    public static boolean start(RootDoc root) {
        ClassDoc[] classes = root.classes();
		List<ZenClassDoc> documentedClasses = new ArrayList<ZenClassDoc>();
		List<ZenExpansionDoc> expansionClasses = new ArrayList<ZenExpansionDoc>();
		
        for (int i = 0; i < classes.length; ++i) {
            ClassDoc cls = classes[i];
			
			for (AnnotationDesc annotation : cls.annotations()) {
				//String fullName = annotation.annotationType().containingPackage().name() + '.' + annotation.annotationType().name();
				String fullName = annotation.annotationType().qualifiedName();
				if (fullName.equals("stanhebben.zenscript.annotations.ZenClass")) {
					documentedClasses.add(new ZenClassDoc(cls, (String) (annotation.elementValues()[0].value().value())));
				} else if (fullName.equals("stanhebben.zenscript.annotations.ZenExpansion")) {
					expansionClasses.add(new ZenExpansionDoc(cls, (String) (annotation.elementValues()[0].value().value())));
				}
			}
        }
		
		Map<String, ZenClassDoc> classesByName = new HashMap<String, ZenClassDoc>();
		Map<String, ZenClassDoc> classesByJavaName = new HashMap<String, ZenClassDoc>();
		
		for (ZenClassDoc documentedClass : documentedClasses) {
			classesByName.put(documentedClass.getName(), documentedClass);
			classesByJavaName.put(documentedClass.getDoc().qualifiedName(), documentedClass);
		}
		for (ZenExpansionDoc expansion : expansionClasses) {
			if (classesByName.containsKey(expansion.getType())) {
				classesByName.get(expansion.getType()).addExpansion(expansion);
			} else {
				if (expansion.getType().endsWith("]")) {
					String baseName = expansion.getType().substring(0, expansion.getType().lastIndexOf('['));
					if (classesByName.containsKey(baseName)) {
						classesByName.get(baseName).addExpansion(expansion);
					}
				}
			}
		}
		
		System.out.println("Number of classes to document: " + classesByName.size());
		File output = new File("../../../build/zendoc");
		if (!output.exists()) output.mkdirs();
		
		String packageList = buildPackageList(documentedClasses);
		
		for (ZenClassDoc doc : classesByName.values()) {
			try {
				File outputDoc = new File(output, doc.getName().replace('.', '/') + ".html");
				File outputDocParent = outputDoc.getParentFile();
				if (!outputDocParent.exists()) outputDocParent.mkdirs();
				
				Writer writer = new BufferedWriter(new FileWriter(outputDoc));
				
				String simpleName = doc.getName().substring(doc.getName().lastIndexOf('.') + 1);
				
				writer.append("<!DOCTYPE html>");
				writer.append("<html><head>");
					writer.append("<title>ZenDoc : " + simpleName + "</title>");
					writer.append("<link rel=\"stylesheet\" href=\"/css/bootstrap.min.css\" />");
					writer.append("<link rel=\"stylesheet\" href=\"/css/bootstrap-theme.css\" />");
					writer.append("<link rel=\"stylesheet\" href=\"/css/font-awesome.min.css\" />");
				writer.append("</head><body>");
				writer.append("<div class=\"container\"><div class=\"row\">");
				writer.append(packageList);
				writer.append("<div class=\"col-md-9\">");
				
				String pkgName = doc.getName().substring(0, doc.getName().lastIndexOf('.'));
				writer.append("<div class=\"class-package-name\">");
				writer.append(pkgName);
				writer.append("</div>");
				
				// output class or interface contents
				writer.append("<h1>");
				if (doc.getDoc().isInterface()) {
					writer.append("Interface ");
				} else {
					writer.append("Class ");
				}
				
				writer.append(doc.getDoc().name()).append("</h1>");
				if (doc.getDoc().interfaces().length > 0) {
					writer.append("<div class=\"implemented-interfaces\">implements ");
					boolean first = true;
					for (Type itype : doc.getDoc().interfaceTypes()) {
						if (first) {
							first = false;
						} else {
							writer.append(", ");
						}
						
						outputType(writer, itype, classesByJavaName);
					}
					writer.append("</div>");
				}
				
				writer.append("<hr />");
				writer.append("<div class=\"documentation-class\">");
				
				for (String p : StringUtil.splitParagraphs(doc.getDoc().commentText())) {
					writer.append("<p>");
					writer.append(p);
					writer.append("</p>");
				}
				
				writer.append("</div>");
				
				
				if (doc.getDoc().isInterface() && doc.getDoc().methods().length == 1) {
					// functional interface
					writer.append("<h2>Functional interface</h2>");
					
					MethodDoc method = doc.getDoc().methods()[0];
					writer.append("<div class=\"functional-interface-signature\">");
					writer.append("function (");
					
					boolean first = true;
					for (Parameter parameter : method.parameters()) {
						if (first) {
							first = false;
						} else {
							writer.append(", ");
						}
						ZenParameterDoc paramdoc = new ZenParameterDoc(parameter);
						writer.append(paramdoc.getName());
						writer.append(" as ");
						outputType(writer, paramdoc.getType(), classesByJavaName);
					}
					writer.append(") as ");
					outputType(writer, method.returnType(), classesByJavaName);
					writer.append("</div>");
					
					String[] comment = StringUtil.splitParagraphs(method.commentText());
					for (String line : comment) {
						writer.append("<p>");
						writer.append(line);
						writer.append("</p>");
					}
				} else {
					writer.append("<h2>Member Overview</h2>");

					// collect members

					LinkedHashMap<String, ZenPropertyDoc> instanceProperties = new LinkedHashMap<String, ZenPropertyDoc>();
					LinkedHashMap<String, ZenPropertyDoc> staticProperties = new LinkedHashMap<String, ZenPropertyDoc>();
					List<ZenMethodDoc> instanceMethods = new ArrayList<ZenMethodDoc>();
					List<ZenMethodDoc> staticMethods = new ArrayList<ZenMethodDoc>();
					List<ZenOperatorDoc> operators = new ArrayList<ZenOperatorDoc>();
					List<ZenCasterDoc> casters = new ArrayList<ZenCasterDoc>();

					for (MethodDoc method : doc.getDoc().methods()) {
						for (AnnotationDesc annotation : method.annotations()) {
							String fullName = annotation.annotationType().qualifiedName();

							if (fullName.equals("stanhebben.zenscript.annotations.ZenMethod")
									|| fullName.equals("stanhebben.zenscript.annotations.ZenMethodStatic")) {
								String name = annotation.elementValues().length > 0
										? (String) (annotation.elementValues()[0].value().value())
										: "";

								if (name.length() == 0) {
									name = method.name();
								}

								List<ZenParameterDoc> parameters = new ArrayList<ZenParameterDoc>();
								for (Parameter parameter : method.parameters()) {
									parameters.add(new ZenParameterDoc(parameter));
								}

								if (method.isStatic()) {
									staticMethods.add(new ZenMethodDoc(name, method, parameters));
								} else {
									instanceMethods.add(new ZenMethodDoc(name, method, parameters));
								}
							} else if (fullName.equals("stanhebben.zenscript.annotations.ZenGetter")) {
								String name = annotation.elementValues().length > 0
										? (String) (annotation.elementValues()[0].value().value())
										: "";

								if (name.length() == 0) {
									name = method.name();
								}

								Type type = method.returnType();
								String[] comment = StringUtil.splitParagraphs(method.commentText());

								if (!method.isStatic()) {
									if (instanceProperties.containsKey(name)) {
										ZenPropertyDoc property = instanceProperties.get(name);
										property.setCanGet(true);
										property.setComment(comment);
									} else {
										ZenPropertyDoc property = new ZenPropertyDoc(name, type);
										property.setCanGet(true);
										property.setComment(comment);
										instanceProperties.put(name, property);
									}
								} else {
									if (staticProperties.containsKey(name)) {
										ZenPropertyDoc property = staticProperties.get(name);
										property.setCanGet(true);
										property.setComment(comment);
									} else {
										ZenPropertyDoc property = new ZenPropertyDoc(name, type);
										property.setCanGet(true);
										property.setComment(comment);
										staticProperties.put(name, property);
									}
								}
							} else if (fullName.equals("stanhebben.zenscript.annotations.ZenSetter")) {
								String name = annotation.elementValues().length > 0
										? (String) (annotation.elementValues()[0].value().value())
										: "";

								if (name.length() == 0) {
									name = method.name();
								}

								Type type = method.parameters()[0].type();
								String[] comment = StringUtil.splitParagraphs(method.commentText());

								if (!method.isStatic()) {
									if (instanceProperties.containsKey(name)) {
										ZenPropertyDoc property = instanceProperties.get(name);
										property.setCanSet(true);
									} else {
										ZenPropertyDoc property = new ZenPropertyDoc(name, type);
										property.setCanGet(true);
										property.setComment(comment);
										instanceProperties.put(name, property);
									}
								} else {
									if (staticProperties.containsKey(name)) {
										ZenPropertyDoc property = staticProperties.get(name);
										property.setCanSet(true);
									} else {
										ZenPropertyDoc property = new ZenPropertyDoc(name, type);
										property.setCanGet(true);
										property.setComment(comment);
										staticProperties.put(name, property);
									}
								}
							} else if (fullName.equals("stanhebben.zenscript.annotations.ZenOperator")) {
								String operator = annotation.elementValues()[0].value().value().toString();
								operators.add(new ZenOperatorDoc(operator, method));
							} else if (fullName.equals("stanhebben.zenscript.annotations.ZenCaster")) {
								casters.add(new ZenCasterDoc(method));
							}
						}
					}

					for (ZenExpansionDoc expansion : doc.getExpansions()) {
						// TODO
					}

					if (!staticProperties.isEmpty()) {
						writer.append("<div class=\"panel panel-info panel-memberlist\">");
						writer.append("<div class=\"panel-heading\">Static Properties</div>");
						writer.append("<div class=\"panel-body\">");
						writer.append("<table class=\"table table-properties table-bordered\"><tbody>");

						String[] names = new String[staticProperties.size()];
						int namei = 0;
						for (String name : staticProperties.keySet()) {
							names[namei++] = name;
						}
						Arrays.sort(names);

						for (String name : names) {
							ZenPropertyDoc property = staticProperties.get(name);

							writer.append("<tr>");
							writer.append("<td class=\"property-type\">");
							outputType(writer, property.getType(), classesByJavaName);
							writer.append("</td>");
							writer.append("<td>");
							writer.append("<div class=\"property-name\">");
							if (!property.isCanSet()) writer.append("<span class=\"fa fa-lock\" style=\"color: #ccc;\" title=\"cannot be set\"></span> ");
							writer.append("<a href=\"#property-staticvalue\">")
									.append(property.getName())
									.append("</a></div>");
							if (property.getComment() != null && property.getComment().length > 0) {
								writer.append("<div class=\"property-intro\">")
										.append(property.getComment()[0])
										.append("</div>");
							}
							writer.append("</td></tr>");
						}

						writer.append("</tbody></table></div></div>");
					}

					if (!instanceProperties.isEmpty()) {
						writer.append("<div class=\"panel panel-info panel-memberlist\">");
						writer.append("<div class=\"panel-heading\">Properties</div>");
						writer.append("<div class=\"panel-body\">");
						writer.append("<table class=\"table table-properties table-bordered\"><tbody>");

						String[] names = new String[instanceProperties.size()];
						int namei = 0;
						for (String name : instanceProperties.keySet()) {
							names[namei++] = name;
						}
						Arrays.sort(names);

						for (String name : names) {
							ZenPropertyDoc property = instanceProperties.get(name);

							writer.append("<tr>");
							writer.append("<td class=\"property-type\">");
							outputType(writer, property.getType(), classesByJavaName);
							writer.append("</td>");
							writer.append("<td>");
							writer.append("<div class=\"property-name\">");
							if (!property.isCanSet()) writer.append("<span class=\"fa fa-lock\" style=\"color: #ccc;\" title=\"cannot be set\"></span> ");
							writer.append("<a href=\"#property-staticvalue\">")
									.append(property.getName())
									.append("</a></div>");
							if (property.getComment() != null && property.getComment().length > 0) {
								writer.append("<div class=\"property-intro\">")
										.append(property.getComment()[0])
										.append("</div>");
							}
							writer.append("</td></tr>");
						}

						writer.append("</tbody></table></div></div>");
					}

					if (!staticMethods.isEmpty()) {
						writer.append("<div class=\"panel panel-info panel-memberlist\">");
						writer.append("<div class=\"panel-heading\">Static Methods</div>");
						writer.append("<div class=\"panel-body\">");
						writer.append("<table class=\"table table-properties table-bordered\"><tbody>");

						for (ZenMethodDoc method : staticMethods) {
							writer.append("<tr>");
							writer.append("<td class=\"property-type\">");
							outputType(writer, method.getDoc().returnType(), classesByJavaName);
							writer.append("</td>");
							writer.append("<td>");
							writer.append("<div class=\"property-name\">");
							writer.append("<a href=\"#property-staticvalue\">")
									.append(method.getName())
									.append("</a>(");
							boolean first = true;

							for (ZenParameterDoc param : method.getParameters()) {
								if (param.isOptional()) writer.append("<span class=\"param-optional\">");
								if (first) {
									first = false;
								} else {
									writer.append(", ");
								}
								writer.append(param.getName());
								writer.append(" as ");
								outputType(writer, param.getType(), classesByJavaName);
							}
							writer.append(")</div>");

							String[] comment = StringUtil.splitParagraphs(method.getDoc().commentText());
							if (comment.length > 0) {
								writer.append("<div class=\"property-intro\">")
										.append(comment[0])
										.append("</div>");
							}
							writer.append("</td></tr>");
						}

						writer.append("</tbody></table></div></div>");
					}

					if (!instanceMethods.isEmpty()) {
						writer.append("<div class=\"panel panel-info panel-memberlist\">");
						writer.append("<div class=\"panel-heading\">Methods</div>");
						writer.append("<div class=\"panel-body\">");
						writer.append("<table class=\"table table-properties table-bordered\"><tbody>");

						for (ZenMethodDoc method : instanceMethods) {
							writer.append("<tr>");
							writer.append("<td class=\"property-type\">");
							outputType(writer, method.getDoc().returnType(), classesByJavaName);
							writer.append("</td>");
							writer.append("<td>");
							writer.append("<div class=\"property-name\">");
							writer.append("<a href=\"#property-staticvalue\">")
									.append(method.getName())
									.append("</a>(");
							boolean first = true;

							for (ZenParameterDoc param : method.getParameters()) {
								if (param.isOptional()) writer.append("<span class=\"param-optional\">");
								if (first) {
									first = false;
								} else {
									writer.append(", ");
								}
								writer.append(param.getName());
								writer.append(" as ");
								outputType(writer, param.getType(), classesByJavaName);
							}
							writer.append(")</div>");

							String[] comment = StringUtil.splitParagraphs(method.getDoc().commentText());
							if (comment.length > 0) {
								writer.append("<div class=\"property-intro\">")
										.append(comment[0])
										.append("</div>");
							}
							writer.append("</td></tr>");
						}

						writer.append("</tbody></table></div></div>");
					}

					if (!operators.isEmpty()) {
						// TODO
					}

					if (!casters.isEmpty()) {
						writer.append("<div class=\"panel panel-info panel-memberlist\">");
						writer.append("<div class=\"panel-heading\">Casters</div>");
						writer.append("<div class=\"panel-body\">");
						writer.append("<table class=\"table table-properties table-bordered\"><tbody>");

						for (ZenCasterDoc caster : casters) {
							writer.append("<tr>");
							writer.append("<td>");
							writer.append("<div class=\"property-name\">");
							writer.append("as <a href=\"#property-staticvalue\">");
							outputTypeRaw(writer, caster.getMethod().returnType(), classesByJavaName);
							writer.append("</a></div>");

							String[] comment = StringUtil.splitParagraphs(caster.getMethod().commentText());
							if (comment.length > 0) {
								writer.append("<div class=\"property-intro\">")
										.append(comment[0])
										.append("</div>");
							}
							writer.append("</td></tr>");
						}

						writer.append("</tbody></table></div></div>");
					}
				}
				
				writer.append("</div></div></div>");
				writer.append("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js\"></script>");
				writer.append("<script src=\"/js/bootstrap.min.js\"></script>");
				writer.append("<script type=\"application/javascript\">");
				writer.append("$('#collapse-");
				writer.append(pkgName.replace('.', '-'));
				writer.append("').collapse('show');");
				writer.append("</script>");
				writer.append("</body></html>");
				writer.close();
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
		
        return true;
    }
	
    /**
    * NOTE: Without this method present and returning LanguageVersion.JAVA_1_5,
    *       Javadoc will not process generics because it assumes LanguageVersion.JAVA_1_1
    * @return language version (hard coded to LanguageVersion.JAVA_1_5)
    */
   public static LanguageVersion languageVersion() {
      return LanguageVersion.JAVA_1_5;
   }
	
	private static String buildPackageList(List<ZenClassDoc> classes) {
		// collect package list
		Map<String, List<ZenClassDoc>> packages = new HashMap<String, List<ZenClassDoc>>();
		for (ZenClassDoc cls : classes) {
			String pkg = cls.getName().substring(0, cls.getName().lastIndexOf('.'));
			if (packages.containsKey(pkg)) {
				packages.get(pkg).add(cls);
			} else {
				ArrayList<ZenClassDoc> classList = new ArrayList<ZenClassDoc>();
				classList.add(cls);
				packages.put(pkg, classList);
			}
		}
		
		String[] packageSorted = new String[packages.size()];
		int packageIndex = 0;
		for (String packageName : packages.keySet()) {
			packageSorted[packageIndex++] = packageName;
		}
		Arrays.sort(packageSorted);
		
		// output data
		StringBuilder output = new StringBuilder();
		output.append("<div class=\"col-md-3\">");
		output.append("<div class=\"panel panel-group\"><div class=\"panel-body\">");
		output.append("<div class=\"panel-heading\"><h3>Packages</h3></div>");
		output.append("<div class=\"panel-scroll\">");
		
		for (int i = 0; i < packageSorted.length; i++) {
			String pkgId = extractId(packageSorted[i]);
			String pkgDir = "/doc/" + packageSorted[i].replace('.', '/');
			
			output.append("<div class=\"panel panel-default\">");
			output.append("<div class=\"panel-heading\"><h4 class=\"panel-title package-title\">");
			output.append("<a data-toggle=\"collapse\" data-parent=\"#accordion\" href=\"#collapse-")
					.append(pkgId)
					.append("\"><span class=\"glyphicon glyphicon-th-large\"></span> ")
					.append(packageSorted[i])
					.append("</a>");
			output.append("</h4></div>");
			
			output.append("<div id=\"collapse-")
					.append(pkgId)
					.append("\" class=\"panel-collapse collapse\">");
			output.append("<div class=\"panel-body\">");
			
			// assemble lists
			List<String> interfaceNames = new ArrayList<String>();
			List<String> classNames = new ArrayList<String>();
			
			for (ZenClassDoc cls : packages.get(packageSorted[i])) {
				if (cls.getDoc().isInterface()) {
					interfaceNames.add(cls.getDoc().name());
				} else {
					classNames.add(cls.getDoc().name());
				}
			}
			
			Collections.sort(interfaceNames);
			Collections.sort(classNames);
			
			if (!interfaceNames.isEmpty()) {
				output.append("<h4 class=\"interfaces-title\">Interfaces</h4>");
				output.append("<table class=\"table table-classes\"><tbody>");
				
				for (String ifaceName : interfaceNames) {
					output.append("<tr><td><a href=\"")
							.append(pkgDir).append('/').append(ifaceName).append(".html")
							.append("\">")
							.append(ifaceName)
							.append("</a></td></tr>");
				}
				
				output.append("</tbody></table>");
			}
			
			if (!classNames.isEmpty()) {
				output.append("<h4 class=\"classes-title\">Classes</h4>");
				output.append("<table class=\"table table-classes\"><tbody>");
				
				for (String className : classNames) {
					output.append("<tr><td><a href=\"")
							.append(pkgDir).append('/').append(className).append(".html")
							.append("\">")
							.append(className)
							.append("</a></td></tr>");
				}
				
				output.append("</tbody></table>");
			}
			
			output.append("</div></div></div>");
		}
		
		output.append("</div></div></div></div>");
		return output.toString();
	}
	
	private static String extractId(String pkg) {
		return pkg.replace('.', '-');
	}
	
	private static void outputType(Writer output, Type type, Map<String, ZenClassDoc> javaClasses) throws IOException {
		if (javaClasses.containsKey(type.qualifiedTypeName())) {
			ZenClassDoc doc = javaClasses.get(type.qualifiedTypeName());
			output.append("<a href=\"");
			output.append(makeLink(doc.getName()));
			output.append("\">");
			output.append(doc.getName().substring(doc.getName().lastIndexOf('.') + 1));
			output.append("</a>");
		} else if (type.qualifiedTypeName().equals("java.util.List")) {
			Type base = type.asParameterizedType().typeArguments()[0];
			outputType(output, base, javaClasses);
			output.append("[]");
		} else if (type.qualifiedTypeName().equals("java.util.Map")) {
			Type key = type.asParameterizedType().typeArguments()[0];
			Type value = type.asParameterizedType().typeArguments()[1];
			outputType(output, value, javaClasses);
			output.append('[');
			outputType(output, key, javaClasses);
			output.append(']');
		} else if (type.qualifiedTypeName().equals("java.lang.String")) {
			output.append("string");
		} else {
			output.append(type.qualifiedTypeName());
		}
	}
	
	private static void outputTypeRaw(Writer output, Type type, Map<String, ZenClassDoc> javaClasses) throws IOException {
		if (javaClasses.containsKey(type.qualifiedTypeName())) {
			ZenClassDoc doc = javaClasses.get(type.qualifiedTypeName());
			output.append(doc.getName().substring(doc.getName().lastIndexOf('.') + 1));
		} else if (type.qualifiedTypeName().equals("java.util.List")) {
			Type base = type.asParameterizedType().typeArguments()[0];
			outputTypeRaw(output, base, javaClasses);
			output.append("[]");
		} else if (type.qualifiedTypeName().equals("java.util.Map")) {
			Type key = type.asParameterizedType().typeArguments()[0];
			Type value = type.asParameterizedType().typeArguments()[1];
			outputTypeRaw(output, value, javaClasses);
			output.append('[');
			outputTypeRaw(output, key, javaClasses);
			output.append(']');
		} else if (type.qualifiedTypeName().equals("java.lang.String")) {
			output.append("string");
		} else {
			output.append(type.qualifiedTypeName());
		}
	}
	
	private static String makeLink(String className) {
		return "/doc/" + className.replace('.', '/') + ".html";
	}
}
