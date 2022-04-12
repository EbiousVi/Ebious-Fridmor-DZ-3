FROM bellsoft/liberica-openjdk-alpine-musl:11.0.14.1-1
LABEL maintainer="EbiousVi" maintainer="Fridmor" github="https://github.com/EbiousVi/Ebious-Fridmor-DZ-3"
ARG BOT=target/pre-reform-dating-bot.jar
WORKDIR /opt/project
COPY  ${BOT} .
EXPOSE 6128
ENTRYPOINT ["java", "-jar", "pre-reform-dating-bot.jar"]