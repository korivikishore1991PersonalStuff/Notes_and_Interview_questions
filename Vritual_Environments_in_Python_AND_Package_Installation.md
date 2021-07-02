- [Vritual Environments in python](#Vritual-Environments-in-python)  
- [Installation of new packages in python](#Installation-of-new-packages-in-python)
- [Migrating to PROD](#Migrating-to-PROD)  
<br /><br />
<br /><br />
  
#  Vritual Environments in python  
## Working with multiple python versions and creating vritual environments  
python3.6 -m venv Directory_Name # if python3.6 exists in the $path  
or  
/path/to/pythonversion -m venv Directory_Name  
## Activating the vritual environment  
$source ./Directory_Name/bin/activate  
## Testing Python and pip versions  
$which python  
$which pip  
or  
(Directory_Name)bash-4.2$ python -m pip --version  
## Deactivating Vritual Environment  
$deactivate 
<br /><br />
<br /><br />
  
# Installation of new packages in python  
## From Git  
python3 -m pip install -e git+https://github.com/jkbr/httpie.git#egg=httpie  
or  
git clone https://github.com/jkbr/httpie.git  
Then just run the setup.py file from that directory,  
sudo python setup.py install  
## From PyPI  
python3 -m pip install "SomeProject==1.4"  
## From Wheel file  
python -m pip install some-package.whl  
## From a Downloaded Package  
python -m pip install ./path/to/SomeProject-x.x.x.tar.gz  
or  
$unzip ./path/to/SomeProject-x.x.x.tar.gz -d to_root_dir_of_SomeProject-x.x.x.tar.gz   
$cd to_root_dir_of_SomeProject-x.x.x.tar.gz  
$python setup.py install  
## From a Company Artifactory  
python -m pip install "SomeProject==1.4" -i http://artifactory-prxy-a.wellsfargo.net/artifactory/api/pypi/pypi-virtual/simple --trusted-host artifactory-prxy-a.wellsfargo.net --no-cache-dir  
<br /><br />
<br /><br />
  
# Migrating to PROD  
pip freeze > frozen-requirements.txt #gathering requirments in lower environment  
pip install -r frozen-requirements.txt #installing requirments from the txt document  
