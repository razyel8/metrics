package org.pwr.repository;

import org.junit.Assert;
import org.junit.Test;
import org.pwr.model.ClassEntity;
import org.pwr.tool.JDBCConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestClassEntityRepository {

    @Test
    public void testAddClassEntity(){
        ClassEntity ce = new ClassEntity();
        ce.setCommitHash("cbdshjcbdshcjds");
        ce.setCommitAuthor("testAuthor");
        ce.setPath("bla bla path");
        ce.setActionType("M");
        ce.setCommitDate(1478296043000L);
        ce.setCommitMessage("cdcdscdscdscs");

        ClassEntityRepository.addClassEntity(ce);
    }

    @Test
    public void testGetBugRevisions(){
        Map<String, Integer> map = ClassEntityRepository.getBugRevisions();
        System.out.println(map.keySet().toString());
        System.out.println(map.values().toString());
    }


}
