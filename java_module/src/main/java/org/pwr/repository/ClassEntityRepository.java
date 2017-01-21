package org.pwr.repository;

import org.pwr.model.ClassEntity;
import org.pwr.tool.JDBCConnector;
import org.pwr.tool.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static org.pwr.metric.LOCMetricsReader.classEntities;


//TODO: Przerobić na Beana
public class ClassEntityRepository {

    public static ArrayList<ClassEntity> allClasses  = new ArrayList<ClassEntity>();

    public static ArrayList<ClassEntity> bugFixingClasses = new ArrayList<ClassEntity>();

    public static ArrayList<ClassEntity> bugProneClasses = new ArrayList<ClassEntity>();

    public static void addClassEntity(ClassEntity classEntity){
        String sql = "INSERT INTO classentity (sh1, filePath, author, date, action, message, isBugFix) VALUES ('"
                + classEntity.getCommitHash() + "','"
                + classEntity.getPath() + "','"
                + classEntity.getCommitAuthor() + "',"
                + classEntity.getCommitDate() + ",'"
                + classEntity.getActionType() + "','"
                + classEntity.getCommitMessage() + "',"
                + classEntity.getIsFix().value
                +")";
        JDBCConnector.getInstance().insertStatement(sql);
    }

    public static ArrayList<ClassEntity> getBugFixingClasses(){
        ArrayList<ClassEntity> classes = new ArrayList<>();
        try {
            Statement stmt = JDBCConnector.getInstance().getConnection().createStatement();
            String sql = "SELECT * FROM classentity WHERE isBugFix = 1 order by date;";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                ClassEntity ce = Utils.createClassEntityDromRS(rs);
                //TODO:
                classes.add(ce);
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public static ClassEntity getPrevoiusTouch(ClassEntity classEntity){
        ClassEntity ce = null;
        try {
            Statement stmt = JDBCConnector.getInstance().getConnection().createStatement();
            String sql = "SELECT * FROM metrics.classentity WHERE filePath = '"+classEntity.getPath()+"' AND date < "+classEntity.getCommitDate()+" order by date;";
            ResultSet rs = stmt.executeQuery(sql);
            //TODO:

            while(rs.next()){
                ce = Utils.createClassEntityDromRS(rs);
                return ce;
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ce;
    }

    //Pobiera listę numerów rewizji w których wystąpiły błędy i liczbę klas *2 w których one wystąpiły
    public static LinkedHashMap<String, Integer> getBugRevisions(){
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        try {
            Statement stmt = JDBCConnector.getInstance().getConnection().createStatement();
            String sql = "SELECT count(*), sh1 FROM classentity WHERE isBugProne = 1 group by sh1 order by date";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                map.put(rs.getString("sh1"), rs.getInt(1)*2);
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }


    public static LinkedHashMap<String, List<ClassEntity>> getBugClassEntities(){
        LinkedHashMap<String, List<ClassEntity>> map = new LinkedHashMap<>();
        try {
            //TODO: Implement
            Statement stmt = JDBCConnector.getInstance().getConnection().createStatement();
            String sql = "SELECT * FROM classentity WHERE isBugProne = 1 group by sh1 order by date";
            ResultSet rs = stmt.executeQuery(sql);
//            while(rs.next()){
//                map.put(rs.getString("sh1"), rs.getInt(1));
//            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }


    public static LinkedHashMap<String, List<ClassEntity>>  getAllClassEntitiesForCalculation(LinkedHashMap<String, List<ClassEntity>> toCalculateBugs){
            //TODO: To implement
        return null;
    }




//    public static ArrayList<ClassEntity> getBugProneClasses(){
//        return getBugProneClasses(allClasses);
//    }
//
//
//    public static int getNumberOfAllCommiters(){
//
//    }
//
//    public static List<IClassEntity> getAllEntities(Date dateTo){
//
//	}
//
	public static List<ClassEntity> getAllClassEntitiesOfCommiter(String commiter){
    	return null;
    }
}
