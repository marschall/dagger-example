

Notes
-----

In Eclipse with m2e you should enable annotation processing under

    Window > Preferences > Maven > Annotation Processing


mvn dependency:get -Dartifact=com.h2database:h2:1.4.182
mvn dependency:copy -Dartifact=com.h2database:h2:1.4.182 -DoutputDirectory=.

db driver goes to:
$CATALINA_BASE/lib


TOMCAT_HOME/conf/catalina.properties
org.apache.catalina.startup.ContextConfig.jarsToSkip=*.jar

<Context processTlds="false">
    <JarScanner className="org.my.tomcat.NullJarScanner"/>
</Context>
