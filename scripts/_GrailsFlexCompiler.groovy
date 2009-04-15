import grails.util.Environment

Ant.property(environment:"env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"

GroovyClassLoader loader = new GroovyClassLoader(rootLoader)
Class groovyClass = loader.parseClass(new File("${gdsflexPluginDir}/src/groovy/org/granite/web/util/WebCompile.groovy"))
GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance()

groovyObject.init("${basedir}/web-app/WEB-INF")

target(flexCompile: "Compile the mxml to swf file") {
    if(Environment.current==Environment.DEVELOPMENT) {
        Ant.copy(tofile: "${basedir}/web-app/WEB-INF/flex/flex-config.xml",
                 file:"${gdsflexPluginDir}/src/flex/flex-config-debug.xml")
    }else {
        Ant.copy(tofile: "${basedir}/web-app/WEB-INF/flex/flex-config.xml",
                 file:"${pluginBasedir}/src/flex/flex-config-release.xml",
                 overwrite: true)
    }
    groovyObject.compile("${basedir}/grails-app/views/mxml",grailsAppName)
}