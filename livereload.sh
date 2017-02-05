#!/usr/bin/env bash
./mvnw -Pcc scala:cc &
./mvnw -Pcc &
gulp
