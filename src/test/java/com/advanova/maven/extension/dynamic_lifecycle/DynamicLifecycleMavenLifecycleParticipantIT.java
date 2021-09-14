package com.advanova.maven.extension.dynamic_lifecycle;

import com.soebes.itf.jupiter.extension.MavenGoal;
import com.soebes.itf.jupiter.extension.MavenJupiterExtension;
import com.soebes.itf.jupiter.extension.MavenTest;
import com.soebes.itf.jupiter.maven.MavenExecutionResult;
import org.assertj.core.api.Assertions;

import static com.soebes.itf.extension.assertj.MavenITAssertions.assertThat;


@MavenJupiterExtension
class DynamicLifecycleMavenLifecycleParticipantIT {

    @MavenTest
    @MavenGoal("my_test")
    void testLifecycle(MavenExecutionResult result) {
        assertThat(result).isSuccessful();
        assertThat(result.getMavenLog()).info().contains("--- echo-maven-plugin:0.4.0:echo (echo-info) @ dynamic-lifecycle-maven-extension-test ---");
    }
}