The **TaskManager** is a dispatcher assigned the following functions:

- Distributing tasks to bloggers;
- Launching and halting an advertising campaign;
- Checking the correctness of tasks;
- Paying remuneration to bloggers for correctly performed tasks.

The **TaskManager** interacts with the following components:
The **Scheduler** is a task scheduling application that engages certain TaskManager methods based on a given schedule.
**DB** is a database in which advertising campaigns and bloggers' tasks are stored.
**MQ** is a transport for asynchronous interaction.
**Stat** is a component that contains all information about user publications, including the AI analysis (a system with artificial intelligence that analyzes sound, video and images for the presence of any objects), statistics of views and so on.
The **PaymentService** is a component that can reward bloggers with money based on the number of views.

**Distribution of tasks to bloggers**

When the advertiser creates an advertising campaign and indicated its timeframes, they can launch it straight away. At the same time, the advertiser must have a sufficient amount of money on the account for launching the advertising campaign, otherwise they will not be able to launch it. Suppose that the advertiser has enough money to conduct the advertising campaign. After launching it, the following happens:
 - The campaign is assigned the "Scheduled" status.
 - The required amount on the account of the advertiser is blocked for the advertising campaign, so every blogger gets a guarantee that the advertiser has money to pay.
 - The bloggers are given tasks according to the advertiser's chosen audience.
 - If the blogger refused the task and the audience is not covered, then the task will be given to other bloggers.
 - If enough bloggers have accepted the task, then it is revoked from the bloggers who have not yet accepted it.

![Distribution of tasks to bloggers](/image/taskmanager/part1.png)

**Launching and halting an advertising campaign**

Once a day, the **Scheduler** engages the **TaskManager** method, which, in turn, receives all planned advertising campaigns from **DB** and checks their timeframes:
 - If the time has come to launch the campaign and it has not yet been launched, then it will be launched.
 - If the time has come to halt the campaign and it has not yet been halted, then it will be halted.

![Launching and halting](/image/taskmanager/part2.png)

**Checking the correctness of the task**

A task is considered to be performed correctly if all the requirements of the advertiser have been fulfilled:
- The **Scheduler** engages the **TaskManager** task execution check method once a day.
- The **TaskManager** receives all the tasks that have been accepted for execution from **DB**.
- The **TaskManager** checks each received task for correct execution with the help of the **Stat** component.
- If the task has been fulfilled, then the blogger will be paid.
- If the task has not been fulfilled, then it will be checked again in 24 hours.
- If something malfunctions during the task checking period, the **Scheduler** will later check all tasks that have been left unchecked.

![checking](/image/taskmanager/part3.png)

**Payment of remuneration to bloggers for correctly performed tasks.**

Payment of remuneration is made in two stages:

Creation of payment orders:
- The **Scheduler** engages the **TaskManager** method once a day to create payment orders.
- The **TaskManager** receives from **DB** a list of users who have performed tasks correctly.
- The **TaskManager** requests from **Stat** the number of views for each user.
- The **TaskManager** transmits the number of views for each user to the **PaymentService** for creating payment orders.
- If something malfunctions when the payment orders are being created, the **Scheduler** will later engage the **TaskManager** method to create payment orders.

Execution of payment orders:
- The **Scheduler** engages the **TaskManager** method once a day to execute payment orders.
- The **TaskManager** checks if the advertising campaign has sufficient funds in its budget.
- If there are enough funds, the payment is made.
- If there are insufficient funds, then the advertising campaign is halted, when the budget is exhausted.
- If something malfunctions at the moment of payment, then the **Scheduler** will later engage the **TaskManager** method to execute the payment orders.

![payments](/image/taskmanager/part4.png)
