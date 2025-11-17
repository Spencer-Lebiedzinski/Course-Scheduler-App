# Course Scheduler Environment Setup

These steps recreate the Apache Derby environment that NetBeans previously configured automatically so you can run the Swing app without typing manual commands each time.

## 1. Install Apache Derby
1. Download the latest binary distribution from <https://db.apache.org/derby/>.
2. Extract it somewhere permanent (e.g., `/Applications/derby` on macOS or `C:\derby` on Windows). The folder that contains `bin/` and `lib/` is the value you will use for `DERBY_HOME`.

## 2. Restore or create the database files
1. Locate your old Derby database folder named `CourseScedulerDBSpencersml7204`. NetBeans typically stored it under `~/Library/NetBeans/<version>/derby/` (macOS) or inside your NetBeans project folder.
2. Copy that entire folder (it contains `service.properties`, `log/`, and multiple `.dat` files) into a directory you control, e.g. `~/databases/CourseScedulerDBSpencersml7204`.
3. The parent folder (e.g. `~/databases`) becomes your `DERBY_SYSTEM_HOME`.

## 3. Configure environment variables
Add the following to your shell profile (`~/.zshrc`, `~/.bash_profile`, etc.) so they are always available:

```bash
export DERBY_HOME=/Applications/derby                # adjust if you installed elsewhere
export DERBY_SYSTEM_HOME=$HOME/databases            # parent folder that contains CourseScedulerDBSpencersml7204
export DERBY_PORT=1527                              # optional; defaults to 1527
```

Open a new terminal (or source the profile) so the variables apply.

## 4. Use the helper script
1. Ensure `scripts/start-course-db.sh` is executable (`chmod +x scripts/start-course-db.sh` done already in repo).
2. Start the Derby network server whenever you log in: `./scripts/start-course-db.sh`.
   - The script automatically picks up `DERBY_HOME`, `DERBY_SYSTEM_HOME`, and `DERBY_PORT`.
   - It validates that the database directory exists and then calls `startNetworkServer -p 1527`.
3. Optionally, create a login item or `launchd` agent to run this script automatically at boot.

## 5. Run the Course Scheduler
With the Derby server running, build and launch the Swing app (from the project root):

```bash
mkdir -p build/classes
javac -d build/classes src/*.java
java -cp build/classes MainFrame
```

The GUI now connects to `jdbc:derby://localhost:1527/CourseScedulerDBSpencersml7204` without needing additional manual steps.
