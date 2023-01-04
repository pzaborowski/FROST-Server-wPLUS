From tomcat:9-jdk11

COPY target/docker_deps/ ${CATALINA_HOME}/lib/

# Copy to images tomcat path
ARG ARTIFACT_FILE
ARG ARTIFACT_DIR
# COPY target/${ARTIFACT_FILE} /tmp/FROST-Server.war

COPY target/${ARTIFACT_DIR} ${CATALINA_HOME}/webapps/
COPY ../Plugins/FROST-Server-PLUS/target/FROST-Server.Plugin.PLUS-2.0.0.jar target/FROST-Server.HTTP-2.0.0/WEB-INF/lib/
# RUN unzip -d ${CATALINA_HOME}/webapps/ /tmp/FROST-Server.war \
#    && rm /tmp/FROST-Server.war \
RUN addgroup --system --gid 1000 tomcat \
    && adduser --system --uid 1000 --gid 1000 tomcat \
    && chgrp -R 0 $CATALINA_HOME \
    && chmod -R g=u $CATALINA_HOME

USER tomcat
