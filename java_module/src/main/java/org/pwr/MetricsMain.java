package org.pwr;

import org.apache.commons.collections.map.LinkedMap;
import org.eclipse.jdt.internal.core.SourceType;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.pwr.conf.SETTINGS;
import org.pwr.model.ClassEntity;
import org.pwr.parser.CommitParser;
import org.pwr.repository.ClassEntityRepository;
import org.pwr.runners.CommandLineExecutor;
import org.pwr.tool.GitTool;
import org.pwr.tool.Utils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MetricsMain {

    public static void loadRepositories() throws GitAPIException, IOException {
        Git git;
        if(SETTINGS.CLONE_REPOSITORY){
            git = GitTool.cloneRepository(SETTINGS.remoteRepository);
        } else {
            git = GitTool.openRepository();
        }

        //TODO: Zrobić na wątkach
        CommitParser.parseToClassEntity(SETTINGS.depressFile);
        System.out.println("Class loaded to DB");
        Utils.setUpBugProne();


        //Wybór klas do liczenia metryk
        //1. Weź wszystkie ClassEntity z bugami
        //2. Dla każdego Class entity z bugiem znajdź odpoweidnik bez buga (przyjaciela) wg algorytmu:
        //  a. weź pierwszą bezbugową klasę z tego samego pakietu. jeśli nie ma w danym pakiecie, to olej sprawę i idź dalej

        LinkedHashMap<String, List<ClassEntity>> toCalculateBugs = ClassEntityRepository.getBugClassEntities();
        LinkedHashMap<String, List<ClassEntity>> allClassEntitiesForCalculation = ClassEntityRepository.getAllClassEntitiesForCalculation(toCalculateBugs);

        //LinkedHashMap<String, Integer> checkoutsMap = ClassEntityRepository.getBugRevisions();

        for(String hash : allClassEntitiesForCalculation.keySet()){
            CommandLineExecutor.runGitCheckout(hash);

            //Calsulate CK metrics
        }

    }


    public static void main(String[] args) throws IOException, GitAPIException {
        loadRepositories();
    }

}
