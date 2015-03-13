parallelExecution in ThisBuild := false

javaOptions += "-XX:ReservedCodeCacheSize=512M"

javaOptions += "-Xmx16G"

// uncomment if you want to train the parser
javaOptions += "-Xss800m"

fork in test := true
