## Import python files from different file location from where the python packages are:  
  
### Put this in /home/el/foo4/stuff/riaa.py:  
```code
def watchout():
  print "computers are transforming into a noose and a yoke for humans"  
```  
  
### Put this in /home/el/foo4/main.py:  
```code
import sys 
import os
sys.path.append(os.path.abspath("/home/el/foo4/stuff"))
from riaa import *
watchout()
```  
### Run it:
```code 
el@apollo:/home/el/foo4$ python main.py   
computers are transforming into a noose and a yoke for humans
```  
That imports everything in the foreign file from a different directory.
