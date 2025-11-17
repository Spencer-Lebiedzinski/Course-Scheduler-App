#!/usr/bin/env bash
# Helper script to start the Apache Derby network server for the Course Scheduler DB.

set -euo pipefail

# Allow overriding via environment variables, but provide sensible defaults.
DERBY_HOME="${DERBY_HOME:-/Applications/derby}"
DERBY_SYSTEM_HOME="${DERBY_SYSTEM_HOME:-$HOME/databases}"
DERBY_PORT="${DERBY_PORT:-1527}"
DB_NAME="CourseScedulerDBSpencersml7204"

export DERBY_HOME
export DERBY_OPTS="-Dderby.system.home=$DERBY_SYSTEM_HOME"

if [ ! -d "$DERBY_HOME/bin" ]; then
  echo "Derby binaries not found. Set DERBY_HOME to your Apache Derby install directory." >&2
  exit 1
fi

if [ ! -d "$DERBY_SYSTEM_HOME/$DB_NAME" ]; then
  cat >&2 <<EOF
Database folder '$DERBY_SYSTEM_HOME/$DB_NAME' not found.
Copy your existing Derby database directory (contains service.properties/log) into this location,
or set DERBY_SYSTEM_HOME to the parent folder that already holds $DB_NAME.
EOF
fi

echo "Starting Derby server from $DERBY_HOME with databases at $DERBY_SYSTEM_HOME (port $DERBY_PORT)..."
"$DERBY_HOME/bin/startNetworkServer" -p "$DERBY_PORT" "$@"
