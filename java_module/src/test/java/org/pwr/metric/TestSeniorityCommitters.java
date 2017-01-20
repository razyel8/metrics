package org.pwr.metric;

import org.junit.Test;
import org.pwr.enums.BugFix;
import org.pwr.enums.BugProne;
import org.pwr.model.ClassEntity;
import org.pwr.repository.ClassEntityRepository;

public class TestSeniorityCommitters {

    @Test
    public void testSoC(){

        ClassEntity ce = new ClassEntity();
        ce.setCommitHash("b0de99bc8cb0a2e20c49e7f627139204bfc13a13");
        ce.setCommitAuthor("Arjen Poutsma");
        ce.setPath("spring-web-reactive/src/main/java/org/springframework/http/server/reactive/UndertowHttpHandlerAdapter.java");
        ce.setActionType("MODIFIED");
        ce.setCommitDate(1467703218000L);
        ce.setIsBug(BugProne.UNKNOWN);
        ce.setIsFix(BugFix.TRUE);


    }
}
