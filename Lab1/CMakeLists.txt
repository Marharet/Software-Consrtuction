cmake_minimum_required(VERSION 3.27)
project(Lab1)

set(CMAKE_CXX_STANDARD 14)

include_directories(src)

add_executable(Lab1
        src/main.cpp
        src/parser.h
        src/parser.cpp)

add_subdirectory(tests)