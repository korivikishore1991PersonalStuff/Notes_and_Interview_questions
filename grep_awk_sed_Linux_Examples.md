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

### Using SED and GREP for Horizontal filtering and awk for vertical filtering
```bash
korivi@Korivi16GB:~$ cat demo_file
+----------------+
| count(d sysid) |
+----------------+
| 1888238        |
+----------------+
1 rows
korivi@Korivi16GB:~$
korivi@Korivi16GB:~$
korivi@Korivi16GB:~$ awk '{ print $2; }' demo_file

count(d

1888238

rows
korivi@Korivi16GB:~$
korivi@Korivi16GB:~$
korivi@Korivi16GB:~$ sed -n '4,4p' demo_file
| 1888238        |
korivi@Korivi16GB:~$
korivi@Korivi16GB:~$
korivi@Korivi16GB:~$ grep -v -e "rows" -e "count" -e "-" demo_file
| 1888238        |
korivi@Korivi16GB:~$
korivi@Korivi16GB:~$
korivi@Korivi16GB:~$ sed -n '4,4p' demo_file |awk '{ print $2; }'
1888238
korivi@Korivi16GB:~$
korivi@Korivi16GB:~$
korivi@Korivi16GB:~$ grep -v -e "rows" -e "count" -e "-" demo_file |awk '{ print $2; }'
1888238
```
