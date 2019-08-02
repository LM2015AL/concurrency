package com.luban.layout;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;
import static java.lang.System.out;

public class JOLExample1 {

    public static void main(String[] args) throws Exception {

        out.println(VM.current().details());
//        out.println(ClassLayout.parseClass(A.class).toPrintable());
//        out.println(ClassLayout.parseClass(new A().getClass()).toPrintable());
        out.println(ClassLayout.parseInstance(new A()).toPrintable());
    }
}