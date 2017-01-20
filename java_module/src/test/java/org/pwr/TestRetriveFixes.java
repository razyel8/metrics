package org.pwr;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.pwr.tool.Utils;

import java.io.File;
import java.io.IOException;

import static org.pwr.Main.REPOSITORY_PATH;

public class TestRetriveFixes {
    public static void main(String[] args) throws GitAPIException, IOException {

        String REPO_DIR = REPOSITORY_PATH + "spring-framework";
        Git git = Git.open( new File(REPO_DIR+"\\.git" ));

        Iterable<RevCommit> logs = git.log()
                .addPath("spring-core/src/main/java/org/springframework/core/Constants.java")
                .call();
        int count = 0;
        for (RevCommit rev : logs) {

            if(Utils.isBugFix(rev.getFullMessage())){
                System.out.println("Fix: " + rev.getFullMessage());

            }
            System.out.println(rev.getCommitTime());

//            if(Utils.isBugFix(rev.getShortMessage())){
//                System.out.println("Fix: " + rev.getShortMessage());
//            }

            //System.out.println("Commit: " + rev  + ", name: " + rev.getName() + ", id: " + rev.getId().getName() + " date: " + rev.getCommitTime());
            count++;
        }
        System.out.println(count);

    }
}
