package org.pwr.parser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MethodParser {

    public static void getMehods(String path){
        // creates an input stream for the file to be parsed
        FileInputStream in = null;
        try {
            in = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // parse it
        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(in);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // visit and print the methods names
        new MethodVisitor().visit(cu, null);
    }



    public static void main(String[] args) throws Exception {
        MethodParser.getMehods("src/main/java/org/pwr/Example.java");
    }

    /**
     * Simple visitor implementation for visiting MethodDeclaration nodes.
     */
    private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            /* here you can access the attributes of the method.
             this method will be called for all methods in this
             CompilationUnit, including inner class methods */
            System.out.println(n.getModifiers() + " " + n.getName() + " " + n.getParameters());
            System.out.println("Lines: " + "[" + n.getBegin().line + " - " + n.getEnd().line + "]");
            System.out.println();
            super.visit(n, arg);
        }
    }
}
