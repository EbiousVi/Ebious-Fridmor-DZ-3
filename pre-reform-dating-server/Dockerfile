FROM bellsoft/liberica-openjdk-alpine-musl:11.0.14.1-1
LABEL maintainer="EbiousVi" maintainer="Fridmor" github="https://github.com/EbiousVi/Ebious-Fridmor-DZ-3"
ARG SERVER=target/pre-reform-dating-server.jar
WORKDIR /opt/project
COPY  ${SERVER} .
EXPOSE 6064
ENTRYPOINT ["java", "-jar", "pre-reform-dating-server.jar"]