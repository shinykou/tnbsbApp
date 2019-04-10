#!/bin/sh

# base command , you can add arguments for different environment, such as "-P offline"


function compile_provider() {
    mvn clean -U package -Dmaven.test.skip=false -P$PROFILE
}

compile_provider
