package org.pwr;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.pwr.conf.SETTINGS;
import org.pwr.metric.CKMetricCalculator;
import org.pwr.metric.CommitterMetricCalculator;
import org.pwr.metric.MetricCalculatorEngine;
import org.pwr.metric.ConcurrentMetricCalculatorEngine;
import org.pwr.model.ClassEntity;
import org.pwr.parser.CommitParser;
import org.pwr.repository.ClassEntityRepository;
import org.pwr.tool.GitTool;
import org.pwr.tool.Utils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class MetricsMain {

    static LinkedHashMap<String, List<ClassEntity>> toCalculateBugs;
    static LinkedHashMap<String, List<ClassEntity>> allClassEntitiesForCKCalculation;
    static LinkedHashMap<String, List<ClassEntity>> allClassEntitiesForCommitterCalculation;

    public static void loadRepositoriesToDB() throws GitAPIException, IOException {
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

        toCalculateBugs = ClassEntityRepository.getBugClassEntities();
        allClassEntitiesForCKCalculation = ClassEntityRepository.getAllClassEntitiesForCalculation(toCalculateBugs);
        allClassEntitiesForCommitterCalculation = allClassEntitiesForCKCalculation; //TODO: DEEP COPY, CZY TO POTRZEBNE?
    }


    public static void main(String[] args) throws IOException, GitAPIException {
        loadRepositoriesToDB();
        calculateAllMetricsAndUpdateDb();
    }

    private static void calculateAllMetricsAndUpdateDb() {
        LinkedList<MetricCalculatorEngine> calculationEngines = new LinkedList<>();
        calculationEngines.add(new MetricCalculatorEngine(allClassEntitiesForCKCalculation, new CKMetricCalculator()));
        calculationEngines.add(new ConcurrentMetricCalculatorEngine(allClassEntitiesForCommitterCalculation, new CommitterMetricCalculator()));
        for(MetricCalculatorEngine mec:calculationEngines){
            mec.start();
            try {
                mec.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
