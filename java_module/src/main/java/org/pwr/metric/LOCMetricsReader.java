package org.pwr.metric;

import org.pwr.model.ClassEntity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LOCMetricsReader {

    public String prefix = "C:\\MMSE\\Spring Framework\\";
    public String pathToFile;

    public static ArrayList<ClassEntity> classEntities = new ArrayList<>();

    LOCMetricsReader(String pathToFile){
        this.pathToFile = pathToFile;
    }

    public void calculateLOCMetrics(ClassEntity classEntity){
        String line = "";
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] lineArray = line.split(cvsSplitBy);
                //Get state by hash

                classEntity.setPath(lineArray[0].split("C:\\MMSE\\Spring Framework\\")[1]);
//                classEntity.setLOC(Integer.parseInt(lineArray[1]));
//                classEntity.setSLOC_P(Integer.parseInt(lineArray[2]));
//                classEntity.setSLOC_L(Integer.parseInt(lineArray[3]));
//                classEntity.setMVG(Integer.parseInt(lineArray[4]));
//                classEntity.setBLOC(Integer.parseInt(lineArray[5]));
//                classEntity.setCandSLOC(Integer.parseInt(lineArray[6]));
//                classEntity.setCLOC(Integer.parseInt(lineArray[7]));
//                classEntity.setCWORD(Integer.parseInt(lineArray[8]));
//                classEntity.setHCLOC(Integer.parseInt(lineArray[9]));
//                classEntity.setHCWORD(Integer.parseInt(lineArray[10]));
                classEntities.add(classEntity);
                System.out.println(classEntity.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
