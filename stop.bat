@echo off
echo ################ Stop backend + frontend ################
docker compose down

echo #################### Stop  database ####################
cd db
docker compose down
cd ..

echo # Tutti i container fermati.                           #
