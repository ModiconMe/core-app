package edu.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.jvm.JvmTestSuite

class TestPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.plugins.apply('jvm-test-suite')
        project.testing {
            suites {
                intTest(JvmTestSuite) {
                    useJUnitJupiter()
                    dependencies {
                        implementation project
                    }
                }
            }
        }

        project.tasks.named('check') {
            dependsOn(testing.suites.intTest)
        }

    }
}
