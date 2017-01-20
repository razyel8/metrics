package org.pwr.conf;

public class SETTINGS {


    public static final int MAX_SIZE = 1000;

    String[] repositoryArray = {
            "https://github.com/spring-projects/spring-framework.git",
            "https://github.com/hibernate/hibernate-orm.git",
            "https://github.com/apache/mesos.git"
    };


    public static String depressFile = "data_files/gitData.xlsx";
    public static String remoteRepository = "https://github.com/spring-projects/spring-framework.git";


    //TODO: plik properties
    public static final boolean CLONE_REPOSITORY = false;
    public static final boolean CALCULATE_METRICS = false;
    public static String REPOSITORY_PATH = "C:\\MMSE\\repositories\\spring-framework\\";

}
