package data_structure;

import java.io.File;

public class Filter extends javax.swing.filechooser.FileFilter {
    private String desc, ext;

    Filter(String description, String extension) {
        this.desc = description;
        this.ext = extension.toLowerCase();
    }

    public boolean accept(File file) {
        return file.getName().toLowerCase().endsWith(this.ext);
    }

    public String getDescription() {
        return this.desc;
    }

    String getExt() {
        return this.ext;
    }
}