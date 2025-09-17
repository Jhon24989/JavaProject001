package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportCSV {

    public static String export(String nombreArchivo, List<String> lineas) {
        try {
            File dir = new File("export");
            if (!dir.exists()) dir.mkdirs();
            File out = new File(dir, nombreArchivo);
            try (FileWriter fw = new FileWriter(out)) {
                for (String l : lineas) {
                    fw.write(l.replaceAll("\n"," "));
                    fw.write("\n");
                }
            }
            return out.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("No se pudo exportar CSV: " + e.getMessage(), e);
        }
    }

}
