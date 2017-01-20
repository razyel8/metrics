package org.pwr.runners;

import java.io.IOException;

public class TestCommandLineExecutor {

    public static void main(String[] args) throws IOException {
        System.out.println(CommandLineExecutor.runScriptAndGetOutput("dir"));
    }
}
