package org.pwr.tool;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.pwr.conf.SETTINGS;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static jdk.nashorn.internal.codegen.CompilerConstants.CONSTANTS;

public class GitTool {


    public static Git openRepository() throws GitAPIException, IOException {
        return Git.open(new File(SETTINGS.REPOSITORY_PATH + "\\.git"));
    }


    public static Git cloneRepository(String address) throws GitAPIException {
        return Git.cloneRepository()
                .setURI(address)
                .setDirectory(new File(SETTINGS.REPOSITORY_PATH))
                .call();
    }

    public static void main(String[] args) throws IOException, GitAPIException {

        ArrayList<String> commitHashes = new ArrayList<>();

        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repo = builder.setGitDir(new File("C:\\MMSE\\Spring Framework\\"+"\\.git")).setMustExist(true).build();
        Git git = new Git(repo);
        Iterable<RevCommit> log = git.log().call();
        for (Iterator<RevCommit> iterator = log.iterator(); iterator.hasNext();) {
            RevCommit rev = iterator.next();
            commitHashes.add(rev.getName());
            System.out.println(rev.getName());
            //logMessages.add(rev.getFullMessage());
        }

        for(int i = commitHashes.size()-1; i>=0; i--){

            String gitHash = commitHashes.get(i);

            //Zapisywanie
            File file = new File("result_files/"+ gitHash + ".csv");
            // creates the file if not exist
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            //Tu Checkout
            //Tu liczenie metryczki
            for(int in = 0; in<100; in++){
                writer.write("C:\\MMSE\\Spring Framework\\src\\test\\java\\org\\springframework\\expression\\spel\\support\\BeanFactoryTypeConverter.java, 112, 53, 75, 20, 16, 0, 21, 132, 15, 99, false;\n");
            }
            writer.flush();
            writer.close();
            System.out.println("Write " + i + "/" + commitHashes.size());
        }

    }

}
