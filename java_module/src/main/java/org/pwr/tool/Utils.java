package org.pwr.tool;

import org.pwr.enums.BugFix;
import org.pwr.enums.BugProne;
import org.pwr.model.ClassEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import static org.pwr.repository.ClassEntityRepository.getBugFixingClasses;
import static org.pwr.repository.ClassEntityRepository.getPrevoiusTouch;

public class Utils {


    public static boolean isBugFix(String message){
        return message != null ?  (message.contains("Fix") ||
                message.contains("fix") ||
                message.contains("FIX")) : false;
    }

    public static void setUpBugProne(){
        ArrayList<ClassEntity> classEntities = getBugFixingClasses();
        for(ClassEntity ce : classEntities){
            ClassEntity cep = getPrevoiusTouch(ce);
            if(cep != null){
                System.out.println("Bug prone class!!");
                String sql = "UPDATE classentity\n" +
                        "SET isBugProne=1\n" +
                        "WHERE filePath='"+cep.getPath()+"' and sh1='"+cep.getCommitHash()+"'";
                JDBCConnector.getInstance().insertStatement(sql);
            }
        }
    }

    public static boolean isJavaFile(String path){
        return path.contains(".java");
    }


    public static ClassEntity createClassEntityDromRS(ResultSet rs){
        ClassEntity ce = new ClassEntity();
        try {
            ce.setCommitHash(rs.getString("sh1"));
            ce.setPath(rs.getString("filePath"));
            ce.setCommitDate(rs.getLong("date"));
            ce.setActionType(rs.getString("action"));
            ce.setCommitAuthor(rs.getString("author"));
            ce.setCommitMessage(rs.getString("message"));
            ce.setIsFix(BugFix.getEnum(rs.getInt("isBugFix")));
            ce.setIsBug(BugProne.getEnum(rs.getInt("isBugFix")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ce;


    }

    public static ArrayList<String> getCommitChanges(String sh1){
        return null;
    }

}
