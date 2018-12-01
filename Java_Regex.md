# Java  
  
  
## Libraries  
import java.util.regex.*;   
  
  
## matching  
  
### Util.regex 
```java
String content = "This is Chaitanya " + "from Beginnersbook.com.";  
String pattern = ".\*book.\*";  
boolean isMatch = Pattern.matches(pattern, content);  
```
(or)  
```java
String content = "This is a tutorial Website!";  
String patternString = ".\*tuToRiAl.\*";  
Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);  
Matcher matcher = pattern.matcher(content);  
boolean isMatched = matcher.matches();  
System.out.println("Is it a Match?" + isMatched);  
```
  
### From Strings  
```java
"Java is fun".matches("Java.*")  
"440-02-4534".matches("\\d{3}-\\d{2}-\\d{4}")  
```
  
  
## Splitting  
  
### Util.regex  
```java
String text = "ThisIsChaitanya.ItISMyWebsite";  
String patternString = "is";  
Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);  
String[] myStrings = pattern.split(text);  
```
  
### From Strings  
```java
"Java,C?C#,C++".split("[.,:;?]")  
the regular expression [.,:;?] specifies a pattern that matches ., ,, :, ;, or ?.  
Each of these characters is a delimiter for splitting the string. Thus, the string is split into
Java, C, C#, and C++, which are stored in array tokens.  
```
  
  
## Replacement  
  
### From Strings  
```java
String s = "a+b$#c".replaceAll("[$+#]", "NNN");  
String s = "Welcome to Tutorialspoint.com".replaceFirst("(.*)Tutorials(.*)", "AMROOD"));  
```
  
  
## Grouping & Extracting  
  
### Util.regex  
```java
public static void main(String[] args) {  
        String content = "ZZZ AA PP AA QQQ AAA ZZ";  
        String string = "AA";  
        Pattern pattern = Pattern.compile(string);  
        Matcher matcher = pattern.matcher(content);  
        while(matcher.find()) {  
            System.out.println("Found at: "+ matcher.start()  
                    +  
                    " - " + matcher.end());  
            System.out.println(content.substring(matcher.start(),matcher.end()));  
          }  
    }
```
(OR)
```java
public static void main(String[] args) {  
        String line = "This order was placed for QT3000! OK?";  
        Pattern pattern = Pattern.compile("(.*) (QT\\d+!) (.*)");  
        Matcher matcher = pattern.matcher(line);  
        while (matcher.find()) {  
            System.out.println("group 1: " + matcher.group(1));  
            System.out.println("group 2: " + matcher.group(2));  
            System.out.println("group 3: " + matcher.group(3));  
        }  
    }
```
