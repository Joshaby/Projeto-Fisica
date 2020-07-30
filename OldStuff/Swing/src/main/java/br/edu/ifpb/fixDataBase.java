package br.edu.ifpb;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class fixDataBase { // classe para retirada de questões erradas, colocadas por engano
    public static void main(String[] args) throws IOException {
        String ids = "59460,59450,59435,59425,59356,58269,58268,58078,57074,57073,57026,58235,57155,56130,57275,56736,55917,58205,57302,56606,56099,59181,59175,59152,56683,61662,60574,60573,61816,64727,64681,64226,203772,65844,65772,65552,65538,65522,67562,133101,133525,110558,130950,130842,130638,123867,136971,145399,145115,167572,188654,181358,176213,176117,109767,194056,194055,212669,194282,193856,213142,213084,212985";
        String[] idsList = ids.split(",");
        System.out.println(ids);
        DirectoryStream<Path> files = Files.newDirectoryStream(Path.of("QuestõesDifícil"));
        for (Path path : files) {
            for (String s : idsList) {
                if (path.getFileName().toString().contains(s) && Files.exists(path)) {
                    if (Files.isDirectory(path)) {
                        DirectoryStream<Path> files1 = Files.newDirectoryStream(path);
                        for (Path p : files1) Files.delete(p);
                    }
                    Files.delete(path);
                }
            }
        }
    }
}
