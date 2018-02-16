# Billing

## Introduction

Billing for systems with transfer of units from one user to another is a critical business system.

Given the approaches to building a distributed system and the focus on scaling the system within the AdHive project, higher requirements are attributed to the billing system. As a result, the concept of non-blockable billing was born, where each transaction on the account will be an increment / decrement to the previous state of the account. Account status at any moment in time will be only the sum of operations, which allows getting rid of account blocking or deadlocking in the build.

This innovation, combined with blockchain, allows abolishing unauthorized changes in account balances.

The possibility of repeating the change operations that are stored in a separate structure guarantees the restoration of not only functional performance, but also the restoration of data in the event of almost any failures.

## Requirements for the billing system.

As for any software for billing, there are functional and non-functional requirements.

Functional requirements:
+ Performing an account setup operation (Basic);
+ Performing increments / decrements on accounts (Basic);
+ Obtaining an account balance (Basic);
+ Receiving account change history for the specified period (Basic);
+ Saving initial queries;
+ Performing group operations (decrement over one and increment over the second);
+ Identifying and authenticating the source of the request;
+ Authorizing operation rights for the query initializer;
+ Getting metrics (ops \ avg latency \ count) by working with the repository and API methods;
+ Obtaining metrics for the number of records in each of the repositories;
+ Ability to view the connection of "message source" - "records in the repository";
+ Lack of uncontrolled history change;
+ Presence of massive operations with data.

Not functional requirements:
+ Proportional Performance Scaling;
+ Proportional scaling of storage volume;
+ Data backup;
+ Ability of restoring state integrity;
+ Ability of transforming storage methods without stopping work;
+ Ability of recording dumps for recovery;
+ No SPOF (No single point of failure);
+ Ability of scaling individual segments of the system;
+ Ability of carrying out maintenance works without degrading basic billing functionality;
+ Lack of degradation of non-logarithmic nature at an increase in the number of records.

## Implementation architecture

To meet the requirements, the following billing implementation scheme was adopted.

<img align="right" src="https://github.com/adhivetv/adhive.tv/blob/master/image/biling.png" />

In essence, billing is divided into 3 layers:

+ External services - services of external interaction with the system;
+ Compute-cache services - layer for caching and performing complex operations;
+ Persist (story) services - a layer of persistent data storage.

Also, a security layer is included in the system, which is common for all systems.

External services – These include all the services that provide external interaction with systems. To avoid cluttering the API, 2 subtypes of "external services" were introduced - public and private.
Public external services: Steel RestAPI, Api for managing mass transactions, integration services with external payment systems.
Private external services – These include services for integration with service systems (administration, metrics).
All messages received from "external services" enter the security layer, which performs identification and authentication of the source of the message and authorization of the access initializer.
In the case of correctness, messages associated with data changes are transmitted to the Historical Service, which stores messages to prevent loss of the message when the external system is running asynchronously. If the message is not related to data changes, it is transferred to the computational grid.

## To a large extent, the grid is built on an IMDG solution.

Grid tasks:
+ Converting messages into an operation on the account, transferring it to the storage layer, and updating the internal cached aggregates;
+ Providing quick access to hot data;
+ Ensuring the implementation of complex operations associated with the transformation of data;
+ Providing basic reporting functionality;
+ Providing triggers for notification of external systems (for example, running out of money or identification of fraud).

To ensure the preservation of data, a storage layer is selected. It represents a set of repositories for storing all the data that the system operates on.

To avoid degradation of the basic functionality, auxiliary structures for storing aggregates and indexes are provided at the level of the repositories. This allows the operation of the most important billing functional codes in the event of computational grid degradation.

## The advantages of the chosen approach

The given implementation has a number of advantages:
+ Segment scalability - to increase performance or increase data capacity, only part of the system needs to be scaled;
+ Resilience to failure of entire storage segments - If the persistent storage is lost, it is possible to continue working with the grid. If the grid is lost, it is possible to provide basic functionality with a persistent layer;
+ Atomicity of updates. In view of the division into atomic services, it is possible to isolate the life cycle of software solutions without the need for a common context (development, testing, commissioning, updating, replacement);
+ Avoidance of unauthorized data changes, even with access to the repository. Thanks to blockchain it is impossible to change or add previously recorded data;
+ Maintaining low response times under high strain thanks to the presence of hot data in memory;
+ The ability to prioritize operation queues over the storage structure.

## Progress of implementation

To ensure the development of a Minimum value product, the following implementation strategy was chosen:
+ Implementation of rest-api + basic functions of the storage layer + administration panel;
+ Integration with the security service;
+ Metrics;
+ Saving incoming messages;
+ Blockchain;
+ Grid.
