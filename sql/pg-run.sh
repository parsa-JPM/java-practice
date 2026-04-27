#!/bin/bash

docker start pg-practice 2>/dev/null || docker run --name pg-practice \
  -e POSTGRES_PASSWORD=secret \
  -e POSTGRES_USER=parsa \
  -e POSTGRES_DB=practicedb \
  -p 5432:5432 \
  -v pg-practice-data:/var/lib/postgresql/data \
  -d postgres:17

echo "PostgreSQL is running on localhost:5432"