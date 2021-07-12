FROM mongo:4.4.1-bionic

RUN echo "rs.initiate();" > /docker-entrypoint-initdb.d/replica-init.js

CMD ["--replSet", "single_node_replica_set"]
