package org.pwr;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;

import java.io.File;
import java.io.IOException;

import static org.pwr.Main.REPOSITORY_PATH;

public class TestRetriveBugs {

    public static void main(String[] args) throws GitAPIException, IOException {
        String REPO_DIR = REPOSITORY_PATH + "spring-framework";
        Git git = Git.open( new File(REPO_DIR+"\\.git" ));

        Iterable<RevCommit> logs = git.log()
                .addPath("spring-core/src/main/java/org/springframework/core/Constants.java")
                .call();
        int count = 0;

//        TreeWalk treeWalk = new TreeWalk(git.getRepository());
//
//
//        treeWalk.addTree(tree);
//        treeWalk.setRecursive(false);
//        while (treeWalk.next()) {
//            if (treeWalk.isSubtree()) {
//                System.out.println("dir: " + treeWalk.getPathString());
//                treeWalk.enterSubtree();
//            } else {
//                System.out.println("file: " + treeWalk.getPathString());
//            }
//        }

        ObjectId commitId = ObjectId.fromString( "e9649b3a341cfa2a01845d22b78d4d27733ffd0d" );
        RevWalk revWalk = new RevWalk(git.getRepository());
        RevCommit commit = revWalk.parseCommit( commitId );
        revWalk.close();

        TreeWalk treeWalk = new TreeWalk(git.getRepository());

        Ref r = git.getRepository().findRef("e9649b3a341cfa2a01845d22b78d4d27733ffd0d");

        treeWalk.addTree(commit.getTree());
        treeWalk.setRecursive(false);
        while (treeWalk.next()) {
            if (treeWalk.isSubtree()) {
                treeWalk.enterSubtree();
            } else {
                System.out.println("file: " + treeWalk.getPathString());
            }
        }



//        for (RevCommit rev : logs) {
//
//            if(Utils.isBugFix(rev.getFullMessage())){
//                TreeWalk treeWalk = new TreeWalk(git.getRepository());
//
//
//                treeWalk.addTree(rev.getTree());
//                treeWalk.setRecursive(false);
//                while (treeWalk.next()) {
//                    if (treeWalk.isSubtree()) {
//                        treeWalk.enterSubtree();
//                    } else {
//                        System.out.println("file: " + treeWalk.getPathString());
//                    }
//                }
//
//            }
//            System.out.println(rev.getCommitTime());
//            count++;
//        }
        System.out.println(count);
    }
}
