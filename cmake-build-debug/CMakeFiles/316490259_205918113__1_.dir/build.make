# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.17

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Disable VCS-based implicit rules.
% : %,v


# Disable VCS-based implicit rules.
% : RCS/%


# Disable VCS-based implicit rules.
% : RCS/%,v


# Disable VCS-based implicit rules.
% : SCCS/s.%


# Disable VCS-based implicit rules.
% : s.%


.SUFFIXES: .hpux_make_needs_suffix_list


# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /snap/clion/138/bin/cmake/linux/bin/cmake

# The command to remove a file.
RM = /snap/clion/138/bin/cmake/linux/bin/cmake -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = "/home/spl211/Downloads/316490259_205918113 (1)"

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = "/home/spl211/Downloads/316490259_205918113 (1)/cmake-build-debug"

# Include any dependencies generated for this target.
include CMakeFiles/316490259_205918113__1_.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/316490259_205918113__1_.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/316490259_205918113__1_.dir/flags.make

CMakeFiles/316490259_205918113__1_.dir/Client/src/connectionHandler.cpp.o: CMakeFiles/316490259_205918113__1_.dir/flags.make
CMakeFiles/316490259_205918113__1_.dir/Client/src/connectionHandler.cpp.o: ../Client/src/connectionHandler.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="/home/spl211/Downloads/316490259_205918113 (1)/cmake-build-debug/CMakeFiles" --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/316490259_205918113__1_.dir/Client/src/connectionHandler.cpp.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/316490259_205918113__1_.dir/Client/src/connectionHandler.cpp.o -c "/home/spl211/Downloads/316490259_205918113 (1)/Client/src/connectionHandler.cpp"

CMakeFiles/316490259_205918113__1_.dir/Client/src/connectionHandler.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/316490259_205918113__1_.dir/Client/src/connectionHandler.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E "/home/spl211/Downloads/316490259_205918113 (1)/Client/src/connectionHandler.cpp" > CMakeFiles/316490259_205918113__1_.dir/Client/src/connectionHandler.cpp.i

CMakeFiles/316490259_205918113__1_.dir/Client/src/connectionHandler.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/316490259_205918113__1_.dir/Client/src/connectionHandler.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S "/home/spl211/Downloads/316490259_205918113 (1)/Client/src/connectionHandler.cpp" -o CMakeFiles/316490259_205918113__1_.dir/Client/src/connectionHandler.cpp.s

CMakeFiles/316490259_205918113__1_.dir/Client/src/inputTaker.cpp.o: CMakeFiles/316490259_205918113__1_.dir/flags.make
CMakeFiles/316490259_205918113__1_.dir/Client/src/inputTaker.cpp.o: ../Client/src/inputTaker.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="/home/spl211/Downloads/316490259_205918113 (1)/cmake-build-debug/CMakeFiles" --progress-num=$(CMAKE_PROGRESS_2) "Building CXX object CMakeFiles/316490259_205918113__1_.dir/Client/src/inputTaker.cpp.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/316490259_205918113__1_.dir/Client/src/inputTaker.cpp.o -c "/home/spl211/Downloads/316490259_205918113 (1)/Client/src/inputTaker.cpp"

CMakeFiles/316490259_205918113__1_.dir/Client/src/inputTaker.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/316490259_205918113__1_.dir/Client/src/inputTaker.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E "/home/spl211/Downloads/316490259_205918113 (1)/Client/src/inputTaker.cpp" > CMakeFiles/316490259_205918113__1_.dir/Client/src/inputTaker.cpp.i

CMakeFiles/316490259_205918113__1_.dir/Client/src/inputTaker.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/316490259_205918113__1_.dir/Client/src/inputTaker.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S "/home/spl211/Downloads/316490259_205918113 (1)/Client/src/inputTaker.cpp" -o CMakeFiles/316490259_205918113__1_.dir/Client/src/inputTaker.cpp.s

# Object files for target 316490259_205918113__1_
316490259_205918113__1__OBJECTS = \
"CMakeFiles/316490259_205918113__1_.dir/Client/src/connectionHandler.cpp.o" \
"CMakeFiles/316490259_205918113__1_.dir/Client/src/inputTaker.cpp.o"

# External object files for target 316490259_205918113__1_
316490259_205918113__1__EXTERNAL_OBJECTS =

316490259_205918113__1_: CMakeFiles/316490259_205918113__1_.dir/Client/src/connectionHandler.cpp.o
316490259_205918113__1_: CMakeFiles/316490259_205918113__1_.dir/Client/src/inputTaker.cpp.o
316490259_205918113__1_: CMakeFiles/316490259_205918113__1_.dir/build.make
316490259_205918113__1_: /usr/lib/x86_64-linux-gnu/libboost_system.a
316490259_205918113__1_: CMakeFiles/316490259_205918113__1_.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir="/home/spl211/Downloads/316490259_205918113 (1)/cmake-build-debug/CMakeFiles" --progress-num=$(CMAKE_PROGRESS_3) "Linking CXX executable 316490259_205918113__1_"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/316490259_205918113__1_.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/316490259_205918113__1_.dir/build: 316490259_205918113__1_

.PHONY : CMakeFiles/316490259_205918113__1_.dir/build

CMakeFiles/316490259_205918113__1_.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/316490259_205918113__1_.dir/cmake_clean.cmake
.PHONY : CMakeFiles/316490259_205918113__1_.dir/clean

CMakeFiles/316490259_205918113__1_.dir/depend:
	cd "/home/spl211/Downloads/316490259_205918113 (1)/cmake-build-debug" && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" "/home/spl211/Downloads/316490259_205918113 (1)" "/home/spl211/Downloads/316490259_205918113 (1)" "/home/spl211/Downloads/316490259_205918113 (1)/cmake-build-debug" "/home/spl211/Downloads/316490259_205918113 (1)/cmake-build-debug" "/home/spl211/Downloads/316490259_205918113 (1)/cmake-build-debug/CMakeFiles/316490259_205918113__1_.dir/DependInfo.cmake" --color=$(COLOR)
.PHONY : CMakeFiles/316490259_205918113__1_.dir/depend

