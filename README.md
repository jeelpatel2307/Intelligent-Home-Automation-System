# Intelligent Home Automation System

This is a sample project demonstrating an Intelligent Home Automation System implemented using Java, Spring Boot, and MQTT.

## Overview

The Intelligent Home Automation System allows users to manage and control various IoT devices in a home environment. The system provides a RESTful API to interact with the devices, enabling users to retrieve information about the devices, create new devices, and send commands to control the devices.

## Architecture

The application is built using a Spring Boot framework and consists of the following components:

1. **Device Controller**: The `DeviceController` class exposes the RESTful API endpoints for managing devices, including retrieving all devices, creating new devices, and sending control commands to devices.
2. **Device Service**: The `DeviceService` class handles the business logic for managing devices, such as saving device information to the database and publishing control commands to the MQTT broker.
3. **Device Repository**: The `DeviceRepository` interface provides the necessary data access methods for the `Device` entity.
4. **MQTT Integration**: The `MqttConfiguration` class sets up the `MqttClient` bean, which is used to publish device control commands to the MQTT broker.
5. **Data Models**: The `Device` entity represents the IoT devices in the home automation system, and the `DeviceCommand` class is a data transfer object used to represent the commands sent to the devices.

The system uses MQTT as the communication protocol between the home automation system and the IoT devices. When a user sends a control command to a device, the `DeviceService` publishes the command to the appropriate MQTT topic, and the device subscribes to that topic to receive and execute the command.

## Technologies Used

- Java 11
- Spring Boot
- Spring Data JPA
- MQTT (Message Queuing Telemetry Transport)
- H2 Database (in-memory database for simplicity)

## Getting Started

1. Set up an MQTT broker (e.g., Mosquitto) and ensure it's running.
2. Clone the repository.
3. Build and run the application using the following command:
    
    ```bash
    ./gradlew bootRun
    
    ```
    
4. The application will start running on [http://localhost:8080](http://localhost:8080/).

## Usage

You can use tools like Postman or cURL to interact with the device management API. Here are some example requests:

**Get All Devices**

```
GET /devices

```

**Create a Device**

```
POST /devices
{
    "name": "Living Room Light",
    "type": "light",
    "location": "living room"
}

```

**Control a Device**

```
POST /devices/{id}/control
{
    "action": "turn_on",
    "parameters": {
        "brightness": 50
    }
}

```

## Future Enhancements

- Implement device discovery and automatic registration to simplify device management.
- Add scheduling and automation rules to automate device control based on user preferences or sensor data.
- Integrate with various IoT device protocols and standards (e.g., Z-Wave, ZigBee, Wi-Fi) to support a wider range of devices.
- Implement energy consumption monitoring and optimization features to improve the system's efficiency.
- Add user authentication and access control to ensure secure access to the home automation system.
- Develop a mobile app or web-based interface for a more user-friendly experience.
- Incorporate machine learning and data analytics to enable intelligent decision-making and predictive maintenance.
