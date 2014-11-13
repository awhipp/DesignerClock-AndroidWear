# CMSC434 - IA05 - Building an Android UI

## Alexander Whipp and Enoch Hsiao

### Description

This repository contains code for a clock app designed for round Android Wear
devices, specifically the Moto 360. The application currently features persistent
settings and two different clock designs. The first design is a simple text-based
digital clock face with an option for either 12-hour or 24-hour formatting. The
second design is a minimalistic analog clock face where the background color
gradually shifts to correspond to the time of day.

### How to Run

* Import the project into Android Studio (Beta) from this repository.
  * Android Studio has built in VCS support
  * Cloning the repository locally and importing from there is also an option
* Compile the code and verify that all packages/dependencies have been properly loaded
* Create an Android Wear emulator from the Android Virtual Device (AVD) manager.
  * Though the application was designed for round watches, it appears that the
round watch emulator still displays a square display area.
* Run the application and select the Android Wear AVD
  * Alternatively, start up the Android Wear AVD and run the application, attaching it
to the existing AVD
