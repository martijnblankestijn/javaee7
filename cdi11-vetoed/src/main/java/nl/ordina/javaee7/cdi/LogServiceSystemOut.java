package nl.ordina.javaee7.cdi;

import javax.enterprise.context.Dependent;

// From the spec
// 12.1 Bean Archives
// An implicit bean archive is any other archive which contains one or more bean classes
// with a bean de?ning annotation, or one or more session beans
//
// 2.5 Bean Defining Annotations
// Any scope type is a bean de?ning annotation.
// If a scope type is declared on a bean class, then the bean class is said to have a
// bean de?ning annotation.
@Dependent
public class LogServiceSystemOut implements LogService {
    public void log(String msg) {
        System.out.println("[" + msg + "]");
    }
}
