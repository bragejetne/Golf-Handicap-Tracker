# Golf Handicap Application

## Overview

The Golf Handicap Application is a JavaFX-based application designed to help golfers calculate and manage their golf handicaps. It allows users to input their scores, select golf courses, and visualize their performance over time through a bar chart representation. The application maintains a record of scores, calculates the user's current handicap based on recent rounds, and saves this information for future reference.

## Features

- **Handicap Calculation**: Automatically computes the golf handicap based on the entered score and selected course.
- **Course Information**: Provides slope and rating for various golf courses.
- **Score Management**: Keeps track of the last twenty rounds played by the user.
- **Data Visualization**: Displays the user's performance in a bar chart format.
- **File Storage**: Records user's scores and handicap history in text files.

## Project Structure

The project is organized into several packages, each serving a specific purpose:

- `golfhandicaptracker`: Contains the main application logic, including classes for handling user input, calculating handicaps, and managing scores.
  - **GolfHandicapApp**: The main entry point of the application.
  - **GolfHandicapController**: Manages the UI interactions and updates the displayed information.
  - **HandicapPlayedTo**: Handles the logic for calculating and storing the user's handicap.
  - **TotalHandicap**: Computes the overall handicap from recent rounds.
  - **HandicapHistory**: Responsible for writing handicap data to files.
  - **RegisterLocalScore**: An interface defining methods for handicap calculations.

## Usage

1. **Running the Application**: Execute the `GolfHandicapApp` class to launch the application.
2. **Input Scores**: Enter your brutto score in the text field and select a golf course from the dropdown menu.
3. **Update Handicap**: Click the "Update Handicap" button to calculate and display your current handicap.
4. **View Performance**: The bar chart will update to show your performance over your last rounds.

## Testing

The project includes unit tests to ensure the functionality of key components. The tests cover:

- Handicap calculation based on valid and invalid inputs.
- Total handicap calculation and formatting.
- Course information retrieval.
- File writing functionality.

To run the tests, use your IDE's built-in testing capabilities or a command-line tool such as Maven or Gradle.

## Dependencies

- JavaFX: Required for building the user interface.
- JUnit: For unit testing.
