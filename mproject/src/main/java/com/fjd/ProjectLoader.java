package com.fjd;

import java.io.File;
import java.io.FileFilter;

public class ProjectLoader {
    public  static void load(){
        String webapps = "d:\\mtomcat\\webapps\\";
        File[] projects = new File(webapps).listFiles(file -> file.isDirectory());
        for (File projectFile:projects){
            return
        }
    }
}
