# Chip8 Emulator

## Purpose
The purpose of this project was to investigate Test Driven Development (TDD) from the perspective of a non-trivial project

The project was approached using Test-First principles to evaluate the use of the workflow in future projects.

## Background
Inspired by this article:
https://kyle.space/posts/i-made-a-nes-emulator/

Following guidelines from here:
http://www.multigesture.net/articles/how-to-write-an-emulator-chip-8-interpreter/

Cross Reference at:
https://en.wikipedia.org/wiki/CHIP-8#Opcode_table

## Run
To run this project, build the maven project using `mvn clean install`
A jar will be built in the `target` folder that can then be run using a java VM.

Unit tests for the project can be run using `mvn clean verify`.

Available games to play on the emulator have been added to the test resources folder at `src/test/resources/c8games`