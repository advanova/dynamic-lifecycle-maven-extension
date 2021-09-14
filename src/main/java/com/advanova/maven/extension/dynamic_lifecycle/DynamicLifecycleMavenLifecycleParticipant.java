package com.advanova.maven.extension.dynamic_lifecycle;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.lifecycle.Lifecycle;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Component(role = AbstractMavenLifecycleParticipant.class, hint = "dynamic_lifecycle")
public class DynamicLifecycleMavenLifecycleParticipant extends AbstractMavenLifecycleParticipant {

    public static final String DYNAMIC_LIFECYCLE_PREFIX = "dynamic.lifecycle.";

    private static final Logger LOGGER= LoggerFactory.getLogger(DynamicLifecycleMavenLifecycleParticipant.class);

    @Inject
    private PlexusContainer container;

    @Override
    public void afterProjectsRead(MavenSession session)
            throws MavenExecutionException {
        session.getCurrentProject().getProperties().forEach((key, value) -> {
            if (String.valueOf(key).startsWith(DYNAMIC_LIFECYCLE_PREFIX)) {
                Lifecycle lifecycle = new Lifecycle(String.valueOf(key).substring(DYNAMIC_LIFECYCLE_PREFIX.length()), Arrays.asList(String.valueOf(value).split(",")), new HashMap<>());
                LOGGER.info("adding lifecycle {} with phases: {}",lifecycle.getId(), lifecycle.getPhases());
                container.addComponent(lifecycle, Lifecycle.class, lifecycle.getId());
            }
        });
    }

}