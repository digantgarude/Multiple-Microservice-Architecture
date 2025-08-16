# Start infrastructure first
docker-compose up -d mysql zookeeper kafka-broker keycloak_db

# Wait for a minute
sleep 30

docker-compose up -d kafka-schema-registry keycloak kafka-ui

# Wait for a minute
sleep 30

# Start applications
docker-compose up -d inventoryservice bookingservice orderservice apigateway