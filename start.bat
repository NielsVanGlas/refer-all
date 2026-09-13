@echo off
setlocal

echo #################### Avvio database ####################
cd db
docker compose up -d
cd ..

echo ############### Avvio backend + frontend ###############
docker compose up --build -d

echo.
echo ########################################################
echo # Servizi avviati:                                     #
echo #  - Database : localhost:5432                         #
echo #  - Backend  : http://localhost:8053                  #
echo #  - Frontend : http://localhost:3000                  #
echo #  - Swagger  : http://localhost:8053/swagger-ui.html  #
echo ########################################################
