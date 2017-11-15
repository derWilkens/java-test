package local.paxbase.unitests;

import java.util.Arrays;
import java.util.Collections;

import com.haulmont.cuba.testsupport.TestContainer;

public class StandardTestContainer  extends TestContainer {

    public StandardTestContainer() {
        super();
        appComponents = Collections.singletonList("com.haulmont.cuba");

        appPropertiesFiles = Arrays.asList(
                // List the files defined in your web.xml
                // in appPropertiesConfig context parameter of the core module
                "cuba-app.properties",
                "local/paxbase/app.properties",
                "test-app.properties"
                // Add this file which is located in CUBA and defines some properties
                // specifically for test environment. You can replace it with your own
                // or add another one in the end.
 );

        
        dbDriver = "com.mysql.jdbc.Driver";
        dbUrl = "jdbc:mysql://localhost/crewbase?useSSL=false&amp;allowMultiQueries=true";
        dbUser = "crewbase";
        dbPassword = "1234";
        //springConfig = "cuba-spring.xml";
        
 
    }

//    public static class Common extends StandardTestContainer {
//
//        public static final StandardTestContainer.Common INSTANCE = new StandardTestContainer.Common();
//
//        private static volatile boolean initialized;
//        
//
//        private Common() {
//        }
//
//        @Override
//        public void before() throws Throwable {
//            if (!initialized) {
//                super.before();
//                initialized = true;
//            }
//            setupContext();
//        }
//
//        @Override
//        public void after() {
//            cleanupContext();
//            // never stops - do not call super
//        }
//    }
}