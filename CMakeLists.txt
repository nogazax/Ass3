cmake_minimum_required(VERSION 3.17)
project(316490259_205918113__1_)

set(CMAKE_CXX_STANDARD 14)

add_executable(316490259_205918113__1_

        Client/src/connectionHandler.cpp
        Client/src/inputTaker.cpp
        Client/include/connectionHandler.h
        Client/include/inputTaker.h)

set(Boost_USE_STATIC_LIBS   ON)
set(Boost_USE_MULTITHREADED   ON)
set(Boost_USE_STATIC_RUNTIME   OFF)
find_package(Boost REQUIRED COMPONENTS system)
include_directories(include)

SET(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -pthread")



target_link_libraries(316490259_205918113__1_ Boost::system)

