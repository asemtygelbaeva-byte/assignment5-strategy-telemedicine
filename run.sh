#!/bin/sh
set -eu
cd "$(dirname "$0")"
mvn -q package
exec java -jar target/telemedicine-strategy-1.0.jar

