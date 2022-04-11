FROM bellsoft/liberica-openjdk-alpine-musl:11.0.14.1-1
LABEL maintainer="EbiousVi" maintainer="Fridmor" github="https://github.com/EbiousVi/Ebious-Fridmor-DZ-3"
ARG TRANSLATOR=target/pre-reform-translator.jar
WORKDIR /opt/project
COPY  ${TRANSLATOR} .
EXPOSE 6032
ENTRYPOINT ["java", "-jar", "pre-reform-translator.jar"]