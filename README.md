# Welcome Students of 4156

Please follow the assignment specifications on Courseworks when completing this project.

Command for style check: mvn checkstyle:check

Command for generating coverage: mvn jacoco:prepare-agent test install jacoco:report
source: https://stackoverflow.com/questions/51964147/java-code-coverage-in-visual-studio-code 

Static bug analyzer used: PMD

Steps followed:
- Download pmd
- Follow steps under "Quick Start" in https://pmd.github.io for macOS
- Run: pmd check -f text -R rulesets/java/quickstart.xml -d /Users/     madhurachatterjee/Desktop/4156-Miniproject-2024-Students-Java

where the path after '-d' is the directory with the java files
