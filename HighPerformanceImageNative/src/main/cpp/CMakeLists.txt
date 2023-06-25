cmake_minimum_required(VERSION 3.22.1)


project("highperformanceimagenative")


add_library(
        highperformanceimagenative SHARED
        highperformanceimagenative.cpp
        lutFilterImage.cpp
        imageMinPixelSize.cpp)

find_library(
        log-lib
        log)

target_link_libraries(
        highperformanceimagenative
        jnigraphics
        ${log-lib})