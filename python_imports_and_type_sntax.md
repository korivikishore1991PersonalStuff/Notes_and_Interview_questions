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
Ex:  
```code
# Importing python program files on to the current python program
import sys
import os
sys.path.append(os.path.abspath("C:/Users/n906147/Desktop/Education/dsfs_python_packages"))
```  
  
## type syntax  
Example for type specifaction of a Vector  
```code
from typing import List
Vector = List[float]
def sum_of_squares_gradient(v: Vector) -> Vector:
    return [2 * v_i for v_i in v]
```    
