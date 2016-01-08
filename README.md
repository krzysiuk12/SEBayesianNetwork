# SE Bayesian Network

# jSMILE Installation

1. Go to https://dslpitt.org/genie/index.php/downloads
2. Download appropriate jSMILE library appropriate for your OS. (SMILE Wrappers for Java and .NET > Java)
3. Create directory and unzip content of jSMILE.zip within it.

# Run

1. Download sources.
2. Import IntellijIdea's project.
3. Build project.
4. Open Terminal window.
5. Go to ${PROJECT_PATH}/out/artifacts.
6. Enter command: java -Djava.library.path="PATH/TO/jSMILE" -jar SEBayesianNetwork.jar (Path To jSMILE is path to directory that contains jsmile.dll and smile.jar, not to jar file itself)
