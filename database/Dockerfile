FROM postgres:13.3
LABEL maintainer="EbiousVi" maintainer="Fridmor" github="https://github.com/EbiousVi/Ebious-Fridmor-DZ-3"
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=dating
ENV PGDATA=/var/lib/postgresql/data/pgdata
COPY initdb.sql /docker-entrypoint-initdb.d/
EXPOSE 5432
