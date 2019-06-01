package file;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public interface FileManager {
    static void createDirectory(String dir){
        File directory = new File(dir);
        directory.mkdir();
    }

    static void createFile(String src){
        final String []position = {""};
        List<String> path = Arrays.asList(src.split("/"));
        path
                .forEach(part -> {
                    try {
                        if(part.equals(path.get(path.size() - 1))) {
                            File element = new File(position[0] + "/" +part);
                            element.createNewFile();
                        } else {
                            position[0] += part + "/";
                            File element = new File(position[0].substring(0, position[0].length() - 1));
                            element.mkdir();
                        }
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                });
    }

    static String read(String src){
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(src)
                    );
            String line;
            while((line = reader.readLine()) != null){
                content
                        .append(line)
                        .append("\n");
            }
            reader.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return content.toString();
    }

    static void write(String src, String content){
        try {
            BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(src)
                    );
            writer.write(content);
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
