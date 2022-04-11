FROM bellsoft/liberica-openjdk-debian:11.0.14.1-1
LABEL maintainer="EbiousVi" maintainer="Fridmor" github="https://github.com/EbiousVi/Ebious-Fridmor-DZ-3"
ARG AVATAR=target/pre-reform-avatar.jar
WORKDIR /opt/project
COPY  ${AVATAR} .
RUN mkdir -p /usr/share/fonts/truetype/
COPY src/main/resources/OldStandardTT-Regular.ttf /usr/share/fonts/truetype/
EXPOSE 6016
ENTRYPOINT ["java", "-jar", "pre-reform-avatar.jar"]