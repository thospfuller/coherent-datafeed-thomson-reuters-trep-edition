@echo off
echo *** DELETING ALL THE JARS FROM THE R-PACKAGE!! ***
del ..\r-package\inst\java\*.jar
echo "Building via Maven..."
call mvn clean package dependency:copy-dependencies gpg:sign
echo "Deleting the current assembly-#.#-SNAPSHOT.jar..."
del C:\development\software\groovy-2.0.0\lib\assembly-0.9-SNAPSHOT.jar
echo "Copying shaded jar..."
copy assembly\target\assembly-0.9-SNAPSHOT.jar C:\development\software\groovy-2.0.0\lib
echo "Done!!!"
