package com.fjd;

import com.fjd.servlet.Servlet;
import com.fjd.utils.XmlParser;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class ProjectLoader {
    public static Map<String, ProjectConfiginfo> load() throws Exception {
        Map<String, ProjectConfiginfo> projectConfiginfoMap = new HashMap<>();
        String webapps = "\\webapps\\";
        File[] projects = new File(webapps).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });
        for (File projectFile : projects) {
            ProjectConfiginfo projectConfiginfo = new XmlParser().loadXml(projectFile.getPath() + "\\WEB-INF\\web.xml");
            projectConfiginfo.projectPath = projectFile.getPath();
            projectConfiginfo.loadServlet();
            projectConfiginfoMap.put(projectFile.getName(), projectConfiginfo);
        }
        return projectConfiginfoMap;
    }

    public class ProjectConfiginfo {
        public Map<String, Object> servlets = new HashMap<>();
        public Map<String, String> servletMappings = new HashMap<>();
        public Map<String, Servlet> servletInstances = new HashMap<>();
        public String projectPath = null;

        public void loadServlet() throws Exception {
            URL classesFile = new URL("file:" + projectPath + "\\WEB-INF\\classes\\");
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{classesFile});
            for (Map.Entry<String, Object> entry : this.servlets.entrySet()) {
                String servletName = entry.getKey();
                String servletClassName = entry.getKey();
                Class<?> clazz = urlClassLoader.loadClass(servletClassName);
                Servlet servlet = (Servlet) clazz.newInstance();
                servletInstances.put(servletName, servlet);
            }
        }
    }
}
