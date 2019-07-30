Automatic Knowledge Uploader is a tool for synchronizing the contents of your a directory with the KMS storage.

# Installation
Download the distribution package and unzip it in any location, that does't require administrator permissions (e.g. under `C:\Users\User\`).

Distribution package (Windows x64)
https://gitlab.rz.uni-bamberg.de/mobi/simutool/aku-client/blob/master/AKU.zip

Important files:
- **aku-start.bat** - double click this script to start application
- **aku — autorun Shortcut** - a shortcut for adding the app to autorun. If you want the app to start automatically on each system start, drag and drop this file to your autorun folder (typically `C:\Users\valen\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup`).
- **aku.jar** - the app core
- **config.yaml** - configuration file. 

### Usage:
1. When you start the programm for the first time, app will ask you to enter some data: email, password, host address and directory you want to synchronize.
    **Changing directory:** to change the directory later, go to `config.yaml` and delete the whole line with `observeDirectory` variable, then restart the app with `aku-start.bat`.
2. Program will watch your directory and detect new files and folders. When something is found, it will ask you whether you want to send this document to the server. 
3. If you choose to send your file, the app will ask to enter some metadata. Provide the document with a title, description, optionally you can link it to an Activity and add any number of Relations.
4. After sending you get a confirmation that you data succesfully arrived.


# Building instructions

AKU-client is a Maven app with JavaFX GUI. App.java is the entry point, you can start it as Java Application.

Requires compiler complience level 1.8 and higher (change under `Properties > Java Compiler` if any problems occur).

#### Step 1: Build jar
Run in Eclipse with `Maven build` configuration, setting goals to `package`.

#### Step 2: Add to package
Find file `target/aku-0.0.1-SNAPSHOT-jar-with-dependencies.jar`. and rename it to `aku.jar`. Replace the file with the same name in the distribution package that we provide.