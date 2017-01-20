package org.pwr;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import com.github.mauricioaniche.ck.Runner;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.pwr.model.ClassEntity;
import org.pwr.parser.CommitParser;
import org.pwr.repository.ClassEntityRepository;
import org.pwr.runners.CommandLineExecutor;
import org.pwr.tool.Logger;

public class Main {

    public static final String REPOSITORY_PATH = "C:\\MMSE\\repositories\\";
    public static final boolean withCloning = false;
    public static final boolean withCalculating = false;

    public static void main(String[] args) throws GitAPIException, IOException {
        Logger.priorityLevel = Logger.LogPriority.DEBUG;

        String[] repositoryArray = {
            "https://github.com/spring-projects/spring-framework.git"
            //"https://github.com/hibernate/hibernate-orm.git",
            //"https://github.com/apache/mesos.git"
        };

        for(String repositoryURI: repositoryArray){

            String REPO_DIR = REPOSITORY_PATH + "spring-framework";

            Git git;
            if(withCloning) {
                git = Git.cloneRepository()
                        .setURI(repositoryURI)
                        .setDirectory(new File(REPO_DIR))
                        .call();
            } else {
                git = Git.open( new File(REPO_DIR+"\\.git" ));
            }

            if(withCalculating){
                Runner.main(new String[] {REPO_DIR, "result_files"});
            }

            Iterable<RevCommit> logs = git.log()
                    .addPath("README.md")
                    .call();
            int count = 0;
            for (RevCommit rev : logs) {
                System.out.println("Commit: " + rev  + ", name: " + rev.getName() + ", id: " + rev.getId().getName() + " date: " + rev.getCommitTime());
                count++;
            }
            System.out.println(count);

            // run rscript file(s?)
            CommandLineExecutor.runRScriptFromPath();
        }

        if(false) {
            try {

//                HashSet<String> allCommits = new HashSet<>();
//
//                int bugFixL = 0, bugProneL = 0;
//                //GET ALL COMMITS
//                ClassEntityRepository.allClasses = CommitParser.parseToClassEntity("data_files/gitData.xlsx");
//
//                //FIND BUG FIXING COMMITS
//                for (ClassEntity classEntity : ClassEntityRepository.allClasses) {
//                    allCommits.add(classEntity.commitHash);
//                    if (classEntity.isBuxFixing()) {
//                        ClassEntityRepository.bugFixingClasses.add(classEntity);
//                        //Logger.log("[FOUND BUG FIXING CLASS]" + classEntity.path, Logger.LogPriority.DEBUG);
//                        bugFixL++;
//                    }
//                }
//                System.out.println(bugFixL);


//            //FIND BUG PRONE COMMITS
//            int bpi = 0;
//            for(ClassEntity classEntity:  ClassEntityRepository.allClasses){
//                System.out.println(++bpi + "/" + ClassEntityRepository.allClasses.size());
//                if(classEntity.isBuxProne()){
//                    ClassEntityRepository.bugProneClasses.add(classEntity);
//                    Logger.log("[FOUND BUG PRONE CLASS] " + classEntity.path, Logger.LogPriority.DEBUG);
//                    bugProneL++;
//                }
//            }
//            System.out.println(bugProneL);

                //FOR All CLASSES CALCULATE METRICS
//            LOCMetricsReader.readMetrics("data_files/LocMetricsFiles.csv");
                //GO back in time by git checkout and calculate them for state from particular commit


                FileRepositoryBuilder builder = new FileRepositoryBuilder();
                Repository repository = builder.setGitDir(new File(REPOSITORY_PATH))
                        .readEnvironment() // scan environment GIT_* variables
                        .findGitDir() // scan up the file system tree
                        .build();
                repository.resolve("dc080cb1be4c35398d1d995c3bb8025ccfde6dea");

//
//
                Git git = new Git(repository);
//
//            CheckoutCommand cc = new CheckoutCommand(repository);

//            TO DZIALA NA POJEDYNCZE COMMITY
//            for(String hash: allCommits){
//                CommadLineExecutor.goToCommit(hash);
//                System.out.println(hash);
//            }


                //SPLIT FOR TEST AND LEARNING DATA

                //BUILD MODEL

                //VERIFY

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
