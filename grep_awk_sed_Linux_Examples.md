### Using Grep to filter and extract file contents  
```bash
[n906147@ua0edge101 ~]$ cat demo_file
THIS LINE IS THE 1ST UPPER CASE LINE IN THIS FILE.
this line is the 1st lower case line in this file.
This Line Has All Its First Character Of The Word With Upper Case.

Two lines above this line is empty.
And this is the last line.
[n906147@ua0edge101 ~]$
[n906147@ua0edge101 ~]$
[n906147@ua0edge101 ~]$ grep -o -P "(?<=is).*(?= line)" demo_file
 line is the 1st lower case
 is the last
[n906147@ua0edge101 ~]$
[n906147@ua0edge101 ~]$
[n906147@ua0edge101 ~]$ grep -o -P "(?<=is).*(?=line)" demo_file
 line is the 1st lower case

 is the last
[n906147@ua0edge101 ~]$
[n906147@ua0edge101 ~]$
[n906147@ua0edge101 ~]$ grep -o "is.*line" demo_file
is line is the 1st lower case line
is line
is is the last line
```  
