package org.pwr.parser;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.pwr.conf.SETTINGS;
import org.pwr.enums.BugFix;
import org.pwr.model.ClassEntity;
import org.pwr.repository.ClassEntityRepository;
import org.pwr.tool.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class CommitParser {

    public static ArrayList<ClassEntity> parseToClassEntity(String path) throws IOException {

        //TODO: Zrobić gromadzenie częściowych stringów a następnie wysłać jedno wielkie wstawienie do bazy

        ArrayList<ClassEntity> classEntities = new ArrayList<>();

        FileInputStream file = new FileInputStream(new File(path));

        XSSFWorkbook workbook = new XSSFWorkbook(file);

        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();


        int counter = 0;
        while(rowIterator.hasNext()){
            Row row = rowIterator.next();

            String pathToFile = row.getCell(5).getRichStringCellValue().toString();

            if(Utils.isJavaFile(pathToFile)) {
                ClassEntity classEntity = new ClassEntity();

                classEntity.setCommitHash(row.getCell(7).getRichStringCellValue().toString());
                classEntity.setCommitDate(row.getCell(6).getDateCellValue().getTime());
                classEntity.setCommitMessage(row.getCell(4).getRichStringCellValue().toString());
                classEntity.setCommitAuthor(row.getCell(2).getRichStringCellValue().toString());
                classEntity.setPath(pathToFile);
                classEntity.setActionType(row.getCell(3).getRichStringCellValue().toString());

                if (Utils.isBugFix(classEntity.getCommitMessage())) {
                    classEntity.setIsFix(BugFix.TRUE);
                } else {
                    classEntity.setIsFix(BugFix.FALSE);
                }

                //Cut commit message for DB optimisation
                String commitMessage = classEntity.getCommitMessage();
                commitMessage = commitMessage.substring(0,  Math.min(commitMessage.length(), 10));
                classEntity.setCommitMessage(commitMessage);

                ClassEntityRepository.addClassEntity(classEntity);
                System.out.println("Added class " + counter);
                if(counter++ > SETTINGS.MAX_SIZE){
                    break;
                }
            }
        }
        return classEntities;
    }
}
