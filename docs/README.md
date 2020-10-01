# User Guide
This app is to help user create, edit and store a task list.
### Quick start 
1. Ensure you have Java 11 installed in your Computer.
1. Download the jar file from (link).
1. Copy the file to the folder you want to use as the home folder for your Duke app
1. Double-click the file to start the app.  
If the setup is correct, you should see something like the below:  
![welcome message](https://github.com/zhangcaicai123/ip/blob/master/docs/image/hello.png)
1. Type the command in the command box and press Enter to execute it.  
    Some example commands you can try:
    - `todo read a book`
    - `deadline return books /by 2pm`
    - `event attend the tutorial /at Friday 4pm`
    - `list`- show the task list
    - `done 1`- mark the first task in list as done
    - `delete 2`- delete the second task in the list
    - `bye`- exit the program
1. Refer to the Features below for details of each command.
## Features 
- Add  
    - Add todo `todo`
    - Add deadline `deadline`
    - Add event `event`
- List `list`
- Delete  `delete`
- Mark as done `done`
- Find `find`
- Exit `bye`
### Add `todo`, `deadline`, `event`
Add a new task to the task list   
- add todo task: `todo`  
Format: `todo TASK`   
Examples: `todo read a book`
- add deadline task: `deadline`  
Format: `deadline TASK /by TIME`  
Examples: `deadline return books /by 2pm`
- add event task: `event`  
Format: `event TASK /at TIME`   
Examples: `event attend tutorial /at Friday 4pm`

Expected outcomes:  
![add](https://github.com/zhangcaicai123/ip/blob/master/docs/image/add.png)  
![data file after adding](https://github.com/zhangcaicai123/ip/blob/master/docs/image/add%20file.png)  

For the deadline and event,
the app also accepts dates in a format such as yyyy-mm-dd format (e.g., 2019-10-15) and print in a different format such as MMM dd yyyy e.g., (Oct 15 2019)  
Format: `deadline TASK /by YYYY-MM-DD`  
Examples: `deadline return books /by 2020-11-20`

Expected outcomes:  
![time](https://github.com/zhangcaicai123/ip/blob/master/docs/image/time.png)  
![time file](https://github.com/zhangcaicai123/ip/blob/master/docs/image/time%20file.png)  

### List `list`
Show the task list  
Format: `list`

Expected outcomes:    
![list](https://github.com/zhangcaicai123/ip/blob/master/docs/image/list.png)
### Delete `delete`
Delete a task from task list
Format: `delete INDEX`
Examples: `delete 2`  

Expected outcomes:   
![delete](https://github.com/zhangcaicai123/ip/blob/master/docs/image/delete.png)  
![data file after deleting](https://github.com/zhangcaicai123/ip/blob/master/docs/image/delete%20file.png)  

### Mark as done `done`
Mark a task as done 
Format: `done INDEX`
Examples: `done 1`  

Expected outcomes:   
![done](https://github.com/zhangcaicai123/ip/blob/master/docs/image/done.png)  
![data file after marking as done](https://github.com/zhangcaicai123/ip/blob/master/docs/image/done%20file.png)   
### Find `find`
Find task in task list with keyword
Format: `find KEYWORD` 
Examples: `find book`

Expected outcomes:  
![find](https://github.com/zhangcaicai123/ip/blob/master/docs/image/find.png)

### Exit `bye`
Exit the program
Format: `bye`

Expected outcomes:  
![exit](https://github.com/zhangcaicai123/ip/blob/master/docs/image/exit.png)

## Command summary
|Actions|Formats|Examples|
|-------|-------|--------|
|todo|`todo TASK`|`todo read a book`|
|deadline|`deadline TASK /by TIME`|`deadline return books /by 2pm`|
|event|`event TASK /at TIME`|`event attend the tutorial /at Friday 4pm`|
|list|`list`||
|delete|`delete INDEX`|`delete 2`|
|done|`done INDEX`|`done 1`|
|find|`find KEYWORD`|`find book`|
|exit|`bye`||

